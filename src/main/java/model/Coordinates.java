package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "coordinates")
public class Coordinates implements Serializable {

    @Id
    private Long id;

    private long x; //Максимальное значение поля: 411
    private float y;

    public Coordinates(long x, float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates(){

    }

    public float getY() {
        return y;
    }

    public long getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}