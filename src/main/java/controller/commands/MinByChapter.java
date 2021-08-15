package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import model.SpaceMarine;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class MinByChapter extends CommandImpl{

    public MinByChapter() {
        description = "min_by_chapter : вывести любой объект из коллекции, значение поля chapter которого является минимальным";
        name = "min_by_chapter";
    }

    @Override
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        Optional<Map.Entry<Integer, SpaceMarine>> minS = spaceMarineManager.spaceMarineList.entrySet().stream()
                .min(Comparator.comparing(a -> a.getValue().getChapter().getName()));
        System.out.println(minS.toString());
        return null;
    }
}