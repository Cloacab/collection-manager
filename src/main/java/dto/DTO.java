package dto;

import java.io.Serializable;
import java.lang.reflect.Array;

public class DTO <T> implements Serializable {

    private T data;
    private int status;
    DTO() {

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
