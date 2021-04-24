package model;

public class Chapter {
    @Rules @UserInput
    private String name; //Поле не может быть null, Строка не может быть пустой

    @Rules(nullable = true) @UserInput
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
