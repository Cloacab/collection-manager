import server.Server;

import java.io.IOException;

public class RunServer {
    public static void main(String[] args) {
        try {
            Server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
