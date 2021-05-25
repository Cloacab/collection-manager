package controller.commands;

import controller.CommandExecutionFailed;
import view.UserInputManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExecuteScript extends CommandImpl{
    private static final String description = "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    private static final String name = "execute_script";
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        File file = new File(args[1]);
        try(Scanner scriptScanner = new Scanner(file)) {
            Scanner userInputScanner = UserInputManager.getUserInputScanner();
            UserInputManager.setUserInputScanner(scriptScanner);
            UserInputManager.setFromScript(true);
            System.out.println("===Starting to read script file===");
            UserInputManager.startListening();
            System.out.println("===Script file ended===");
        } catch (FileNotFoundException e) {
            throw new CommandExecutionFailed("Script file was not found. Try again.");
        }
    }
}
