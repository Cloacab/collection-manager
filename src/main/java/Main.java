import controller.CommandExecutionFailed;
import controller.CommandManager;
import controller.commands.Command;
import controller.commands.CommandImpl;
import model.SpaceMarine;
import model.SpaceMarineManager;
import org.reflections.Reflections;
import view.UserInputManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {

        SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
        SpaceMarineManager.setFileName("SpaceMarineList.csv");
        spaceMarineManager.readFromCsv();
        UserInputManager.startListening();

//        CommandManager commandManager = CommandManager.getInstance();
//        System.out.println(commandManager.getAvailableCommands().toString());
//
//        try {
//            commandManager.getAvailableCommands().get("info").execute(new String[] {});
//        } catch (CommandExecutionFailed commandExecutionFailed) {
//            commandExecutionFailed.printStackTrace();
//        }
    }

}
