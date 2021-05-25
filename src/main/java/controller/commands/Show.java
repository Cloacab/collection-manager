package controller.commands;

import controller.CommandExecutionFailed;

public class Show extends CommandImpl{
    private static final String description = "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    private static final String name = "show";
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        spaceMarineManager.spaceMarineList.entrySet()
                .forEach(System.out::println);
    }
}
