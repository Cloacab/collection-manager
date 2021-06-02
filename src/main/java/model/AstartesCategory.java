package model;

import java.io.Serializable;

public enum AstartesCategory implements Serializable {
    SCOUT(0),
    TACTICAL(1),
    TERMINATOR(2),
    CHAPLAIN(3),
    HELIX(4);

    private final int value;

    AstartesCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
