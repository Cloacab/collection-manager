package controller.commands;

import controller.SpaceMarineService;
import model.SpaceMarineManager;

import java.io.Serializable;

public abstract class Command implements ICommand, Serializable {

    private static final long serialVersionUID = 128375098276L;
    protected transient SpaceMarineManager spaceMarineManager;
    protected final static SpaceMarineService spaceMarineService = SpaceMarineService.getInstance();
    protected String name;
    protected String description;
    public Class[] argType = null;
    protected Object[] args;

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
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
