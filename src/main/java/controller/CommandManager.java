package controller;

import controller.commands.Command;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Set;

public class CommandManager {

    private final HashMap<String, Class<? extends Command>> availableCommands = new HashMap<String, Class<? extends Command>>();

    private final String commandsDescription;

    private static CommandManager instance = null;
    private CommandManager() {
        StringBuilder builder = new StringBuilder();

        Reflections reflections = new Reflections("controller.commands");
        Set<Class<? extends Command>> commandClasses = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> commandClass :
                commandClasses) {
            try {
                Command command = commandClass.getConstructor().newInstance();
                availableCommands.put(command.getName(), commandClass);
                builder.append(command.getDescription()).append("\n");
            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        commandsDescription = builder.toString();
    }

    public static CommandManager getInstance() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    public HashMap<String, Class<? extends Command>> getAvailableCommands() {
        return availableCommands;
    }

    public String getCommandsDescription() {
        return commandsDescription;
    }
}
