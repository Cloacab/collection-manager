package model.rules;

public class LessThan implements Rules {

    private final long value;

    public LessThan (long value) {
        this.value = value;
    }

    @Override
    public <T> boolean validate(T value) {
        return (long) value < this.value;
    }
}
