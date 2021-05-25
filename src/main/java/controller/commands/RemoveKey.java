package controller.commands;

import controller.CommandExecutionFailed;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoveKey extends CommandImpl{
    private static final String description = "remove_key null : удалить элемент из коллекции по его ключу";
    private static final String name = "remove_key";
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Integer key = Integer.parseInt(args[1]);
        spaceMarineManager.spaceMarineList.remove(key);
    }
}
