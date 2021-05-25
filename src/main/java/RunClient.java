import client.Client;

import java.io.IOException;

public class RunClient {
    public static void main(String[] args) {
        try {
            Client.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
