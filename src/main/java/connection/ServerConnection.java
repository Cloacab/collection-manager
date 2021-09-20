package connection;

import dto.DTO;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServerConnection extends Connection{

    public ServerConnection() {
        super();
        try {
            this.socket = new DatagramSocket(SERVER_PORT);
//            this.socket.setSoTimeout(2000);
//            this.IPAddress = socket.getInetAddress();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connect() {
        boolean successful = false;
        DTO<String> handshake = dtoFactory.getDTO();
        handshake.setData("handshake");
        do {
            try {
                DTO<?> receive = receive();
                if (receive.getData().equals("handshake")){
                    successful = true;
//                    this.senderIPAddress = socket.getInetAddress();
//                    this.SERVER_PORT = socket.getPort();
                } else {
                    throw new IOException();
                }
                System.out.println("Client has connected to the server.");
                send(handshake);
            } catch (IOException e) {
                System.out.println("Client cannot connect to the server.");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        while (!successful);
    }
}
