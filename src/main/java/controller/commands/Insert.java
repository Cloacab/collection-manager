package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;
import model.SpaceMarineManager;
import view.UserInputManager;

public class Insert extends CommandImpl{
    private static final String description = "insert null {element} : добавить новый элемент с заданным ключом";
    private static final String name = "insert";
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Integer key = Integer.parseInt(args[1]);
        if (spaceMarineManager.spaceMarineList.get(key) != null) throw new CommandExecutionFailed("Element with this key already exists. Change key or use 'update' instead.");
        SpaceMarine spaceMarine = UserInputManager.readObject();
        spaceMarineManager.spaceMarineList.put(key, spaceMarine);
    }
}
