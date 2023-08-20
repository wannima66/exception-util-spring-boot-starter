package tmen.utilspringbootstart.logutil.exception;

public class DuplicateBeanException extends RuntimeException {

    public DuplicateBeanException() {
        super();
    }
    public DuplicateBeanException(String message) {
        super(message);
    }

    public DuplicateBeanException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateBeanException(Throwable cause) {
        super(cause);
    }
}
