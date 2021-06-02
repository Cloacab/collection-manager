package model;

import java.io.Serializable;

public enum MeleeWeapon implements Serializable {
    CHAIN_SWORD(0),
    POWER_SWORD(1),
    MANREAPER(2),
    POWER_BLADE(3),
    POWER_FIST(4);

    private final int value;

    MeleeWeapon(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
