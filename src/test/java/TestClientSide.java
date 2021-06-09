import client.Client;
import com.apple.eawt.event.SwipeEvent;
import dto.DTO;
import dto.DTOFactory;
import model.SpaceMarine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import server.Server;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TestClientSide {

    @Test
    public void shouldDoSmth() throws IOException {
//        given
        DTOFactory dtoFactory = DTOFactory.getInstance();
        DTO<SpaceMarine> dto = dtoFactory.getDTO();
        dto.setData(new SpaceMarine());
        Server server = new Server();
        Client client = new Client();
//        when
        client.send(dto);
        DTO<?> dto1 =  server.recieve();
        server.send(dto1);
        DTO<?> dto2 = client.recieve();
//        then
        Assertions.assertEquals(dto2, dto);
    }
}
