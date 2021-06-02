package controller.commands;

import model.SpaceMarineManager;

import java.io.Serializable;

public abstract class CommandImpl implements Command, Serializable {

    private static final long serialVersionUID = 1L;
    protected transient SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
    protected String name;
    protected String description;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
