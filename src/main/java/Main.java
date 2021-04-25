import controller.CommandManager;
import controller.commands.Command;
import model.*;
import view.UserInputManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        SpaceMarineManager manager = SpaceMarineManager.getInstance();
        manager.readFromCsv("SpaceMarineList.csv");
        SpaceMarine one = new SpaceMarine();
        manager.spaceMarineList.put(2, one);
        manager.writeToScv("SpaceMarine.csv");
        System.out.println(Arrays.toString(MeleeWeapon.class.getEnumConstants()));
        System.out.println(MeleeWeapon.class.getDeclaredFields()[0].getName());
//        MeleeWeapon chain_sword = (MeleeWeapon) "CHAIN_SWORD";
//        System.out.println(Arrays.toString(SpaceMarine.class.getConstructors()));
//        System.out.println(Arrays.toString(SpaceMarine.class.getConstructors()[1].getParameterTypes()));
//        System.out.println(Arrays.stream(SpaceMarine.class.getDeclaredFields())
//                .filter(x -> x.getAnnotation(UserInput.class) != null)
//                .map(Field::getType)
//                .collect(Collectors.toList()));
//        List<Class<?>> list = Arrays.stream(SpaceMarine.class.getDeclaredFields())
//                .filter(x -> x.getAnnotation(UserInput.class) != null)
//                .map(Field::getType)
//                .collect(Collectors.toList());
//        Constructor<?> rightConstructor = null;
//        for(Constructor<?> constructor : SpaceMarine.class.getDeclaredConstructors()) {
//            try {
//                Class<?>[] params =  constructor.getParameterTypes();
//                if (Arrays.equals(list.toArray(Class<?>[]::new), params)){
//                    rightConstructor = constructor;
//                    rightConstructor.setAccessible(true);
//                    break;
//                }
//            } catch (Exception e) {
//                System.out.println("penis");
//            }
//        }
//        assert rightConstructor != null;
//        System.out.println(Arrays.toString(rightConstructor.getParameterTypes()));
//        try {
//            System.out.println(rightConstructor.newInstance("hyi", new Coordinates(1, 2), 123, AstartesCategory.SCOUT, Weapon.BOLT_RIFLE, MeleeWeapon.CHAIN_SWORD, new Chapter("zlupa", "pizda")).toString());
//        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
//            e.printStackTrace();
//        }

        for(Method rule: Rules.class.getDeclaredMethods()) {
            System.out.println(rule.getDefaultValue());
        }
//        List<Constructor<?>> constructor = Arrays.stream(SpaceMarine.class.getConstructors())
//                .filter(x -> Arrays.stream(x.get())
//                        .allMatch(y -> y.getAnnotation(UserInput.class) != null))
//                .collect(Collectors.toList());
//        System.out.println(Arrays.toString(constructor.get(0).getParameterTypes()));
//        UserInputManager.readObject(SpaceMarine.class, false);
//        System.out.println(Weapon.valueOf("BOLT_RIFLE"));
        System.out.println(manager.spaceMarineList.toString());
        SpaceMarine two = UserInputManager.readObject(SpaceMarine.class, false);
        System.out.println(two);
//        System.out.println(one.getChapter().getName() == null);
//        for (CommandManager command:
//             CommandManager.values()) {
//            System.out.println((command.getDescription()));
//        }
//        System.out.println(CommandManager.values()[0].getClass());
    }
}
