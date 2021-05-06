package controller.commands;

import controller.CommandExecutionFailed;
import controller.CommandManager;

public class Help implements Command {
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        for (CommandManager commandManager : CommandManager.values()) {
            System.out.println(commandManager.getDescription());
        }
    }
}
