package connection;

import dto.DTO;

import java.io.IOException;
import java.net.*;

public class ClientConnection extends Connection{

    private final InetSocketAddress serverSocket = InetSocketAddress.createUnresolved("localhost", SERVER_PORT);

    public ClientConnection() {
        super();
        try {
            this.socket = new DatagramSocket();
            this.IPAddress = InetAddress.getByName("localhost");
            this.SENDER_PORT = 50001;
            this.senderIPAddress = InetAddress.getByName("localhost");
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connect() {

        try {
            datagramChannel.bind(null);
            datagramChannel.connect(serverSocket);
//            datagramChannel.re
        } catch (IOException e) {
            e.printStackTrace();
        }

        int maxConnectionsAttempts = 5;
        int currentConnectionsAttempts = 0;
        boolean successful = false;
        DTO<String> handshake = dtoFactory.getDTO();
        handshake.setData("handshake");
        do {
            System.out.println("Try to connect to server...");
            try {
                send(handshake);
                DTO<?> receive = receive();
                if (receive.getData().equals("handshake")){
                    successful = true;
                }
                System.out.println("Successfully connected to the server.");
                break;
            } catch (ConnectionTimeoutException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            currentConnectionsAttempts++;
            try {
                System.out.printf("Try to reconnect in 3 seconds, attempts rest: %d", maxConnectionsAttempts - currentConnectionsAttempts);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (!successful && currentConnectionsAttempts < maxConnectionsAttempts);
        if (currentConnectionsAttempts >= maxConnectionsAttempts) {
            System.out.println("Cannot connect to the server now. Restart server and client and try again.");
        }
    }
}
