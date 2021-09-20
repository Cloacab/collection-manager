package server;

import connection.Connection;
import connection.ServerConnection;
import controller.CommandExecutionFailed;
import controller.CommandManager;
import controller.commands.Command;
import controller.executor.CommandExecutor;
import dto.DTO;
import dto.DTOFactory;
import model.SpaceMarineManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;


public class Server implements Runnable{

    private final SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
    private final CommandManager commandManager = CommandManager.getInstance();
    private final DTOFactory dtoFactory = DTOFactory.getInstance();
    private final CommandExecutor executor = CommandExecutor.getInstance();

    public Connection getConnection() {
        return connection;
    }

    private final Connection connection = new ServerConnection();

    public Server() {
        SpaceMarineManager.setFileName("SpaceMarineList.csv");
        spaceMarineManager.readFromCsv();
    }

    @Override
    public void run() {
        connection.connect();
        sendCommands();
        startListening();
    }

    public void startListening() {
        System.out.println("===Start listening user input===");
        System.out.println("Enter command or type 'help' for list of all commands.");
        while (true) {
            try {
                DTO<?> receivedMessage = connection.receive();
                if (receivedMessage.getData() instanceof Command) {
                    Command userCommand = (Command) receivedMessage.getData();
                    Command command = userCommand.getClass().getDeclaredConstructor().newInstance();
                    command.execute(new String[]{});
                } else if (receivedMessage.getData().equals("get")) {
                    sendCommands();
                } else if (receivedMessage.getData().equals("handshake")) {
                    connection.connect();
                } else {
                    System.out.printf("Cannot recognize command name: '%s'.\n", receivedMessage.getData());
                }
            } catch (IOException e) {
                System.out.println("Cannot receive data from client.");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (CommandExecutionFailed commandExecutionFailed) {
                System.out.println("Command cannot be executed.");
                commandExecutionFailed.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendCommands() {
        do {
            try {
                DTO<?> receive = connection.receive();
                if (receive.getData().equals("get")) break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } while (true);
        DTO<HashMap<String, Command>> dto = dtoFactory.getDTO();
        dto.setData(commandManager.getAvailableCommands());
        try {
            connection.send(dto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
