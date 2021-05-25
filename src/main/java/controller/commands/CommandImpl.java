package controller.commands;

import model.SpaceMarineManager;

public abstract class CommandImpl implements Command {

    protected SpaceMarineManager spaceMarineManager = SpaceMarineManager.getInstance();

}
