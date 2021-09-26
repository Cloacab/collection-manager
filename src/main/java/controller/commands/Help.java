package controller.commands;

import controller.CommandExecutionFailed;
import controller.CommandManager;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;

public class Help extends Command {

    public Help() {
        description = "help : вывести справку по доступным командам";
        name = "help";
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        String result = spaceMarineService.help();
        DTO<String> dto = DTOFactory.getInstance().getDTO();
        dto.setData(result);
        dto.setStatus(DTOStatus.OK);
        return dto;
//        Object[] localArgs = args.length == 0 ? (String[]) this.args : args;
//        System.out.println(CommandManager.getInstance().getCommandsDescription());
//        return null;
    }
}
