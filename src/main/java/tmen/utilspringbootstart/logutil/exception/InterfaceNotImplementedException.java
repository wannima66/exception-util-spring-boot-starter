package tmen.utilspringbootstart.logutil.exception;

public class InterfaceNotImplementedException extends RuntimeException {

    public InterfaceNotImplementedException() {
        super();
    }
    public InterfaceNotImplementedException(String message) {
        super(message);
    }

    public InterfaceNotImplementedException(String message, Throwable cause) {
        super(message, cause);
    }

    public InterfaceNotImplementedException(Throwable cause) {
        super(cause);
    }

    public InterfaceNotImplementedException(Class<?> clazz, Class<?> interfaceClass) {
        super(clazz.getName() + " does not implement " + interfaceClass.getName());
    }
}
