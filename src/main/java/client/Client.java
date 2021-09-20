package client;

import connection.ClientConnection;
import connection.Connection;
import controller.commands.Command;
import dto.DTO;
import dto.DTOFactory;
import view.UserInputManager;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static view.UserInputManager.setFromScript;

public class Client {

    private HashMap<String, Class<Command>> availableCommands;
    private final DTOFactory dtoFactory = DTOFactory.getInstance();
    private final Scanner userInputScanner = new Scanner(System.in);
    private String[] userCommandParts;

    public Connection getConnection() {
        return connection;
    }

    private final Connection connection = new ClientConnection();

    public Client() {

    }

    public void run() {
        connection.connect();
        startListening();
    }

    public void startListening() {
//        getCommands();
        System.out.println("===Start listening user input===");
        System.out.println("Enter command or type 'help' for list of all commands. Type 'connect' to connect to the serer.");
        while (true) {
            String userInput = userInputScanner.nextLine();
            userCommandParts = userInput.split(" ");
            String userCommand = userCommandParts[0];
            if (userCommand.equals("exit")) break;
            if (userCommand.equals("save")) {
                System.out.println("Unavailable command for user.");
                break;
            }
            if (userCommand.equals("connect")) {
                connection.connect();
                continue;
            } if (userCommand.equals("load")) {
                getCommands();
            }
            try {
                Class<Command> command = availableCommands.get(userCommand.trim().toLowerCase(Locale.ROOT));
                DTO<Class<Command>> commandDTO = dtoFactory.getDTO();
                commandDTO.setData(command);
                connection.send(commandDTO);
                connection.receive();
            } catch (IllegalArgumentException e) {
                System.out.println("Command was not found, try again.");
            } catch (IOException e) {
                System.out.println("Cannot send command to the server.");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("You haven't load command list from server, type 'load' to get available commands.");
            }
        }
    }

    private void getCommands() {
        DTO<String> dto = dtoFactory.getDTO();
        dto.setData("get");
        try {
            connection.send(dto);
            DTO<HashMap<String, Command>> receive = (DTO<HashMap<String, Command>>) connection.receive();
            HashMap<String, Class<Command>> map = new HashMap<String, Class<Command>>();
            for (Map.Entry<String, Command> x : receive.getData().entrySet()) {
                map.putIfAbsent(x.getKey(), (Class<Command>) x.getValue().getClass());
            }
            availableCommands = map;
        } catch (SocketTimeoutException e) {
            System.out.println("Lost connection with server, check server or try to type 'connect'.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
