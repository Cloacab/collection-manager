package view;

import controller.CommandExecutionFailed;
import controller.CommandManager;
import controller.commands.Command;
import model.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

public class UserInputManager {
    protected static Scanner userInputScanner = new Scanner(System.in);
    protected static boolean fromScript = false;
    protected static final CommandManager commandManager = CommandManager.getInstance();

    public static HashMap<String, Command> getAvailableCommands() {
        return availableCommands;
    }

    public static void setAvailableCommands(HashMap<String, Command> availableCommands) {
        UserInputManager.availableCommands = availableCommands;
    }

    private static HashMap<String, Command> availableCommands;

    private static final String INVALID_VALUE = "Invalid value for this field.";
    private static final String TRY_AGAIN_MESSAGE = "Try again.";
    private static final String INVALID_STRING = "Field must not be empty.";
    private static final String INVALID_X = "Field must be less than or equal 411";
    private static final String INVALID_HEALTH = "Field must be more than 0";

    public UserInputManager () {
        availableCommands = commandManager.getAvailableCommands();
    }

    public static Scanner getUserInputScanner() {
        return userInputScanner;
    }

    public static void setUserInputScanner(Scanner userInputScanner) {
        UserInputManager.userInputScanner = userInputScanner;
    }

    public static void setFromScript(boolean fromScript) {
        UserInputManager.fromScript = fromScript;
    }

    //TODO: Refactor command input
    public static void startListening() {
        System.out.println("===Start listening user input===");
        if (!fromScript) System.out.println("Enter command or type 'help' for list of all commands.");
        while (true) {
            String userInput = userInputScanner.nextLine();
            String userCommand = userInput.split(" ")[0];
            try {
                Command command = availableCommands.get(userCommand.trim().toLowerCase(Locale.ROOT));
                command.execute(Arrays.stream(userInput.split(" ")).toArray(String[]::new));
            } catch (IllegalArgumentException e) {
                setFromScript(false);
                if(!fromScript) System.out.println("Command was not found, try again.");
            } catch (CommandExecutionFailed e) {
                setFromScript(false);
                if(!fromScript) {
                    System.out.println(e.getMessage());
                    System.out.println("Command cannot be executed, check arguments and try again.");
                }
            }
            if (!userInputScanner.hasNextLine() && fromScript) {
                userInputScanner = new Scanner(System.in);
                setFromScript(false);
                break;
            }
        }
    }

    private static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

     public static SpaceMarine readObject() {
         String name;
         long x;
         float y;
         Coordinates coordinates;
         Long health;
         AstartesCategory category;
         Weapon weaponType;
         MeleeWeapon meleeWeapon;
         String chapterName;
         String chapterWorld;
         Chapter chapter;
         String userInput;

//         userInputScanner.nextLine();
         if (!fromScript) {
             while (true) {
                 System.out.print("Enter space marine's name:\n\t->");
                 userInput = userInputScanner.nextLine();
                 if (userInput.isEmpty()) {
                     System.out.println("Invalid value for this field. Field must not be empty. Try again.");
                     continue;
                 } else {
                     name = userInput;
                     break;
                 }
             }

             while (true) {
                 System.out.print("Enter space marine's x coordinate:\n\t->");
                 userInput = userInputScanner.nextLine();
                 if (userInput.isEmpty() || !isNumeric(userInput)) {
                     System.out.println("Invalid value for this field. Field must not be empty. Try again.");
                     continue;
                 }
                 if (Long.parseLong(userInput) > 411) {
                     System.out.println("Invalid value for this field. X coordinate must be <= 411. Try again");
                 } else {
                     x = Long.parseLong(userInput);
                     break;
                 }
             }

             while (true) {
                 System.out.print("Enter space marine's y coordinate:\n\t->");
                 userInput = userInputScanner.nextLine();
                 if (userInput.isEmpty() || !isNumeric(userInput)) {
                     System.out.println("Invalid value for this field. Field must not be empty. Try again.");
                 } else {
                     y = Float.parseFloat(userInput);
                     break;
                 }
             }


             while (true) {
                 System.out.print("Enter space marine's health:\n\t->");
                 userInput = userInputScanner.nextLine();
                 if (userInput.isEmpty() || !isNumeric(userInput)) {
                     System.out.println("Invalid value for this field. Field must not be empty. Try again.");
                     continue;
                 }
                 if (Long.parseLong(userInput) <= 0) {
                     System.out.println(INVALID_VALUE + " " + INVALID_HEALTH + " " + TRY_AGAIN_MESSAGE);
                 } else {
                     health = Long.parseLong(userInput);
                     break;
                 }
             }

             while (true) {
                 System.out.println("Enter space marine's category:");
                 System.out.println("Possible variants: " + Arrays.toString(AstartesCategory.class.getEnumConstants()) + ".");
                 System.out.print("\t->");
                 userInput = userInputScanner.nextLine();
                 if (userInput.isEmpty()) {
                     category = null;
                     break;
                 } else {
                     try {
                         category = AstartesCategory.valueOf(userInput.trim().toUpperCase(Locale.ROOT));
                         break;
                     } catch (Exception e) {
                         System.out.println("Cannot resolve enum constant. Try again.");
                         continue;
                     }
                 }
             }

             while (true) {
                 System.out.println("Enter space marine's weapon:");
                 System.out.println("Possible variants: " + Arrays.toString(Weapon.class.getEnumConstants()) + ".");
                 System.out.print("\t->");
                 userInput = userInputScanner.nextLine();
                 if (userInput.isEmpty()) {
                     System.out.println(INVALID_VALUE + " " + INVALID_STRING + " " + TRY_AGAIN_MESSAGE);
                 } else {
                     try {
                         weaponType = Weapon.valueOf(userInput.trim().toUpperCase(Locale.ROOT));
                         break;
                     } catch (Exception e) {
                         System.out.println("Cannot resolve enum constant. Try again.");
                         continue;
                     }
                 }
             }

             while (true) {
                 System.out.println("Enter space marine's melee weapon:");
                 System.out.println("Possible variants: " + Arrays.toString(MeleeWeapon.class.getEnumConstants()) + ".");
                 System.out.print("\t->");
                 userInput = userInputScanner.nextLine();
                 if (userInput.isEmpty()) {
                     meleeWeapon = null;
                     break;
                 } else {
                     try {
                         meleeWeapon = MeleeWeapon.valueOf(userInput.trim().toUpperCase(Locale.ROOT));
                         break;
                     } catch (Exception e) {
                         System.out.println("Cannot resolve enum constant. Try again.");
                         continue;
                     }
                 }
             }

             while (true) {
                 System.out.print("Enter space marine's chapter name:\n\t->");
                 userInput = userInputScanner.nextLine();
                 if (userInput.isEmpty()) {
                     System.out.println(INVALID_VALUE + " " + INVALID_STRING + " " + TRY_AGAIN_MESSAGE);
                 } else {
                     chapterName = userInput;
                     break;
                 }
             }

             while (true) {
                 System.out.print("Enter space marine's chapter world:\n\t->");
                 userInput = userInputScanner.nextLine();
                 if (userInput.isEmpty()) {
                     chapterWorld = null;
                     break;
                 } else {
                     chapterWorld = userInput;
                     break;
                 }
             }


         } else {
             name = userInputScanner.nextLine();
             x = Long.parseLong(userInputScanner.nextLine());
             y = Float.parseFloat(userInputScanner.nextLine());
             health = Long.parseLong(userInputScanner.nextLine());
             category = AstartesCategory.valueOf(userInputScanner.nextLine());
             weaponType = Weapon.valueOf(userInputScanner.nextLine());
             String meleeWeaponSt = userInputScanner.nextLine();
             meleeWeapon = meleeWeaponSt.isEmpty() ? null : MeleeWeapon.valueOf(meleeWeaponSt);
             chapterName = userInputScanner.nextLine();
             String chapterWorldSt = userInputScanner.nextLine();
             chapterWorld = chapterWorldSt.isEmpty() ? null : chapterWorldSt;
         }

         coordinates = new Coordinates(x, y);
         chapter = new Chapter(chapterName, chapterWorld);
         return new SpaceMarine(name, coordinates, health, category, weaponType, meleeWeapon, chapter);
     }
}
