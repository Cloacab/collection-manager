package view;

import controller.CommandExecutionFailed;
import controller.CommandManager;
import controller.commands.Command;
import model.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class UserInputManager {
    private static final Scanner userInputScanner = new Scanner(System.in);
    private Scanner scriptInputScanner;

    public UserInputManager () {
    }

    public void startListenning() {
        System.out.println("Enter command or type 'help' for list of all commands.");
        while (true) {
            String userInput = userInputScanner.nextLine();
            String userCommand = userInput.split(" ")[0];
            try {
                Command command = CommandManager.valueOf(userCommand.toUpperCase(Locale.ROOT)).getCommand();
                command.execute(Arrays.stream(userInput.split(" ")).toArray(String[]::new));
            } catch (IllegalArgumentException e) {
                System.out.println("Command was not found, try again.");
            } catch (CommandExecutionFailed e) {
                System.out.println("Command cannot be executed, check arguments and try again.");
            }

        }
    }

//    private boolean checkField(Field field, Object value) {
//        Rules rules = field.getAnnotation(Rules.class);
//        for(Method rule : Rules.class.getDeclaredMethods()) {
//            rule.getDefaultValue() == rules.
//        }
//    }

    private static <T> T readField(Field fieldToRead, boolean fromScript) {

        T writtenField = null;
        System.out.println("Enter " + fieldToRead.getName() + ":");
        Class<?> fieldClass = fieldToRead.getType();

        if (fieldClass.getAnnotation(Complex.class) != null) {
            writtenField = (T)readObject(fieldClass, false) ;
        }

        if (fieldClass.isEnum()) {
            System.out.println("Possible variants:");
            for (Field field: fieldClass.getDeclaredFields()) {
                System.out.print(field.getName() + " ");
            }
            System.out.println();
        }

        while (true) {
            String userInput = userInputScanner.nextLine();
            Rules rules = fieldClass.getAnnotation(Rules.class);

//            if (userInput.equals("break")) {
//
//                break;
//            }

            for(Field rule: Rules.class.getDeclaredFields()) {
                System.out.println(rule);
            }

            if (rules != null) {

                if (rules.nullable()) {
                    writtenField = userInput.isEmpty() ? null : (T) userInput;
                } else {
                    System.out.println("Incorrect value for this field, try again.");
                    continue;
                }
                if (rules.epmtyString()) {
                    writtenField = (T) userInput;
                } else {
                    System.out.println("Incorrect value for this field, try again.");
                    continue;
                }
                if (rules.leftBorder() < Long.parseLong(userInput)) {
                    writtenField = (T) userInput;
                } else {
                    System.out.println("Incorrect value for this field, try again.");
                    continue;

                }
            }
            break;
        }

        return writtenField;
    }

    private static Constructor<?> getRightConstructor(Class<?> clazz) {
        List<Class<?>> fieldClasses = Arrays.stream(clazz.getDeclaredFields())
                .filter(x -> x.getAnnotation(UserInput.class) != null)
                .map(Field::getType)
                .collect(Collectors.toList());

        Constructor<?> rightConstructor = null;

        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            try {
                Class<?>[] params =  constructor.getParameterTypes();
                if (Arrays.equals(fieldClasses.toArray(Class<?>[]::new), params)){
                    rightConstructor = constructor;
                    rightConstructor.setAccessible(true);
                    break;
                }
            } catch (Exception ignored) {

            }
        }

        return rightConstructor;
    }

    public static <T> T readObject(Class<T> tClass, boolean fromScript) {

        System.out.println("Starting object reading. Type 'break' to cancel object reading.");

        List<Field> fields = Arrays.stream(tClass.getDeclaredFields())
                .filter(x -> x.getAnnotation(UserInput.class) != null)
                .collect(Collectors.toList());

        Constructor<?> rightConstructor = getRightConstructor(tClass);
        List<Object> varargs = new ArrayList<>();

        for (Field field : fields) {
            varargs.add(readField(field, fromScript));
        }

        try {
            return tClass.cast(rightConstructor.newInstance(varargs.toArray(Object[]::new)));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static SpaceMarine readObject(boolean fromScript) {
        String name;
        Coordinates coordinates;
        Long health;
        AstartesCategory category;
        Weapon weaponType;
        MeleeWeapon meleeWeapon;
        Chapter chapter;

        userInputScanner.nextLine();

        Set<Class<?>> fields = Arrays.stream(SpaceMarine.class.getDeclaredFields())
                .filter(x -> x.getAnnotation(UserInput.class) != null)
                .map(Field::getType)
                .collect(Collectors.toSet());

        System.out.println("egor soset pinisis");

        return new SpaceMarine();

    }
}
