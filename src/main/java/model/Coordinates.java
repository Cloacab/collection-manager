package model;

import model.rules.LessThan;
import model.rules.UserInput;

public class Coordinates {
    @UserInput @LessThan(412)
    private long x; //Максимальное значение поля: 411
    @UserInput
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