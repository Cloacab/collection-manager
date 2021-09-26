package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;

public class Save extends Command {

    public Save() {
        description = "save : сохранить коллекцию в файл";
        name = "save";
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        DTO<String> dto = DTOFactory.getInstance().getDTO();
        String save = spaceMarineService.save();
        dto.setData(save);
        dto.setStatus(DTOStatus.OK);
        return dto;
    }
}
