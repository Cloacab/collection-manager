import model.SpaceMarine;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import server.Server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class RunServer {
    private static SessionFactory sessionFactory;
    public static void main(String[] args) throws Exception {
        setUp();
        Server server = new Server();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(server);
//        executorService.submit(server::start);
        executorService.execute(server::start);
//        serverThread.start();
//        server.start();
    }

    protected static void setUp() throws Exception {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(new SpaceMarine());
            session.save(new SpaceMarine());
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
            // so destroy it manually.
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }
}
