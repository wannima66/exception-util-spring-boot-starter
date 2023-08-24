package com.tmen.exception.util.exception;

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
