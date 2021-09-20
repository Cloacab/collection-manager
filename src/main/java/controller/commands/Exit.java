package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;

public class Exit extends CommandImpl{

    public Exit() {
        description = "exit : завершить программу (без сохранения в файл)";
        name = "exit";
        argType = null;
    }

    @Override
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        String[] localArgs = args.length == 0 ? (String[]) this.args : args;
        System.exit(0);
        return null;
    }
}
