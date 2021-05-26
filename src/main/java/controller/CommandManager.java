package controller;

import controller.commands.Command;
import controller.commands.CommandImpl;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class CommandManager {

    private final Map<String, Command> availableCommands = new HashMap<>();

    private final String commandsDescription;

    private static CommandManager instance = null;
    private CommandManager() {
        StringBuilder builder = new StringBuilder();

        Reflections reflections = new Reflections("controller.commands");
        Set<Class<? extends CommandImpl>> commandClasses = reflections.getSubTypesOf(CommandImpl.class);
        for (Class<? extends CommandImpl> commandClass :
                commandClasses) {
            try {
                CommandImpl command = commandClass.getConstructor().newInstance();
                availableCommands.put(command.getName(), command);
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

    public Map<String, Command> getAvailableCommands() {
        return availableCommands;
    }

    public String getCommandsDescription() {
        return commandsDescription;
    }
}
