package model.rules;

public class MoreThan implements Rules{

    private final long value;

    public MoreThan(long value) {
        this.value = value;
    }

    @Override
    public <T> boolean validate(T value) {
        return (long) value > this.value;
    }
}
