package controller.commands;

import controller.CommandExecutionFailed;

public class Save implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        spaceMarineManager.writeToScv();
    }
}
