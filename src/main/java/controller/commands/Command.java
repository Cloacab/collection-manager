package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;

import java.io.Serializable;

public interface Command extends Serializable {
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed;
    public Object[] getArgs();
    public void setArgs(String[] args);
}
