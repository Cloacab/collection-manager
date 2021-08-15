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
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        String[] localArgs = args.length == 0 ? this.args : args;
        Integer key = Integer.parseInt(localArgs[1]);
        if (spaceMarineManager.spaceMarineList.get(key) != null) throw new CommandExecutionFailed("Element with this key already exists. Change key or use 'update' instead.");
        SpaceMarine spaceMarine = UserInputManager.readObject();
        spaceMarineManager.spaceMarineList.put(key, spaceMarine);
        return null;
    }
}
