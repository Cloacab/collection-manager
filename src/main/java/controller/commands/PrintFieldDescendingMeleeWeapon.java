package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import model.SpaceMarine;

import java.util.Map;

public class PrintFieldDescendingMeleeWeapon extends Command {

    public PrintFieldDescendingMeleeWeapon() {
        description = "print_field_descending_melee_weapon : вывести значения поля meleeWeapon всех элементов в порядке убывания";
        name = "print_field_descending_melee_weapon";
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        Map<Integer, SpaceMarine> result = spaceMarineService.printFieldDescendingMeleeWeapon();
        DTO<Map<Integer, SpaceMarine>> dto = DTOFactory.getInstance().getDTO();
        dto.setData(result);
        dto.setStatus(DTOStatus.OK);
        return dto;
//        spaceMarineManager.spaceMarineList.entrySet().stream()
//                .filter(a -> a.getValue().getMeleeWeapon() != null)
//                .sorted((a, b) -> b.getValue().getMeleeWeapon().name().compareTo(a.getValue().getMeleeWeapon().name()))
//                .forEach(System.out::println);
//        return null;
    }
}
