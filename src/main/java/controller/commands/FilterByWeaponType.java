package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import model.SpaceMarine;
import model.Weapon;
import view.UserInputManager;

import java.util.Arrays;
import java.util.Map;

public class FilterByWeaponType extends CommandImpl {

    public FilterByWeaponType() {
        description = "filter_by_weapon_type weaponType : вывести элементы, значение поля weaponType которых равно заданному";
        name = "filter_by_weapon_type";
        argType = new Class[]{Weapon.class};
    }

    @Override
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        String[] localArgs = args.length == 0 ? (String[]) this.args : args;
        Weapon weapon;
        while (true) {
            if ((localArgs.length == 1)) {
                weapon = Weapon.valueOf(localArgs[0]);
                break;
            }
//            TODO: rewrite logic
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
        return null;
    }
}
