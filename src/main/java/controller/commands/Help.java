package controller.commands;

import controller.CommandExecutionFailed;
import controller.CommandManager;
import dto.DTO;

public class Help extends CommandImpl {

    public Help() {
        description = "help : вывести справку по доступным командам";
        name = "help";
        argType = null;
    }

    @Override
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        String[] localArgs = args.length == 0 ? (String[]) this.args : args;
        System.out.println(CommandManager.getInstance().getCommandsDescription());
        return null;
    }
}
