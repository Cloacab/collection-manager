package controller.commands;

import controller.CommandExecutionFailed;

public class Exit extends CommandImpl{
    private static final String description = "exit : завершить программу (без сохранения в файл)";
    private static final String name = "exit";
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        System.exit(0);
    }
}
