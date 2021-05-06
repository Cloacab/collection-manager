package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;
import model.SpaceMarineManager;
import view.UserInputManager;

public class Insert implements Command{
    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        Integer key = Integer.parseInt(args[1]);
        SpaceMarine spaceMarine = UserInputManager.readObject();
        spaceMarineManager.spaceMarineList.put(key, spaceMarine);
    }
}
