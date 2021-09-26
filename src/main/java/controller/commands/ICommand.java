package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;

import java.io.Serializable;

public interface ICommand extends Serializable {
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed;
    public Object[] getArgs();
    public void setArgs(Object[] args);
}
