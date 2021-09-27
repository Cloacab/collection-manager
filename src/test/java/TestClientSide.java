import client.Client;
import controller.NotFoundEnumTypeException;
import controller.commands.ICommand;
import controller.commands.Info;
import dto.DTO;
import dto.DTOFactory;
import model.SpaceMarine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.Server;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class TestClientSide {
    private static DTOFactory factory;

    @BeforeAll
    static void setup() {
        factory = DTOFactory.getInstance();
    }

    @Test
    public void shouldDoSmth() throws IOException, ClassNotFoundException {
//        given
        DTOFactory dtoFactory = DTOFactory.getInstance();
        DTO<SpaceMarine> dto = dtoFactory.getDTO();
        dto.setData(new SpaceMarine());
        Server server = new Server();
        Client client = new Client();
//        when
        client.getConnection().send(dto);
        DTO<?> dto1 =  server.getConnection().receive();
        server.getConnection().send(dto1);
        DTO<?> dto2 = client.getConnection().receive();
//        then
        Assertions.assertEquals(dto2, dto);
    }

    @Test
    public void shouldDoSmt() {
        Class myClass = SpaceMarine.class;
        Object obj = "BOLT_RIFLE";
//        Enum anEnum = Enum.valueOf(myClass, obj.toString());
        try {
            System.out.println(fromStringToEnum(obj, myClass));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public <T extends Enum<T>> T fromStringToEnum(Object str, Class<T> enm) throws NotFoundEnumTypeException {
        try {
            return (T) Enum.valueOf(enm, str.toString());
        } catch (Exception e) {
            throw new NotFoundEnumTypeException(e.getMessage());
        }
    }

    @Test
    public void shouldTestDtoTypeTrue() {
        DTO<Object> dto = DTOFactory.getInstance().getDTO();
        SpaceMarine spaceMarine = new SpaceMarine();
        dto.setData(spaceMarine);
        Assertions.assertTrue(dto.getData() instanceof SpaceMarine);
    }

    @Test
    public void shouldTestDtoTypeFalse() {
        DTO<Object> dto = factory.getDTO();
        ICommand ICommand = new Info();
        dto.setData(ICommand);
        Assertions.assertFalse(dto.getData() instanceof SpaceMarine);
    }
}
