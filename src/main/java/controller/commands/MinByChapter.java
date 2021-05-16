package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class MinByChapter implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Optional<Map.Entry<Integer, SpaceMarine>> minS = spaceMarineManager.spaceMarineList.entrySet().stream()
                .min(Comparator.comparing(a -> a.getValue().getChapter().getName()));
        System.out.println(minS.toString());
    }
}