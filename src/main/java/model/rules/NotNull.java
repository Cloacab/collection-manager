package model.rules;

public class NotNull implements Rules{

    @Override
    public <T> boolean validate(T value) {
        return value != null;
    }
}
