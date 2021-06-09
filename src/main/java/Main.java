import controller.commands.Command;
import controller.commands.Show;
import dto.DTO;
import dto.DTOFactory;
import model.SpaceMarine;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {

//        SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
//        SpaceMarineManager.setFileName(args.length > 0 ? args[0] : "SpaceMarineList.csv");
//        spaceMarineManager.readFromCsv();
//        UserInputManager.startListening();

        DTOFactory dtoFactory = DTOFactory.getInstance();
        DTO<SpaceMarine> dto = dtoFactory.getDTO();
        DTO<Command> dto1 = dtoFactory.getDTO();
        dto1.setData(new Show());
        dto.setData(new SpaceMarine());
        System.out.println(dto.getData().toString());
        System.out.println(dto1.getData().getClass());
        List<Integer> a = new ArrayList<>();
        a.add(5);
        a.add(3);
        a.add(7);
        System.out.println(a);
        a.sort(Comparator.comparingInt(x -> -x));
        System.out.println(a);
    }

}
