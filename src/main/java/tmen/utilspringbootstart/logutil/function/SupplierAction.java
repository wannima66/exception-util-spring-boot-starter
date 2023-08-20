package tmen.utilspringbootstart.logutil.function;

@FunctionalInterface
public interface SupplierAction<T> {

    T perform();
}
