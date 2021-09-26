package controller.executor;

import controller.commands.Command;
import dto.DTO;

public interface Executor {
    public DTO<?> execute(Command command, Object[] args);
}
