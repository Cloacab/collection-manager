package client;

import connection.ClientConnection;
import connection.Connection;
import controller.CommandManager;
import controller.commands.ICommand;
import controller.commands.Command;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import model.*;
import sun.jvm.hotspot.memory.Space;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.*;

public class Client {

    private static final String INVALID_VALUE = "Invalid value for this field.";
    private static final String TRY_AGAIN_MESSAGE = "Try again.";
    private static final String INVALID_STRING = "Field must not be empty.";
    private static final String INVALID_X = "Field must be less than or equal 411";
    private static final String INVALID_HEALTH = "Field must be more than 0";


    private HashMap<String, Class<? extends Command>> availableCommands = CommandManager.getInstance().getAvailableCommands();;
    private final DTOFactory dtoFactory = DTOFactory.getInstance();
    private final Scanner userInputScanner = new Scanner(System.in);

    private String[] userCommandParts;

    public Connection getConnection() {
        return connection;
    }

    private final Connection connection = new ClientConnection();

    public Client() {

    }

    public void run() {
//        connection.connect();
        startListening2();
    }

    public void startListening() {
//        getCommands();
        System.out.println("===Start listening user input===");
        System.out.println("Enter command or type 'help' for list of all commands. Type 'connect' to connect to the serer.");
        while (true) {
            String userInput = userInputScanner.nextLine();
            userCommandParts = userInput.split(" ");
            String userCommand = userCommandParts[0];
            if (userCommand.equals("exit")) break;
            if (userCommand.equals("save")) {
                System.out.println("Unavailable command for user.");
                break;
            }
            if (userCommand.equals("connect")) {
                connection.connect();
                continue;
            } if (userCommand.equals("load")) {
//                getCommands();
            }
            try {
                Class<? extends Command> command = availableCommands.get(userCommand.trim().toLowerCase(Locale.ROOT));
                DTO<Class<? extends Command>> commandDTO = dtoFactory.getDTO();
                commandDTO.setData(command);
                connection.send(commandDTO);
                connection.receive();
            } catch (IllegalArgumentException e) {
                System.out.println("ICommand was not found, try again.");
            } catch (IOException e) {
                System.out.println("Cannot send command to the server.");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("You haven't load command list from server, type 'load' to get available commands.");
            }
        }
    }

    public void startListening2() {
        DTO<?> reqest = dtoFactory.getDTO();
        System.out.println("===Start listening user input===");
        while (true) {
            String userInput = userInputScanner.nextLine();
            userCommandParts = userInput.split(" ");
            String userCommand = userCommandParts[0];
            try {
                Class<? extends Command> commandClass = availableCommands.get(userCommand);
                if (commandClass == null) throw new ClassNotFoundException("No such command.");
                Command command = commandClass.newInstance();
                command = validateCommand(command, userCommandParts);
                DTO<Command> request = dtoFactory.getDTO();
                request.setData(command);
                connection.send(request);
                DTO<?> response = connection.receive();
                System.out.println(response.getData());
                if (response.getStatus().equals(DTOStatus.NOT_OK)) {
                    System.out.println(response.getMessage());
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Cannot send command to the server. Seems like server is down, try again.");
//                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
//                e.printStackTrace();
            }
        }
    }

    private Command validateCommand(Command command, String[] userCommandParts) {
        if (command.getArgType() == null) {
            return command;
        }

        ArrayList<Object> arguments = new ArrayList<>();

        for (Class argType : command.getArgType()) {
            if (argType == SpaceMarine.class) {
                SpaceMarine spaceMarine = readObject();
                arguments.add(1, spaceMarine);
            } else if (argType == Integer.class) {
                int key = Integer.parseInt(userCommandParts[1]);
                arguments.add(0, key);
            } else if (argType == Weapon.class) {
                Weapon weapon = readWeapon();
                arguments.add(0, weapon);
            } else if (argType == String.class) {
                String filename = userCommandParts[1];
                arguments.add(0, filename);
            }
        }
        System.out.println("==========");
        System.out.println(arguments);
        System.out.println("==========");
        command.setArgs(arguments.toArray());
        System.out.println("==========");
        System.out.println(Arrays.toString(command.getArgs()));
        System.out.println("==========");
        return command;
    }

    private Weapon readWeapon() {
        Weapon weaponType;
        String userInput;
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
        return weaponType;
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

    public SpaceMarine readObject() {
        boolean fromScript = false;

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

//    private void getCommands() {
//        DTO<String> dto = dtoFactory.getDTO();
//        dto.setData("get");
//        try {
//            connection.send(dto);
//            DTO<HashMap<String, ICommand>> receive = (DTO<HashMap<String, ICommand>>) connection.receive();
//            HashMap<String, Class<ICommand>> map = new HashMap<String, Class<ICommand>>();
//            for (Map.Entry<String, ICommand> x : receive.getData().entrySet()) {
//                map.putIfAbsent(x.getKey(), (Class<ICommand>) x.getValue().getClass());
//            }
////            availableCommands = map;
//        } catch (SocketTimeoutException e) {
//            System.out.println("Lost connection with server, check server or try to type 'connect'.");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
