package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import model.SpaceMarine;

import java.util.Map;

public class Show extends Command {

    public Show() {
        description = "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
        name = "show";
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        DTO<Map<Integer, SpaceMarine>> dto = DTOFactory.getInstance().getDTO();
        dto.setData(spaceMarineService.show());
//        spaceMarineManager.spaceMarineList.entrySet()
//              .forEach(System.out::println);
        return dto;
    }
}
