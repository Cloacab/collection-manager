package connection;

public class ConnectionTimeoutException extends RuntimeException {
    public ConnectionTimeoutException(String message) {
        super(message);
    }
}
