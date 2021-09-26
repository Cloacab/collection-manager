package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;

public class RemoveKey extends Command {

    public RemoveKey() {
        description = "remove_key null : удалить элемент из коллекции по его ключу";
        name = "remove_key";
        argType = new Class[]{Integer.class};
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        DTO<String> dto = DTOFactory.getInstance().getDTO();
        if (this.args.length != 1) {
            throw new CommandExecutionFailed(String.format("Ti conch? Argumenti normal'nie peredai. nado: %d, ti dal: %d", 1, this.args.length));
        }
        try {
            int key = Integer.parseInt(this.args[0].toString());
            String s = spaceMarineService.removeKey(key);
            dto.setData(s);
            dto.setStatus(DTOStatus.OK);
        } catch (Exception e) {
            throw new CommandExecutionFailed(e.getMessage());
        }
        return dto;
    }
}
