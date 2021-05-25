package controller.commands;

import controller.CommandExecutionFailed;

public class Save extends CommandImpl{
    private static final String description = "save : сохранить коллекцию в файл";
    private static final String name = "save";
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        spaceMarineManager.writeToScv();
    }
}
