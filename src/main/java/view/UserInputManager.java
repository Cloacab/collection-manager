package view;

import controller.CommandExecutionFailed;
import controller.CommandManager;
import controller.commands.Command;
import model.*;
import model.rules.Complex;
import model.rules.Rule;
import model.rules.Rules;
import model.rules.UserInput;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public class UserInputManager {
    private static final Scanner userInputScanner = new Scanner(System.in);
    private Scanner scriptInputScanner;

    private static final String INVALID_VALUE = "Invalid value for this field.";
    private static final String TRY_AGAIN_MESSAGE = "Try again.";
    private static final String INVALID_STRING = "Field must not be empty.";
    private static final String INVALID_X = "Field must be less than or equal 411";
    private static final String INVALID_HEALTH = "Field must be more than 0";

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

//    private boolean checkField(Field field) {
//        List<Annotation> annotations = Arrays.stream(field.getAnnotations())
//                .filter(x -> x.getClass().getAnnotation(Rule.class) != null)
//                .collect(Collectors.toList());
//        for (Annotation annotation : annotations) {
//            annotation.getClass().getDeclaredMethods();
//            Rules;
//        }
//    }

    private static <T> T readField(Field fieldToRead, boolean fromScript) {

        T writtenField = null;
        System.out.println("Enter " + fieldToRead.getName() + ":");
        Class<?> fieldClass = fieldToRead.getType();

        if (fieldToRead.getAnnotation(Complex.class) != null) {
            writtenField = (T)readObject(fieldClass, false) ;
        }

        if (fieldClass.isEnum()) {
            System.out.println("Possible variants:");
            System.out.print("\t");
            for (Object field: fieldClass.getEnumConstants()) {
                System.out.print(field + " ");
            }
            System.out.println();
            System.out.print("->");
        }

        String userInput = userInputScanner.nextLine();
        writtenField = (T) userInput;

//        while (true) {
//            String userInput = userInputScanner.nextLine();
//            Rule rule = fieldToRead.getAnnotation(Rule.class);
//            if (fieldClass.isEnum()) {
//                writtenField = fieldClass.isInstance()
//            }
//            writtenField = (T) userInput;
//            break;
////            if (userInput.equals("break")) {
////
////                break;
////            }
//
//
////            if (rule != null) {
////
////            }
////            break;
//        }

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

        List<Field> fields = Arrays.stream(tClass.getDeclaredFields())
                .filter(x -> x.getAnnotation(UserInput.class) != null)
                .collect(Collectors.toList());

        Constructor<?> rightConstructor = getRightConstructor(tClass);
        List<Object> varargs = new ArrayList<>();

        for (Field field : fields) {
            varargs.add(readField(field, fromScript));
        }

        rightConstructor.get

        try {
            return tClass.cast(rightConstructor.newInstance(varargs.toArray(Object[]::new)));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

     public static SpaceMarine readObject(boolean fromScript) {
         String name;
         long x;
         float y;
         Coordinates coordinates;
         Long health;
         AstartesCategory category;
         Weapon weaponType;
         MeleeWeapon meleeWeapon;
         Chapter chapter;
         String userInput;

         userInputScanner.nextLine();

         Set<Class<?>> fields = Arrays.stream(SpaceMarine.class.getDeclaredFields())
                 .filter(x -> x.getAnnotation(UserInput.class) != null)
                 .map(Field::getType)
                 .collect(Collectors.toSet());

         while (true) {
             System.out.print("Enter space marine's name:\n\t->");
             userInput = userInputScanner.nextLine();
             if (userInput.isEmpty()) {
                 System.out.println("Invalid value for this field. Field must not be empty. Try again.");
                 continue;
             } else {
                 name = userInput;
             }
         }

         while (true) {
             System.out.print("Enter space marine's x coordinate:\n\t->");
             userInput = userInputScanner.nextLine();
             if (userInput.isEmpty()) {
                 System.out.println("Invalid value for this field. Field must not be empty. Try again.");
                 continue;
             }
             if (Long.parseLong(userInput) > 411) {
                 System.out.println("Invalid value for this field. X coordinate must be <= 411. Try again");
             } else {
                 x = Long.parseLong(userInput);
             }
         }

         while (true) {
             System.out.print("Enter space marine's y coordinate:\n\t->");
             userInput = userInputScanner.nextLine();
             if (userInput.isEmpty()) {
                 System.out.println("Invalid value for this field. Field must not be empty. Try again.");
             } else {
                 y = Float.parseFloat(userInput);
             }
         }

         while (true) {
             System.out.print("Enter space marine's healt:\n\t->");
             userInput = userInputScanner.nextLine();
             if (userInput.isEmpty()) {
                 System.out.println("Invalid value for this field. Field must not be empty. Try again.");
                 continue;
             }
             if (Long.parseLong(userInput) <= 0) {
                 System.out.println(INVALID_VALUE + " " + INVALID_HEALTH + " " + TRY_AGAIN_MESSAGE);
             } else {
                 health = Long.parseLong(userInput);
             }
         }

         return new SpaceMarine();

     }
}
