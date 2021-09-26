package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import model.SpaceMarine;
import view.UserInputManager;

public class ReplaceIfLower extends Command {

    public ReplaceIfLower() {
        description = "replace_if_lower null {element} : заменить значение по ключу, если новое значение меньше старого";
        name = "replace_if_lower";
        argType = new Class[]{Integer.class, SpaceMarine.class};
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
//        Integer key = (int) args[1];
//        SpaceMarine newSpaceMarine = UserInputManager.readObject();
//        if (spaceMarineManager.spaceMarineList.get(key).getName().compareTo(newSpaceMarine.getName()) > 0) {
//            spaceMarineManager.spaceMarineList.put(key, newSpaceMarine);
//        }
//        return null;
        DTO<String> dto = DTOFactory.getInstance().getDTO();
        if (this.args.length != 2) {
            throw new CommandExecutionFailed(String.format("Ti conch? Argumenti normal'nie peredai. nado: %d, ti dal: %d", 2, this.args.length));
        }
        try {
            int key = Integer.parseInt(this.args[0].toString());
            SpaceMarine spaceMarine = (SpaceMarine) this.args[1];

            String s = spaceMarineService.replaceIfLower(key, spaceMarine);
            dto.setData(s);
            dto.setStatus(DTOStatus.OK);
        } catch (Exception e) {
            throw new CommandExecutionFailed(e.getMessage());
        }
        return dto;
    }
}
