package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import model.SpaceMarine;
import view.UserInputManager;

import java.util.Objects;

public class Insert extends Command {

    public Insert() {
        description = "insert null {element} : добавить новый элемент с заданным ключом";
        name = "insert";
        argType = new Class[]{Integer.class, SpaceMarine.class};
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        DTO<String> dto = DTOFactory.getInstance().getDTO();
        int key;
        SpaceMarine value;

        if (this.args.length != 2) {
            throw new CommandExecutionFailed(String.format("Wrong arguments passed. needed: %d, found: %d", 2, this.args.length));
        } else {
            try {
                key = Integer.parseInt(this.args[0].toString());
                value = (SpaceMarine) this.args[1];
            } catch (Exception e) {
                throw new CommandExecutionFailed(e.getMessage());
            }
        }
        String result = spaceMarineService.insert(key ,value);
        dto.setData(result);
        dto.setStatus(DTOStatus.OK);
        return dto;
//        Object[] localArgs = args.length == 0 ? this.args : args;
//        int key = (int) args[0];
//        if (spaceMarineManager.spaceMarineList.get(key) != null) throw new CommandExecutionFailed("Element with this key already exists. Change key or use 'update' instead.");
//        SpaceMarine spaceMarine = UserInputManager.readObject();
//        spaceMarineManager.spaceMarineList.put(key, spaceMarine);
//        return null;
    }
}
