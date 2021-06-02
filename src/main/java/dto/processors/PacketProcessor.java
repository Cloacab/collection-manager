package dto.processors;

import client.Client;
import dto.Packet;

import java.lang.reflect.Method;

public class PacketProcessor implements Processor{
    @Override
    public Method process(Packet packet) {
        switch (packet.getMessage()) {
            case "long": {
                try {
                    return Client.class.getDeclaredMethod("receiveLong", int.class);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                    return null;
                }
            }
            case " ": {
                return null;
            }
        }

        return null;
    }
}
