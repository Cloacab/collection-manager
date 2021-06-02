package server;

import controller.CommandManager;
import controller.commands.Command;
import dto.ObjectSerializer;
import dto.Packet;
import model.SpaceMarine;
import model.SpaceMarineManager;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.TreeMap;

import static java.lang.Integer.min;

public class Server{
    // Серверный UDP-сокет запущен на этом порту
    public final static int SERVICE_PORT=50001;
    private int currentUserPort;
    private final static int BUFFER_SIZE = 1024 * 4;
    private final static int BATCH_SIZE = 256;
    private final SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
    private final CommandManager commandManager = CommandManager.getInstance();
    private final DatagramSocket serverSocket;
    private final ByteBuffer sendingDataBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    private final ByteBuffer receivingDataBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    public Server() throws SocketException {

        serverSocket = new DatagramSocket(SERVICE_PORT);
    }

    public void run(){


        try{
            // Создайте новый экземпляр DatagramSocket, чтобы получать ответы от клиента

      /* Создайте буферы для хранения отправляемых и получаемых данных.
Они временно хранят данные в случае задержек связи */
            /* Создайте экземпляр UDP-пакета для хранения клиентских данных с использованием буфера для полученных данных */
            DatagramPacket inputPacket = new DatagramPacket(receivingDataBuffer.array(),
                    receivingDataBuffer.capacity());
            System.out.println("Waiting for a client to connect...");

            // Получите данные от клиента и сохраните их в inputPacket
            serverSocket.receive(inputPacket);

            Packet packet = ObjectSerializer.deserialize(ByteBuffer.wrap(inputPacket.getData()));
            System.out.println(packet);

            if (packet.getMessage().equals("handshake")) {
                clearBuffers();
                ByteBuffer serializedCommands = ObjectSerializer.serialize(commandManager.getAvailableCommands());
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
            }

            clearBuffers();
            Packet sendingPacket = Packet.getBuilder()
                    .addObject(1, new SpaceMarine())
                    .setMessage("fuck you")
                    .build();
            ByteBuffer serializedPacket = (ObjectSerializer.serialize(sendingPacket));

            System.out.println(sendingDataBuffer.toString());

            sendBigPacket(serializedPacket, inputPacket.getPort(), inputPacket.getAddress());

            /*
             * Преобразуйте отправленные клиентом данные в верхний регистр,
             * Преобразуйте их в байты
             * и сохраните в соответствующий буфер. */


            // Закройте соединение сокетов
            serverSocket.close();
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }

    private Packet receiveOne() throws IOException, ClassNotFoundException {

        clearBuffers();

        DatagramPacket receivingPacket = new DatagramPacket(
                receivingDataBuffer.array(),
                receivingDataBuffer.capacity());
        serverSocket.receive(receivingPacket);

        clearBuffers();

        return ObjectSerializer.deserialize(ByteBuffer.wrap(receivingPacket.getData()));
    }

    private void sendBigPacket (ByteBuffer bigPacket, int port, InetAddress address) {
        int n = bigPacket.capacity() / BATCH_SIZE + 1;
        Packet packet = Packet.getBuilder()
                .setMessage("long")
                .setArgument(String.valueOf(n))
                .build();
        ByteBuffer serializedPacket = ObjectSerializer.serialize(packet);
        send(serializedPacket.array(), port, address);
        for (int i = 0; i < n; i++) {
            byte[] array = new byte[BATCH_SIZE];
            bigPacket.get(array, 0, min(BATCH_SIZE, bigPacket.remaining()));
            send(array, port, address);
        }
    }

    private void send(byte[] piece, int port, InetAddress address) {
        DatagramPacket outputPacket = new DatagramPacket(
                piece, piece.length,
                address, port
        );
        try {
            serverSocket.send(outputPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearBuffers() {
        sendingDataBuffer.clear();
        receivingDataBuffer.clear();
    }
}
