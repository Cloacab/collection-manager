package server;

import connection.Connection;
import connection.ServerConnection;
import controller.CommandExecutionFailed;
import controller.CommandManager;
import controller.DTOProcessingException;
import controller.commands.Command;
import controller.commands.Save;
import controller.executor.CommandExecutor;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import dto.processors.Processor;
import model.*;

import java.io.IOException;
import java.util.*;


public class Server implements Runnable {

    private static final String INVALID_VALUE = "Invalid value for this field.";
    private static final String TRY_AGAIN_MESSAGE = "Try again.";
    private static final String INVALID_STRING = "Field must not be empty.";
    private static final String INVALID_X = "Field must be less than or equal 411";
    private static final String INVALID_HEALTH = "Field must be more than 0";

    private final SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
    private final CommandManager commandManager = CommandManager.getInstance();
    private final DTOFactory dtoFactory = DTOFactory.getInstance();
    private final CommandExecutor executor = CommandExecutor.getInstance();
    private final LinkedList<? extends Processor> processors = new LinkedList<>();
    private final Connection connection = new ServerConnection();
    Scanner serverInput = new Scanner(System.in);

    public Server() {
        SpaceMarineManager.setFileName("SpaceMarineList.csv");
        spaceMarineManager.readFromCsv();
    }

    public void start() {
        Scanner serverInput = new Scanner(System.in);
        while (true) {
            String serverInputString = serverInput.nextLine();
            String[] serverInputParts = serverInputString.trim().split(" ");
            try {
                Command command = commandManager.getAvailableCommands().get(serverInputParts[0]).newInstance();
                command = validateCommand(command, serverInputParts);
                if (serverInputParts[0].equalsIgnoreCase("exit")) {
                    new Save().execute(new Object[]{});
                    System.exit(0);
                }
                DTO<?> execute = command.execute(new Object[]{});
                System.out.println(execute.getData());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (CommandExecutionFailed e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.out.println("===Exit interactive mode===");
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    @Override
    public void run() {
        startListening();
    }

    private void startListening() {
        System.out.println("===Server is up and running===");
        while (true) {
            DTO<?> response = dtoFactory.getDTO();
            try {
                DTO<?> request = connection.receive();
                DTO<?> processedRequest = process(request);
                response = executor.execute((Command) processedRequest.getData());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                response.setStatus(DTOStatus.NOT_OK);
                response.setMessage(e.getMessage());
            } catch (DTOProcessingException e) {
                response.setStatus(DTOStatus.NOT_OK);
                response.setMessage(e.getMessage());
            } finally {
                try {
                    connection.send(response);
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    private DTO<?> process(DTO<?> request) {
        try {
            processors.forEach(p -> p.process(request));
        } catch (Exception e) {
            request.setStatus(DTOStatus.NOT_OK);
            request.setMessage(e.getMessage());
            throw new DTOProcessingException("Cannot process DTO.");
        }
        return request;
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
            userInput = serverInput.nextLine();
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

    private SpaceMarine readObject() {
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

//         serverInput.nextLine();
        if (!fromScript) {
            while (true) {
                System.out.print("Enter space marine's name:\n\t->");
                userInput = serverInput.nextLine();
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
                userInput = serverInput.nextLine();
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
                userInput = serverInput.nextLine();
                if (userInput.isEmpty() || !isNumeric(userInput)) {
                    System.out.println("Invalid value for this field. Field must not be empty. Try again.");
                } else {
                    y = Float.parseFloat(userInput);
                    break;
                }
            }


            while (true) {
                System.out.print("Enter space marine's health:\n\t->");
                userInput = serverInput.nextLine();
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
                userInput = serverInput.nextLine();
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
                userInput = serverInput.nextLine();
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
                userInput = serverInput.nextLine();
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
                userInput = serverInput.nextLine();
                if (userInput.isEmpty()) {
                    System.out.println(INVALID_VALUE + " " + INVALID_STRING + " " + TRY_AGAIN_MESSAGE);
                } else {
                    chapterName = userInput;
                    break;
                }
            }

            while (true) {
                System.out.print("Enter space marine's chapter world:\n\t->");
                userInput = serverInput.nextLine();
                if (userInput.isEmpty()) {
                    chapterWorld = null;
                    break;
                } else {
                    chapterWorld = userInput;
                    break;
                }
            }


        } else {
            name = serverInput.nextLine();
            x = Long.parseLong(serverInput.nextLine());
            y = Float.parseFloat(serverInput.nextLine());
            health = Long.parseLong(serverInput.nextLine());
            category = AstartesCategory.valueOf(serverInput.nextLine());
            weaponType = Weapon.valueOf(serverInput.nextLine());
            String meleeWeaponSt = serverInput.nextLine();
            meleeWeapon = meleeWeaponSt.isEmpty() ? null : MeleeWeapon.valueOf(meleeWeaponSt);
            chapterName = serverInput.nextLine();
            String chapterWorldSt = serverInput.nextLine();
            chapterWorld = chapterWorldSt.isEmpty() ? null : chapterWorldSt;
        }

        coordinates = new Coordinates(x, y);
        chapter = new Chapter(chapterName, chapterWorld);
        return new SpaceMarine(name, coordinates, health, category, weaponType, meleeWeapon, chapter);
    }
}
