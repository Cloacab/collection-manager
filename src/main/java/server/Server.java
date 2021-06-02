package server;

import controller.CommandManager;
import controller.commands.Command;
import dto.ObjectSerializer;
import model.SpaceMarine;
import model.SpaceMarineManager;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class Server extends Thread {
    // Серверный UDP-сокет запущен на этом порту
    public final static int SERVICE_PORT=50001;
    private final static int BUFFER_SIZE = 1024 * 4;
    private SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
    private CommandManager commandManager = CommandManager.getInstance();

    @Override
    public void run(){



        try{
            // Создайте новый экземпляр DatagramSocket, чтобы получать ответы от клиента
            DatagramSocket serverSocket = new DatagramSocket(SERVICE_PORT);

      /* Создайте буферы для хранения отправляемых и получаемых данных.
Они временно хранят данные в случае задержек связи */
            byte[] receivingDataBuffer = new byte[BUFFER_SIZE];
            byte[] sendingDataBuffer = new byte[BUFFER_SIZE];

            /* Создайте экземпляр UDP-пакета для хранения клиентских данных с использованием буфера для полученных данных */
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
            System.out.println("Waiting for a client to connect...");

            // Получите данные от клиента и сохраните их в inputPacket
            serverSocket.receive(inputPacket);

            // Выведите на экран отправленные клиентом данные
            ByteBuffer serializedData = ByteBuffer.wrap(inputPacket.getData());
            try {
                SpaceMarine spaceMarine = ObjectSerializer.deserialize(serializedData);
                System.out.println("Received from client: \n" + spaceMarine);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(Arrays.toString(new SocketAddress[]{inputPacket.getSocketAddress()}));

            /*
             * Преобразуйте отправленные клиентом данные в верхний регистр,
             * Преобразуйте их в байты
             * и сохраните в соответствующий буфер. */

            HashMap<String, Command> availableCommands = CommandManager.getInstance().getAvailableCommands();
            ByteBuffer serializedCommands = ObjectSerializer.serialize(availableCommands);
            System.out.println(serializedCommands.toString());

            // Получите IP-адрес и порт клиента
            InetAddress senderAddress = inputPacket.getAddress();
            int senderPort = inputPacket.getPort();

            // Создайте новый UDP-пакет с данными, чтобы отправить их клиенту
            DatagramPacket outputPacket = new DatagramPacket(
                    serializedCommands.array(), serializedCommands.capacity(),
                    senderAddress,senderPort
            );

            // Отправьте пакет клиенту
            serverSocket.send(outputPacket);
            // Закройте соединение сокетов
            serverSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
