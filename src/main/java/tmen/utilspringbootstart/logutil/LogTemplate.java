package tmen.utilspringbootstart.logutil;

import tmen.utilspringbootstart.logutil.cache.ExceptionAdapterCache;
import tmen.utilspringbootstart.logutil.exceptionadapter.ExceptionHandler;
import tmen.utilspringbootstart.logutil.function.ConsumerAction;
import tmen.utilspringbootstart.logutil.function.SupplierAction;

import java.util.Optional;
import java.util.function.Function;

public class LogTemplate {


    public <T> T handleException(SupplierAction<T> supplier) {
        T  res = null;
        try {
            res = supplier.perform();
        } catch (Exception e) {
            Optional.ofNullable(getAdapter(e)).ifPresent(adapter -> adapter.handleException(e));
        }
        return res;
    }
    public void handleException(ConsumerAction action) {
        try {
            action.perform();
        } catch (Exception e) {
            Optional.ofNullable(getAdapter(e)).ifPresent(adapter -> adapter.handleException(e));
        }
    }

    public <T, R> R handleException(T input, Function<T,R> function) {
        R res = null;
        try {
            res = function.apply(input);
        } catch (Exception e) {
            Optional.ofNullable(getAdapter(e)).ifPresent(adapter -> adapter.handleException(e));
        }
        return res;
    }


    public void handleException(Exception e) {
        Optional.ofNullable(getAdapter(e)).ifPresent(adapter ->adapter.handleException(e));
    }


    private ExceptionHandler getAdapter(Exception e) {
        if (e == null) {
            return null;
        }
        String name = exceptionSimpleName(e);
        return ExceptionAdapterCache.get(name);
    }

    private String exceptionSimpleName(Exception e) {
        return e.getClass().getSimpleName();
    }

}
