package controller.commands;

import controller.CommandExecutionFailed;

public class Save extends CommandImpl{

    public Save() {
        description = "save : сохранить коллекцию в файл";
        name = "save";
    }

    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        spaceMarineManager.writeToScv();
    }
}
