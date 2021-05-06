package controller.commands;

import controller.CommandExecutionFailed;
import view.UserInputManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExecuteScript implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        File file = new File(args[1]);
        try(Scanner scriptScanner = new Scanner(file)) {
            Scanner userInputScanner = UserInputManager.getUserInputScanner();
            UserInputManager.setUserInputScanner(scriptScanner);
            UserInputManager.setFromScript(true);
            System.out.println("Starting to read script file.");
            UserInputManager.startListening();
            UserInputManager.setFromScript(false);
            UserInputManager.setUserInputScanner(userInputScanner);
        } catch (FileNotFoundException e) {
            throw new CommandExecutionFailed("Script file was not found. Try again.");
        }
    }
}
