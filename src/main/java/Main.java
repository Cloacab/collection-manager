import model.SpaceMarineManager;
import view.UserInputManager;
import au.com.bytecode.opencsv.CSVReader;

public class Main {
    public static void main(String[] args) {

        SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
        spaceMarineManager.readFromCsv(args[0]);
        UserInputManager.startListening();

    }
}
