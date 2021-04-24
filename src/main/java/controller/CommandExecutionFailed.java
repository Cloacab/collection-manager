package controller;

import controller.commands.Command;
import controller.commands.Help;

public class CommandExecutionFailed extends Exception {
    public CommandExecutionFailed(String message) {
        super(message);
    }

}
