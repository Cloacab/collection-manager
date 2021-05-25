package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;

import java.util.LinkedHashMap;

public class Clear extends CommandImpl{
    private static final String description = "clear : очистить коллекцию";
    private static final String name = "clear";
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        spaceMarineManager.spaceMarineList = new LinkedHashMap<>();
        SpaceMarine.setCounter(-1);
    }
}
