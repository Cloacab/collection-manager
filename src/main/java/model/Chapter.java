package model;

import java.io.Serializable;

public class Chapter implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой

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
                "\n\t\tname='" + name + '\'' +
                "\n\t\tworld='" + world + '\'' +
                "\n\t}\n";
    }
}
