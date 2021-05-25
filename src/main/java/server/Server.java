package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server{
    // Серверный UDP-сокет запущен на этом порту
    public final static int SERVICE_PORT=50001;

    public static void run() throws IOException{
        try{
            // Создайте новый экземпляр DatagramSocket, чтобы получать ответы от клиента
            DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);

      /* Создайте буферы для хранения отправляемых и получаемых данных.
Они временно хранят данные в случае задержек связи */
            byte[] receivingDataBuffer = new byte[1024];
            byte[] sendingDataBuffer = new byte[1024];

            /* Создайте экземпляр UDP-пакета для хранения клиентских данных с использованием буфера для полученных данных */
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Waiting for a client to connect...");

            // Получите данные от клиента и сохраните их в inputPacket
            serverSocket.receive(inputPacket);

            // Выведите на экран отправленные клиентом данные
            String receivedData = new String(inputPacket.getData());
            System.out.println("Sent from the client: " + receivedData);

            /*
             * Преобразуйте отправленные клиентом данные в верхний регистр,
             * Преобразуйте их в байты
             * и сохраните в соответствующий буфер. */
            sendingDataBuffer = receivedData.toUpperCase().getBytes();

            // Получите IP-адрес и порт клиента
            InetAddress senderAddress = inputPacket.getAddress();
            int senderPort = inputPacket.getPort();

            // Создайте новый UDP-пакет с данными, чтобы отправить их клиенту
            DatagramPacket outputPacket = new DatagramPacket(
                    sendingDataBuffer, sendingDataBuffer.length,
                    senderAddress,senderPort
            );

            // Отправьте пакет клиенту
            serverSocket.send(outputPacket);
            // Закройте соединение сокетов
            serverSocket.close();
        }
        catch (SocketException e){
            e.printStackTrace();
        }
    }
}
