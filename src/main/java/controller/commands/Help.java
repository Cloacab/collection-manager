package controller.commands;

import controller.CommandExecutionFailed;
import controller.CommandManager;

import java.util.Arrays;

public class Help implements Command {
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Arrays.stream(CommandManager.values())
                .forEach(a -> System.out.println(a.getDescription()));
    }
}
