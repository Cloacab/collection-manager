package server;

import connection.Connection;
import connection.ServerConnection;
import controller.CommandExecutionFailed;
import controller.CommandManager;
import controller.DTOProcessingException;
import controller.commands.ICommand;
import controller.commands.Command;
import controller.executor.CommandExecutor;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import dto.processors.Processor;
import model.SpaceMarineManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;


public class Server implements Runnable{

    private final SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
    private final CommandManager commandManager = CommandManager.getInstance();
    private final DTOFactory dtoFactory = DTOFactory.getInstance();
    private final CommandExecutor executor = CommandExecutor.getInstance();
    private final LinkedList<? extends Processor> processors = new LinkedList<>();

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
        startListening2();
    }

    private void startListening2() {
        System.out.println("===Server is up and running===");
        while (true) {
            DTO<?> response = dtoFactory.getDTO();
            try {
                DTO<?> request = connection.receive();
                DTO<?> processedRequest = process(request);
                response = executor.execute((Command) processedRequest.getData(), new Object[]{});
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                response.setStatus(DTOStatus.NOT_OK);
                response.setMessage(e.getMessage());
            } catch (DTOProcessingException e) {
                response.setStatus(DTOStatus.NOT_OK);
                response.setMessage(e.getMessage());
            } finally {
                try {
                    connection.send(response);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private DTO<?> process(DTO<?> request) {
        DTO<?> finalRequest = request;
        try {
            processors.forEach(p -> p.process(finalRequest));
//        for (Processor processor : processors) {
//            request = processor.process(request);
//        }
        } catch (Exception e) {
            request.setStatus(DTOStatus.NOT_OK);
            request.setMessage(e.getMessage());
            throw new DTOProcessingException("Cannot process DTO.");
        }
        return request;
    }

    public void startListening() {
        System.out.println("===Start listening user input===");
        System.out.println("Enter command or type 'help' for list of all commands.");
        while (true) {
            try {
                DTO<?> receivedMessage = connection.receive();
                if (receivedMessage.getData() instanceof ICommand) {
                    ICommand userICommand = (ICommand) receivedMessage.getData();
                    ICommand ICommand = userICommand.getClass().getDeclaredConstructor().newInstance();
                    ICommand.execute(new String[]{});
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
                System.out.println("ICommand cannot be executed.");
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
        DTO<HashMap<String, Class<? extends Command>>> dto = dtoFactory.getDTO();
        dto.setData(commandManager.getAvailableCommands());
        try {
            connection.send(dto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
