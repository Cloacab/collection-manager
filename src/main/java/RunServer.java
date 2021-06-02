import server.Server;

import java.io.IOException;
import java.net.SocketException;

public class RunServer {
    public static void main(String[] args) {
        try {
            new Server().run();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
