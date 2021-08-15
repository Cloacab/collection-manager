package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;

public class Save extends CommandImpl{

    public Save() {
        description = "save : сохранить коллекцию в файл";
        name = "save";
    }

    @Override
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        spaceMarineManager.writeToScv();
        return null;
    }
}
