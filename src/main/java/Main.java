import controller.CommandExecutionFailed;
import controller.commands.Command;
import controller.commands.Show;
import model.MeleeWeapon;
import model.SpaceMarine;
import model.SpaceMarineManager;
import view.UserInputManager;

import java.io.File;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
        spaceMarineManager.readFromCsv(args[0]);
        UserInputManager.startListening();

    }
}
