package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RemoveGreaterKey extends Command {

    public RemoveGreaterKey() {
        description = "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный";
        name = "remove_greater_key";
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
            String s = spaceMarineService.removeGreaterKey(key);
            dto.setData(s);
            dto.setStatus(DTOStatus.OK);
        } catch (Exception e) {
            throw new CommandExecutionFailed(e.getMessage());
        }
        return dto;
    }
}
