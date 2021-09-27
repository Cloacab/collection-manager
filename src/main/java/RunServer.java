import server.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RunServer {
    public static void main(String[] args) {
        Server server = new Server();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(server);
//        executorService.submit(server::start);
        executorService.execute(server::start);
//        serverThread.start();
//        server.start();
    }
}
