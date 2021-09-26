package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import model.SpaceMarineManager;

public class Info extends Command {

    public Info() {
        description = "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
        name = "info";
    }

   @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
       String clean = spaceMarineService.info();
       DTO<String> dto = DTOFactory.getInstance().getDTO();
       dto.setData(clean);
       dto.setStatus(DTOStatus.OK);
       return dto;
//        Object[] localArgs = args.length == 0 ? this.args : args;
//        System.out.printf("Collection class: %s\nCreation date: %s\nElements: %d\n",
//                spaceMarineManager.spaceMarineList.getClass().getName(),
//                SpaceMarineManager.getInitializationDate(),
//                spaceMarineManager.spaceMarineList.size());
//       return null;
   }
}
