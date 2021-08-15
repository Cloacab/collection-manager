package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;

public class Exit extends CommandImpl{

    public Exit() {
        description = "exit : завершить программу (без сохранения в файл)";
        name = "exit";
    }

    @Override
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        String[] localArgs = args.length == 0 ? this.args : args;
        System.exit(0);
        return null;
    }
}
