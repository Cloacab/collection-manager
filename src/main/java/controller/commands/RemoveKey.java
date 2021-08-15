package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;

public class RemoveKey extends CommandImpl{

    public RemoveKey() {
        description = "remove_key null : удалить элемент из коллекции по его ключу";
        name = "remove_key";
    }

    @Override
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        Integer key = Integer.parseInt(args[1]);
        spaceMarineManager.spaceMarineList.remove(key);
        return null;
    }
}
