package dto;

public enum DTOStatus {
    OK(200),
    NOT_OK(500);

    private final int value;

    DTOStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
