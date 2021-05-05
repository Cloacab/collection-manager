package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;

import java.util.LinkedHashMap;

public class Clear implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        spaceMarineManager.spaceMarineList = new LinkedHashMap<>();
        SpaceMarine.setCounter(-1);
    }
}
