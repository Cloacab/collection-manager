package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "chapters")
public class Chapter implements Serializable {

    @Id
    private Long id;

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
