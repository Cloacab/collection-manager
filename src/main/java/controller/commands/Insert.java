package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;
import model.SpaceMarineManager;
import view.UserInputManager;

public class Insert implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Integer key = Integer.parseInt(args[1]);
        if (spaceMarineManager.spaceMarineList.get(key) != null) throw new CommandExecutionFailed("Element with this key already exists. Change key or use 'update' instead.");
        SpaceMarine spaceMarine = UserInputManager.readObject();
        spaceMarineManager.spaceMarineList.put(key, spaceMarine);
    }
}
