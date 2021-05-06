package controller.commands;

import controller.CommandExecutionFailed;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoveKey implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Integer key = Integer.parseInt(args[1]);
        spaceMarineManager.spaceMarineList.remove(key);
    }
}
