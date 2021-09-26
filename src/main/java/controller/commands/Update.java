package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import model.SpaceMarine;
import view.UserInputManager;

import java.util.Map;

public class Update extends Command {

    public Update() {
        description = "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
        name = "update";
        argType = new Class[]{Integer.class, SpaceMarine.class};
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
//        int id = (int)args[1];
//        Integer key = null;
//        SpaceMarine spaceMarine = null;
//        for(Map.Entry<Integer, SpaceMarine> entry : spaceMarineManager.spaceMarineList.entrySet()) {
//            if (entry.getValue().getId() == id) {
//                key = entry.getKey();
//                spaceMarine = entry.getValue();
//            }
//        }
//        if (spaceMarine == null) {
//            throw new CommandExecutionFailed("No element with such id. Try again.");
//        }
//        spaceMarine = UserInputManager.readObject();
//        spaceMarine.setId(id);
//        spaceMarineManager.spaceMarineList.put(key, spaceMarine);
//        return null;
        DTO<String> dto = DTOFactory.getInstance().getDTO();
        if (this.args.length != 2) {
            throw new CommandExecutionFailed(String.format("Ti conch? Argumenti normal'nie peredai. nado: %d, ti dal: %d", 2, this.args.length));
        }
        try {
            long id = Long.parseLong(this.args[0].toString());
            SpaceMarine spaceMarine = (SpaceMarine) this.args[1];

            String s = spaceMarineService.update(id, spaceMarine);
            dto.setData(s);
            dto.setStatus(DTOStatus.OK);
        } catch (Exception e) {
            throw new CommandExecutionFailed(e.getMessage());
        }
        return dto;
    }
}
