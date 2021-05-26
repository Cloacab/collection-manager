package controller.commands;

import model.SpaceMarineManager;

public abstract class CommandImpl implements Command {

    protected SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();
    protected String name;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    protected String description;
}
