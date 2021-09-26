package controller.executor;

import controller.CommandExecutionFailed;
import controller.commands.Command;
import dto.DTO;
import dto.DTOFactory;
import dto.DTOStatus;
import model.SpaceMarineManager;

public class CommandExecutor implements Executor {
    private static volatile CommandExecutor instance;
    private static final SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();

    private CommandExecutor() {

    }

    public static CommandExecutor getInstance() {
        CommandExecutor current = instance;
        if (current == null) {
            synchronized (CommandExecutor.class) {
                current = instance;
                if (current == null) {
                    instance = current = new CommandExecutor();
                }
            }
        }
        return current;
    }

    @Override
    public synchronized DTO<?> execute(Command command, Object[] args) {
        command.setSpaceMarineManager(spaceMarineManager);
        DTO<?> result = DTOFactory.getInstance().getDTO();
        try {
            result = command.execute(args);
            result.setStatus(DTOStatus.OK);
        } catch (CommandExecutionFailed e) {
            result.setStatus(DTOStatus.NOT_OK);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
