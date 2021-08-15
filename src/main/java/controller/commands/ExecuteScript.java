package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import view.UserInputManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExecuteScript extends CommandImpl{

    public ExecuteScript() {
        name = "execute_script";
        description = "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    @Override
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        String[] localArgs = args.length == 0 ? this.args : args;
        File file = new File(localArgs[1]);
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
        return null;
    }
}
