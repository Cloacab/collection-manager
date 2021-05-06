package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PrintFieldDescendingMeleeWeapon implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        spaceMarineManager.spaceMarineList.entrySet().stream()
                .filter(a -> a.getValue().getMeleeWeapon() != null)
                .sorted((a, b) -> b.getValue().getMeleeWeapon().name().compareTo(a.getValue().getMeleeWeapon().name()))
                .forEach(System.out::println);
//        System.out.println(sortedMap)sortedMap;
    }
}
