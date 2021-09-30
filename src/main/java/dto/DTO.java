package dto;

import java.io.Serializable;

public class DTO <T> implements Serializable {

    private static final long serialVersionUID = 12837509435276L;

    private T data;
    private DTOStatus status;
    private String message;
    DTO() {

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public DTOStatus getStatus() {
        return status;
    }

    public void setStatus(DTOStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
