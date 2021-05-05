package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;
import model.Weapon;

import java.util.Map;
import java.util.stream.Stream;

public class FilterByWeaponType implements Command {
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        String value = args[0];
        try {
            Weapon weapon = Weapon.valueOf(value);
        } catch (Exception e) {
            throw new CommandExecutionFailed("No such weapon type. Try again.");
        }
        for (Map.Entry<Integer, SpaceMarine> x : spaceMarineManager.spaceMarineList.entrySet()) {
            if (x.getValue().getWeaponType() == Weapon.valueOf(value)) {
                System.out.println(x);
            }
        }
    }
}
