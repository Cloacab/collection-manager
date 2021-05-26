package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;
import view.UserInputManager;

public class ReplaceIfLower extends CommandImpl{

    public ReplaceIfLower() {
        description = "replace_if_lowe null {element} : заменить значение по ключу, если новое значение меньше старого";
        name = "replace_if_lower";
    }

    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Integer key = Integer.parseInt(args[1]);
        SpaceMarine newSpaceMarine = UserInputManager.readObject();
        if (spaceMarineManager.spaceMarineList.get(key).getName().compareTo(newSpaceMarine.getName()) > 0) {
            spaceMarineManager.spaceMarineList.put(key, newSpaceMarine);
        }
    }
}
