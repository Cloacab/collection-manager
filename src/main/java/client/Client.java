package client;

import controller.commands.Command;
import dto.ObjectSerializer;
import dto.Packet;
import model.SpaceMarine;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
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

    public Client() throws SocketException, UnknownHostException {
        clientSocket = new DatagramSocket();
        IPAddress = InetAddress.getByName("localhost");
    }

    public void run() throws IOException{
        try{
            handshake();
            System.out.println(availableCommands);
            Packet packet = receiveOne();
            int n = Integer.parseInt(packet.getArgument());
            packet = receiveLong();
            System.out.println(packet);
            // Закройте соединение с сервером через сокет
            clientSocket.close();
        }
        catch(SocketException | ClassNotFoundException e) {
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

    private Packet receiveOne() throws IOException, ClassNotFoundException {

        clearBuffers();
        DatagramPacket receivingPacket = new DatagramPacket(
                receivingDataBuffer.array(),
                receivingDataBuffer.capacity());
        clientSocket.receive(receivingPacket);

        clearBuffers();

        return ObjectSerializer.deserialize(ByteBuffer.wrap(receivingPacket.getData()));
    }

    private ArrayList<Packet> receiveMultiple() throws IOException, ClassNotFoundException {

        ArrayList<Packet> packets = new ArrayList<>();

        Packet packet = receiveOne();
        packets.add(packet);
        long start = System.currentTimeMillis();
        while (!packet.getMessage().equals("end") || System.currentTimeMillis() - start > 500) {
            packet = receiveOne();
            packets.add(packet);
            start = System.currentTimeMillis();
        }
        return packets;
    }

    private Packet receiveLong() throws IOException, ClassNotFoundException {
        clearBuffers();
        ArrayList<Byte> byteArray = new ArrayList<Byte>();
        Packet packet = receiveOne();
        int n = Integer.parseInt(packet.getArgument());
        for (int i = 0; i < n; i++) {
            clearBuffers();
            DatagramPacket receivingPacket = new DatagramPacket(
                    receivingDataBuffer.array(),
                    receivingDataBuffer.capacity());
            clientSocket.receive(receivingPacket);
            for (Byte b :
                    receivingPacket.getData()) {
                byteArray.add(b);
            }
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(byteArray.size());
        for (Byte b :
                byteArray) {
        byteBuffer.put(b);
        }
        return (Packet) ObjectSerializer.deserialize(byteBuffer);
    }

    private ArrayList<Packet> receive() throws IOException, ClassNotFoundException {
        clearBuffers();
        Packet packet = receiveOne();
        switch (packet.getMessage()){
            case "begin": {
                return receiveMultiple();
            }
            case "long": {
                ArrayList<Packet> packets = new ArrayList<>();
                packets.add(receiveLong());
                return packets;
            }
            default: {
                ArrayList<Packet> packets = new ArrayList<>();
                packets.add(packet);
                return packets;
            }
        }
    }

    private void clearBuffers() {
        sendingDataBuffer.clear();
        receivingDataBuffer.clear();
    }

}
