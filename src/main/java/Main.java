import model.SpaceMarineManager;
import view.UserInputManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
        SpaceMarineManager.setFileName(args[0]);
        spaceMarineManager.readFromCsv();
        UserInputManager.startListening();

    }
}
