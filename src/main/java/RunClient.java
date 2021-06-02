import client.Client;

import java.io.IOException;

public class RunClient {
    public static void main(String[] args) {
        try {
            new Client().run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
