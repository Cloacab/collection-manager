package model;

import java.io.Serializable;

public class Coordinates implements Serializable {
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