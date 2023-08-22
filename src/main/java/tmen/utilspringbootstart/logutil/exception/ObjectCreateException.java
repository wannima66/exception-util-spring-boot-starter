package tmen.utilspringbootstart.logutil.exception;

public class ObjectCreateException extends RuntimeException  {

    public ObjectCreateException() {
        super();
    }
    public ObjectCreateException(String message) {
        super(message);
    }

    public ObjectCreateException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectCreateException(Throwable cause) {
        super(cause);
    }
}
