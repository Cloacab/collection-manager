package model.rules;

public interface Rules {
    public <T> boolean validate(T value);
}
