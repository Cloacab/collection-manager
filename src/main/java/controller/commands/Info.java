package controller.commands;

import controller.CommandExecutionFailed;
import model.SpaceMarineManager;

public class Info extends CommandImpl{

    public Info() {
        description = "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
        name = "info";
    }

   @Override
    public void execute(String[] args) throws CommandExecutionFailed {
        System.out.printf("Collection class: %s\nCreation date: %s\nElements: %d\n",
                spaceMarineManager.spaceMarineList.getClass().getName(),
                SpaceMarineManager.getInitializationDate(),
                spaceMarineManager.spaceMarineList.size());
    }
}
