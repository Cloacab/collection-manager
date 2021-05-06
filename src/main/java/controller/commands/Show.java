package controller.commands;

import controller.CommandExecutionFailed;

public class Show implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        spaceMarineManager.spaceMarineList.entrySet()
                .forEach(System.out::println);
    }
}
