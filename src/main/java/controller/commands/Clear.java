package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import model.SpaceMarine;

import java.util.LinkedHashMap;

public class Clear extends CommandImpl{

    public Clear() {
        name = "clear";
        description = "clear : очистить коллекцию";
        argType = null;
    }

    @Override
    public DTO<?> execute(String[] args) throws CommandExecutionFailed {
        String[] localArgs = args.length == 0 ? (String[]) this.args : args;
        spaceMarineManager.spaceMarineList = new LinkedHashMap<>();
        SpaceMarine.setCounter(-1);
        return null;
    }
}
