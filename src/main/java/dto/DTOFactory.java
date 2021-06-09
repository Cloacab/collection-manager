package dto;

public class DTOFactory {

    private static DTOFactory instance = null;

    private DTOFactory() {

    }

    public static DTOFactory getInstance() {
        if (instance == null) {
            instance = new DTOFactory();
        }
        return instance;
    }

    public <T> DTO<T> getDTO() {
        return new DTO<>();
    }

}
