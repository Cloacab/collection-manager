package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class MinByChapter implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Optional<Map.Entry<Integer, SpaceMarine>> minS = spaceMarineManager.spaceMarineList.entrySet().stream()
                .min((a, b) -> a.getValue().getChapter().getName().compareTo(b.getValue().getChapter().getName()));
        System.out.println(minS.toString());
    }
}