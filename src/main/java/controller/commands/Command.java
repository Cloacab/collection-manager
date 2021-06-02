package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarineManager;

import java.io.Serializable;

public interface Command extends Serializable {
    public void execute(String[] args) throws CommandExecutionFailed;
}
