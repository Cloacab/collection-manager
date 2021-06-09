package client;

import controller.commands.Command;
import dto.*;
import model.SpaceMarine;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;

public class Client {
    public final static int SERVICE_PORT = 50001;
    private final static int BUFFER_SIZE = 1024 * 4;
    private HashMap<String, Command> availableCommands;
    private final DatagramSocket clientSocket;
    private final InetAddress IPAddress;
    private final ByteBuffer sendingDataBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    private final ByteBuffer receivingDataBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    private final DTOFactory dtoFactory = DTOFactory.getInstance();

    public Client() throws SocketException, UnknownHostException {
        clientSocket = new DatagramSocket();
        IPAddress = InetAddress.getByName("localhost");
    }

    public void run() throws IOException{
        try{
            handshake();
            System.out.println(availableCommands);
            DullPacket packet = receive();
            int n = Integer.parseInt(packet.getData());
            packet = receiveLong();
            System.out.println(packet);
            // Закройте соединение с сервером через сокет
            clientSocket.close();
        }
        catch(SocketException e) {
            e.printStackTrace();
        }
    }

    public void handshake() throws IOException {
        sendingDataBuffer.clear();
        receivingDataBuffer.clear();
        Packet packet = Packet.getBuilder()
                .setMessage("handshake")
                .build();

        sendingDataBuffer.put(ObjectSerializer.serialize(packet));

        DatagramPacket sendingPacket = new DatagramPacket(
                sendingDataBuffer.array(), sendingDataBuffer.capacity(),
                IPAddress, SERVICE_PORT
        );
        clientSocket.send(sendingPacket);

        DatagramPacket receivingPacket = new DatagramPacket(
                receivingDataBuffer.array(),
                receivingDataBuffer.capacity());
        clientSocket.receive(receivingPacket);
        try {
            HashMap<String, Command> receivedPacket = ObjectSerializer.deserialize(ByteBuffer.wrap(receivingPacket.getData()));
            System.out.println(receivedPacket);
            availableCommands = receivedPacket;

        } catch (ClassNotFoundException e) {
            System.out.println("Unable to deserialize packet. Nothing received.");
            e.printStackTrace();
        }
    }

    private DullPacket receive() throws IOException {
        clearBuffers();
        DatagramPacket receivingPacket = new DatagramPacket(
                receivingDataBuffer.array(),
                receivingDataBuffer.capacity());
        clientSocket.receive(receivingPacket);
        try {
            return ObjectSerializer.deserialize(ByteBuffer.wrap(receivingPacket.getData()));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    private DullPacket receiveLong() {
        clearBuffers();
        DatagramPacket receivingPacket = new DatagramPacket(
                receivingDataBuffer.array(),
                receivingDataBuffer.capacity());
        return null;
    }

    /**
     * Sends serialized data to server
     * @param data serialized data to transfer to server
     * @param packets number of packets to split data to
     */
    private void send(ByteBuffer data, int packets) throws IOException {
        clearBuffers();
        for (int i = 0; i < packets; i++) {
            byte[] piece = new byte[BUFFER_SIZE];
            data.get(piece, 0, BUFFER_SIZE);
            sendPacket(piece);
        }
        sendEnd();
    }

    /**
     * Sends terminating command that ends packet receiving on server
     */
    private void sendEnd() throws IOException {
        DTO<String> end = this.dtoFactory.getDTO();
        end.setData("end");
        ByteBuffer serialize = ObjectSerializer.serialize(end);
        sendPacket(serialize.array());
    }

    /**
     * Sends already prepared piece of packet
     * @param piece serialized piece of packet
     */
    private void sendPacket(byte[] piece) throws IOException {
        DatagramPacket sendingPacket = new DatagramPacket(
                piece, piece.length,
                IPAddress, SERVICE_PORT
        );
        clientSocket.send(sendingPacket);
    }

    /**
     *
     * @param dto containerised data to send to server
     * @return serialized data
     */
    private ByteBuffer prepareDTO(DTO<?> dto) {
        return ObjectSerializer.serialize(dto);
    }

    /**
     *
     * @param data array of serialized data
     * @return number of packages to split data to
     */
    private int countPackets(ByteBuffer data) {
        return (data.capacity() + BUFFER_SIZE - 1) / BUFFER_SIZE;
    }

    private void clearBuffers() {
        sendingDataBuffer.clear();
        receivingDataBuffer.clear();
    }

    public void send(DTO<SpaceMarine> dto) throws IOException {
        ByteBuffer preparedData = prepareDTO(dto);
        int packets = countPackets(preparedData);
        send(preparedData, packets);
    }

    public DTO<?> recieve() {
        return null;
    }
}
