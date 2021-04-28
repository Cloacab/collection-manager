package model;

import model.rules.NotNull;
import model.rules.UserInput;

public class Chapter {
    @UserInput @NotNull
    private String name; //Поле не может быть null, Строка не может быть пустой

    @UserInput
    private String world; //Поле может быть null

    public Chapter() {

    }

    public Chapter(String name, String world) {
        this.name = name;
        this.world = world;
    }

    public String getName() {
        return name;
    }

    public String getWorld() {
        return world;
    }

    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", world='" + world + '\'' +
                '}';
    }
}
