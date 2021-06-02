package dto;

import controller.commands.Command;
import model.SpaceMarine;

import java.io.Serializable;
import java.util.HashMap;

public class Packet implements Serializable {
    private Command command = null;
    private HashMap<Integer,SpaceMarine> objects = null;
    private String argument = null;
    private String message = null;
    private int clientPort;

    public Command getCommand() {
        return command;
    }

    public HashMap<Integer, SpaceMarine> getObject() {
        return objects;
    }

    public String getArgument() {
        return argument;
    }

    public String getMessage() {
        return message;
    }

    public int getClientPort() {
        return clientPort;
    }

    private Packet() {

    }

    public static Builder getBuilder() {
        return new Packet().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder setCommand(Command command) {
            Packet.this.command = command;
            return this;
        }

        public Builder addObject(Integer key ,SpaceMarine object) {
            Packet.this.objects.put(key, object);
            return this;
        }

        public Builder setArgument(String argument) {
            Packet.this.argument = argument;
            return this;
        }

        public Builder setMessage(String message) {
            Packet.this.message = message;
            return this;
        }

        public Builder setClientPort(int port) {
            Packet.this.clientPort = port;
            return this;
        }

        public Packet build() {
            return Packet.this;
        }

    }

    @Override
    public String toString() {
        return "Packet{" +
                "\n\tcommand=" + command +
                "\n\t, objects=" + objects +
                "\n\t, argument='" + argument + '\'' +
                "\n\t, message='" + message + '\'' +
                "\n\t, clientPort=" + clientPort +
                "\n}";
    }
}
