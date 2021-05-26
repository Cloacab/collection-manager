package controller.commands;

import controller.CommandExecutionFailed;

public class Exit extends CommandImpl{

    public Exit() {
        description = "exit : завершить программу (без сохранения в файл)";
        name = "exit";
    }

    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        System.exit(0);
    }
}
