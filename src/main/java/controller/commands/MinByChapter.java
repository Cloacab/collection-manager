package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;

import java.util.stream.Stream;

public class MinByChapter implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        String minValue = ;
        SpaceMarine minSpaceMarine = null;
        for (SpaceMarine spaceMarine : spaceMarineManager.spaceMarineList.values()) {

        }
    }
}