package controller.commands;

import controller.CommandExecutionFailed;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;

public class ExecuteScript extends Command {

    public ExecuteScript() {
        name = "execute_script";
        description = "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
        argType = new Class[]{String.class};
    }

    @Override
    public DTO<?> execute(Object[] args) throws CommandExecutionFailed {
        String executeScript = spaceMarineService.executeScript((String) this.args[0]);
        DTO<String> dto = DTOFactory.getInstance().getDTO();
        dto.setData(executeScript);
        dto.setStatus(DTOStatus.OK);
        return dto;
//        Object[] localArgs = args.length == 0 ? this.args : args;
//        File file = new File((String) localArgs[1]);
//        try(Scanner scriptScanner = new Scanner(file)) {
//            Scanner userInputScanner = UserInputManager.getUserInputScanner();
//            UserInputManager.setUserInputScanner(scriptScanner);
//            UserInputManager.setFromScript(true);
//            System.out.println("===Starting to read script file===");
//            UserInputManager.startListening();
//            System.out.println("===Script file ended===");
//        } catch (FileNotFoundException e) {
//            throw new CommandExecutionFailed("Script file was not found. Try again.");
//        }
//        return factory.getDTO();
    }
}
