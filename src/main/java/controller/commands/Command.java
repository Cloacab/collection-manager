package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;

import java.io.Serializable;

public interface Command extends Serializable {
    public DTO<?> execute(String[] args) throws CommandExecutionFailed;
    public String[] getArgs();
    public void setArgs(String[] args);
}
