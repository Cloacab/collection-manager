package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;
import view.UserInputManager;

public class ReplaceIfLower implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Integer key = Integer.parseInt(args[1]);
        SpaceMarine newSpaceMarine = UserInputManager.readObject();
        if (spaceMarineManager.spaceMarineList.get(key).getName().compareTo(newSpaceMarine.getName()) > 0) {
            spaceMarineManager.spaceMarineList.put(key, newSpaceMarine);
        }
    }
}
