package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import model.SpaceMarine;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class MinByChapter extends Command {

    public MinByChapter() {
        description = "min_by_chapter : вывести любой объект из коллекции, значение поля chapter которого является минимальным";
        name = "min_by_chapter";
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        Map.Entry<Integer, SpaceMarine> result = spaceMarineService.minByChapter();
        DTO<Map.Entry<Integer, SpaceMarine>> dto = DTOFactory.getInstance().getDTO();
        dto.setData(result);
        dto.setStatus(DTOStatus.OK);
        return dto;
//        Optional<Map.Entry<Integer, SpaceMarine>> minS = spaceMarineManager.spaceMarineList.entrySet().stream()
//                .min(Comparator.comparing(a -> a.getValue().getChapter().getName()));
//        System.out.println(minS.toString());
//        return null;
    }
}