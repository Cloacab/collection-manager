package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import model.SpaceMarine;

import java.util.LinkedHashMap;

public class Clear extends CommandImpl{

    public Clear() {
        super();
        name = "clear";
        description = "clear : очистить коллекцию";
    }

    @Override
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        String[] localArgs = args.length == 0 ? this.args : args;
        spaceMarineManager.spaceMarineList = new LinkedHashMap<>();
        SpaceMarine.setCounter(-1);
        return null;
    }
}
