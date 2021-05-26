package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;

import java.util.LinkedHashMap;

public class Clear extends CommandImpl{

    public Clear() {
        name = "clear";
        description = "clear : очистить коллекцию";
    }

    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        spaceMarineManager.spaceMarineList = new LinkedHashMap<>();
        SpaceMarine.setCounter(-1);
    }
}
