package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;

public class Exit extends Command {

    public Exit() {
        description = "exit : завершить программу (без сохранения в файл)";
        name = "exit";
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        String result = spaceMarineService.exit();
        DTO<String> dto = DTOFactory.getInstance().getDTO();
        dto.setData(result);
        dto.setStatus(DTOStatus.OK);
        return dto;
//        Object[] localArgs = args.length == 0 ? (String[]) this.args : args;
//        System.exit(0);
//        return factory.getDTO();
    }
}
