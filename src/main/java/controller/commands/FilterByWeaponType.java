package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;
import model.Weapon;
import view.UserInputManager;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

public class FilterByWeaponType implements Command {
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Weapon weapon;
        while (true) {
            System.out.println("Enter weapon type:");
            System.out.print("Possible variants: " + Arrays.toString(Weapon.class.getEnumConstants()) + ".\n");
            String value = UserInputManager.getUserInputScanner().nextLine();
            try {
                weapon = Weapon.valueOf(value);
                break;
            } catch (Exception e) {
                System.out.println("Invalid emun constant. Try again.");
            }
        }
        for (Map.Entry<Integer, SpaceMarine> x : spaceMarineManager.spaceMarineList.entrySet()) {
            if (x.getValue().getWeaponType() == weapon) {
                System.out.println(x);
            }
        }
    }
}
