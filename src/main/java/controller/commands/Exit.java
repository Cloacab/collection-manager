package controller.commands;

import controller.CommandExecutionFailed;

public class Exit implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        System.exit(0);
    }
}
