package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarineManager;

public class Info implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        System.out.printf("Collection class: %s\nCreation date: %s\nElements: %d",
                spaceMarineManager.spaceMarineList.getClass().getName(),
                SpaceMarineManager.getInitializationDate(),
                spaceMarineManager.spaceMarineList.size());
    }
}
