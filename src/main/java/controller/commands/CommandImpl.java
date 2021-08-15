package controller.commands;

import model.SpaceMarineManager;

import java.io.Serializable;

public abstract class CommandImpl implements Command, Serializable {

    private static final long serialVersionUID = 1L;
    protected transient SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
    protected String name;
    protected String description;
    protected String[] args = new String[0];

    public String[] getArgs() {
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

}
