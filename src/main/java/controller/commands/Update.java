package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarine;
import model.SpaceMarineManager;
import view.UserInputManager;

import java.util.Map;

public class Update extends CommandImpl{

    public Update() {
        description = "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
        name = "update";
    }

    @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        int id = Integer.parseInt(args[1]);
        Integer key = null;
        SpaceMarine spaceMarine = null;
        for(Map.Entry<Integer, SpaceMarine> entry : spaceMarineManager.spaceMarineList.entrySet()) {
            if (entry.getValue().getId() == id) {
                key = entry.getKey();
                spaceMarine = entry.getValue();
            }
        }
        if (spaceMarine == null) {
            throw new CommandExecutionFailed("No element with such id. Try again.");
        }
        spaceMarine = UserInputManager.readObject();
        spaceMarine.setId(id);
        spaceMarineManager.spaceMarineList.put(key, spaceMarine);
    }
}
