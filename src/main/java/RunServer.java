import server.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RunServer {
    public static void main(String[] args) {
        Server server = new Server();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(server);
        executorService.execute(server::start);
    }
}
