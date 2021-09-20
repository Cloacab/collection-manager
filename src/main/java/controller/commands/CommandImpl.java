package controller.commands;

import model.SpaceMarineManager;

import java.io.Serializable;

public abstract class CommandImpl implements Command, Serializable {

    private static final long serialVersionUID = 128375098275L;
    protected transient SpaceMarineManager spaceMarineManager;
    protected String name;
    protected String description;
    public Class[] argType = null;
    protected Object[] args = new String[0];

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Class<?>[] getArgType() {
        return argType;
    }

    public void setArgType(Class<?>[] argType) {
        this.argType = argType;
    }

    public void setSpaceMarineManager(SpaceMarineManager manager) {
        this.spaceMarineManager = manager;
    }
}
