package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import model.SpaceMarine;
import model.Weapon;
import view.UserInputManager;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FilterByWeaponType extends Command {

    public FilterByWeaponType() {
        description = "filter_by_weapon_type weaponType : вывести элементы, значение поля weaponType которых равно заданному";
        name = "filter_by_weapon_type";
        argType = new Class[]{Weapon.class};
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        DTO<HashMap<Integer, SpaceMarine>> result = DTOFactory.getInstance().getDTO();
        if (this.args.length == 0) {
            throw new CommandExecutionFailed("No arguments presented.");
        } else {
            if (this.args[0] instanceof Weapon) {
                HashMap<Integer, SpaceMarine> map = spaceMarineService.filterByWeaponType(Weapon.valueOf(this.args[0].toString()));
                result.setData(map);
                result.setStatus(DTOStatus.OK);
            } else  {
                throw new CommandExecutionFailed("Incompatible argument type.");
            }
        }
        return result;
//        Object[] localArgs = args.length == 0 ? (String[]) this.args : args;
//        Weapon weapon;
//        while (true) {
//            if ((localArgs.length == 1)) {
//                weapon = Weapon.valueOf((String) localArgs[0]);
//                break;
//            }
////            TODO: rewrite logic
//            System.out.println("Enter weapon type:");
//            System.out.print("Possible variants: " + Arrays.toString(Weapon.class.getEnumConstants()) + ".\n");
//            String value = UserInputManager.getUserInputScanner().nextLine();
//            try {
//                weapon = Weapon.valueOf(value);
//                break;
//            } catch (Exception e) {
//                System.out.println("Invalid enum constant. Try again.");
//            }
//        }
//        for (Map.Entry<Integer, SpaceMarine> x : spaceMarineManager.spaceMarineList.entrySet()) {
//            if (x.getValue().getWeaponType() == weapon) {
//                System.out.println(x);
//            }
//        }
//        return null;
    }
}
