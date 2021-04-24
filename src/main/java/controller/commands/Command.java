package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarineManager;

public interface Command {
    public void execute(String[] args) throws CommandExecutionFailed;
    SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();

}
