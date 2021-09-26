package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import model.SpaceMarine;

import java.util.LinkedHashMap;

public class Clear extends Command {

    public Clear() {
        name = "clear";
        description = "clear : очистить коллекцию";
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        String clean = spaceMarineService.clear();
        DTO<String> dto = DTOFactory.getInstance().getDTO();
        dto.setData(clean);
        dto.setStatus(DTOStatus.OK);
        return dto;
//        Object[] localArgs = args.length == 0 ? this.args : args;
//        spaceMarineManager.spaceMarineList = new LinkedHashMap<>();
//        SpaceMarine.setCounter(-1);
//        return factory.getDTO();
    }
}
