package controller.executor;

import controller.CommandExecutionFailed;
import controller.commands.CommandImpl;
import dto.DTO;
import dto.DTOFactory;
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
    public synchronized DTO<?> execute(CommandImpl command, Object[] args) {
        command.setSpaceMarineManager(spaceMarineManager);
        DTO<?> result = DTOFactory.getInstance().getDTO();
        try {
            result = command.execute(args);
            result.setStatus(200);
        } catch (CommandExecutionFailed e) {
            result.setStatus(500);
            result.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
