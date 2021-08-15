package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;

public class PrintFieldDescendingMeleeWeapon extends CommandImpl{

    public PrintFieldDescendingMeleeWeapon() {
        description = "print_field_descending_melee_weapon : вывести значения поля meleeWeapon всех элементов в порядке убывания";
        name = "print_field_descending_melee_weapon";
    }

    @Override
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        spaceMarineManager.spaceMarineList.entrySet().stream()
                .filter(a -> a.getValue().getMeleeWeapon() != null)
                .sorted((a, b) -> b.getValue().getMeleeWeapon().name().compareTo(a.getValue().getMeleeWeapon().name()))
                .forEach(System.out::println);
        return null;
    }
}
