package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import model.SpaceMarine;
import view.UserInputManager;

public class Insert extends CommandImpl{

    public Insert() {
        description = "insert null {element} : добавить новый элемент с заданным ключом";
        name = "insert";
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        Object[] localArgs = args.length == 0 ? this.args : args;
        int key = (int) args[0];
        if (spaceMarineManager.spaceMarineList.get(key) != null) throw new CommandExecutionFailed("Element with this key already exists. Change key or use 'update' instead.");
        SpaceMarine spaceMarine = UserInputManager.readObject();
        spaceMarineManager.spaceMarineList.put(key, spaceMarine);
        return null;
    }
}
