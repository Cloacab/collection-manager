import client.Client;
import dto.DTO;
import dto.DTOFactory;
import model.SpaceMarine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.Server;

import java.io.IOException;

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
}
