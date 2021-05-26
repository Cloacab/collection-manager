package controller.commands;

import controller.CommandExecutionFailed;
import controller.CommandManager;

import java.util.Arrays;

public class Help extends CommandImpl {

    public Help() {
        description = "help : вывести справку по доступным командам";
        name = "help";
    }

    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        System.out.println(CommandManager.getInstance().getCommandsDescription());
    }
}
