package tmen.utilspringbootstart.logutil.exception;

public class LogClassNotFoundException extends RuntimeException {

    public LogClassNotFoundException() {
        super();
    }
    public LogClassNotFoundException(String message) {
        super(message);
    }

    public LogClassNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LogClassNotFoundException(Throwable cause) {
        super(cause);
    }
}
