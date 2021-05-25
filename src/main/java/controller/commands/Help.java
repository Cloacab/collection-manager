package controller.commands;

import controller.CommandExecutionFailed;
import controller.CommandManager;

import java.util.Arrays;

public class Help extends CommandImpl {
    private static final String description = "help : вывести справку по доступным командам";
    private static final String name = "help";
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Arrays.stream(CommandManager.values())
                .forEach(a -> System.out.println(a.getDescription()));
    }
}
