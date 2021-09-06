package client;

import connection.Connection;
import controller.CommandExecutionFailed;
import controller.commands.Command;
import view.UserInputManager;

import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class ClientCommandListener extends UserInputManager {

    private final Connection connection;

    public ClientCommandListener(Connection connection) {
        this.connection = connection;
    }

//    public void startListenning() {
//        System.out.println("===Start listening user input===");
//        if (!fromScript) System.out.println("Enter command or type 'help' for list of all commands.");
//        while (true) {
//            String userInput = userInputScanner.nextLine();
//            String userCommand = userInput.split(" ")[0];
//            try {
//                Command command = commandManager.getAvailableCommands().get(userCommand.trim().toLowerCase(Locale.ROOT));
//
//                connection.send();
//            } catch (IllegalArgumentException e) {
//                setFromScript(false);
//                if(!fromScript) System.out.println("Command was not found, try again.");
//            }
//            if (!userInputScanner.hasNextLine() && fromScript) {
//                userInputScanner = new Scanner(System.in);
//                setFromScript(false);
//                break;
//            }
//        }
//    }
}