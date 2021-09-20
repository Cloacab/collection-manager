import client.Client;
import dto.DTO;
import dto.DTOFactory;
import model.MeleeWeapon;
import model.SpaceMarine;
import model.Weapon;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class TestClientSide {

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
//        Enum anEnum = Enum.valueOf(myClass, obj.toStrinng());
        try {
            System.out.println(fromStringToEnum(obj, myClass));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T extends Enum<T>> T fromStringToEnum(Object str, Class<T> enm) throws Exception {
        try {
            return (T) Enum.valueOf(enm, str.toString());
        } catch (Exception e) {
            System.out.println("ti pidor");
            throw new Exception("not ");
        }
    }
}
