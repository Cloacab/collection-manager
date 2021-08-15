package connection;

import dto.DTO;
import dto.DTOFactory;
import dto.ObjectSerializer;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

import static java.lang.Math.min;

public abstract class Connection {

    protected final DTOFactory dtoFactory = DTOFactory.getInstance();
    protected int SERVER_PORT = 50001;
    protected int SENDER_PORT;
    protected final int BUFFER_SIZE = 1024;
    protected DatagramSocket socket = null;
    protected InetAddress IPAddress = null;
    protected InetAddress senderIPAddress;
    protected DatagramChannel datagramChannel;
    private boolean interrupted;

    public Connection() {
        try {
            datagramChannel = DatagramChannel.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Establishes the connection between client and server
     */
    public abstract void connect();

    /**
     * Sends dto packet with variable byte length
     * @param dto - dto object to send
     * @throws IOException - trows exception if it cannot send packet
     */
    public void send(DTO<?> dto) throws IOException {
        ByteBuffer preparedData = prepareDTO(dto);
        int packets = countPackets(preparedData);
        sendManyPackets(preparedData, packets);
    }

    /**
     * Sends serialized data to server
     * @param data serialized data to transfer to server
     * @param packets number of packets to split data to
     */
    private void sendManyPackets(ByteBuffer data, int packets) throws IOException {
        sendPackets(packets);
        for (int i = 0; i < packets; i++) {
            byte[] piece = new byte[BUFFER_SIZE];
            data.get(piece, 0, min(BUFFER_SIZE, data.capacity()-data.position()));
            sendOnePacket(piece);
        }
    }

    private void sendPackets(int packets) throws IOException {
        DTO<Integer> packs = this.dtoFactory.getDTO();
        packs.setData(packets);
        ByteBuffer serialize = ObjectSerializer.serialize(packs);
        sendOnePacket(serialize.array());
    }

    /**
     * Sends terminating command that ends packet receiving on server
     */
    private void sendEnd() throws IOException {
        DTO<String> end = this.dtoFactory.getDTO();
        end.setData("end");
        ByteBuffer serialize = ObjectSerializer.serialize(end);
        sendOnePacket(serialize.array());
    }

    /**
     * Sends already prepared piece of packet
     * @param piece serialized piece of packet
     */
    private void sendOnePacket(byte[] piece) throws IOException {
        DatagramPacket sendingPacket = new DatagramPacket(
                piece, piece.length,
                senderIPAddress, SENDER_PORT
        );
        socket.send(sendingPacket);
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

    /**
     *
     * @return - dto which contains received data
     * @throws IOException - if cannot connect to the server
     * @throws ClassNotFoundException - if something happens with received data
     */
    public DTO<?> receive() throws IOException, ClassNotFoundException, ConnectionTimeoutException {

        Thread timeOut = new Thread(() ->{
            try {
                Thread.sleep(3000);
                throw new ConnectionTimeoutException("Timeout exceeded.");
            } catch (InterruptedException ignored) {
//                e.printStackTrace();
            }
        });

        timeOut.setDaemon(true);
        timeOut.start();

        ArrayList<byte[]> bytes = new ArrayList<>();
        byte[] piece = receiveOne();
        DTO<Integer> deserialize = ObjectSerializer.deserialize(ByteBuffer.wrap(piece));
        int packets = deserialize.getData();
        for (int i = 0; i < packets; i++) {
            piece = receiveOne();
            bytes.add(piece);
        }
        ByteBuffer wholeMessage = ByteBuffer.allocate(bytes.size() * BUFFER_SIZE);
        for(byte[] bytes1 : bytes) {
            wholeMessage.put(bytes1);
        }
        timeOut.interrupt();
        return ObjectSerializer.deserialize(wholeMessage);
    }

    private byte[] receiveOne() throws IOException {
        byte[] receivingData = new byte[BUFFER_SIZE];
        DatagramPacket receivingPacket = new DatagramPacket(
                receivingData,
                receivingData.length);
        socket.receive(receivingPacket);
        this.SENDER_PORT = receivingPacket.getPort();
        InetSocketAddress socketAddress = (InetSocketAddress) receivingPacket.getSocketAddress();
        this.senderIPAddress = socketAddress.getAddress();
        return receivingData;
    }
}
