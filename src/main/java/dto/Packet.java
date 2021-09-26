package dto;

import controller.commands.ICommand;
import model.SpaceMarine;

import java.io.Serializable;
import java.util.HashMap;

public class Packet implements Serializable {
    private ICommand ICommand = null;
    private final HashMap<Integer,SpaceMarine> objects = new HashMap<>();
    private String argument = null;
    private String message = null;

    public ICommand getCommand() {
        return ICommand;
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

    private Packet() {

    }

    public static Builder getBuilder() {
        return new Packet().new Builder();
    }

    public class Builder {
        private Builder() {

        }

        public Builder setCommand(ICommand ICommand) {
            Packet.this.ICommand = ICommand;
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

        public Packet build() {
            return Packet.this;
        }

    }

    @Override
    public String toString() {
        return "Packet{" +
                "\n\tICommand=" + ICommand +
                ",\n\t objects=" + objects +
                ",\n\t argument='" + argument + '\'' +
                ",\n\t message='" + message + '\'' +
                "\n}";
    }
}
