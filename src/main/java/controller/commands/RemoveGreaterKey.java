package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoveGreaterKey extends CommandImpl{

    public RemoveGreaterKey() {
        description = "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный";
        name = "remove_greater_key";
    }

    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Integer key = Integer.parseInt(args[1]);
        spaceMarineManager.spaceMarineList = spaceMarineManager.spaceMarineList.entrySet().stream()
                .filter(a -> a.getKey() <= key)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }
}
