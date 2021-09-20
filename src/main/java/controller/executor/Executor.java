package controller.executor;

import controller.commands.Command;
import controller.commands.CommandImpl;
import dto.DTO;

public interface Executor {
    public DTO<?> execute(CommandImpl command, Object[] args);
}
