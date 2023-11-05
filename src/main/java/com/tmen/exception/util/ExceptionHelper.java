package com.tmen.exception.util;


import com.tmen.exception.util.context.HandleExceptionContext;
import com.tmen.exception.util.exceptionHandler.ExceptionHandler;
import com.tmen.exception.util.utils.TracebackUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import com.tmen.exception.util.exceptionHandler.ExceptionHandlerRegister;
import com.tmen.exception.util.function.ConsumerAction;
import com.tmen.exception.util.function.SupplierAction;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class ExceptionHelper {

    public <R> R handleExceptionCustom(SupplierAction<R> supplier, Consumer<? super Throwable> h) {
        Pair<R, Exception> result = execute(supplier);
        Optional.ofNullable(result.getRight()).ifPresent(h);
        return result.getLeft();
    }

    public <R> R handleExceptionCustom(SupplierAction<R> supplier, Function<? super Throwable, R> h) {
        Pair<R, Exception> result = execute(supplier);
        R res = result.getLeft();
        boolean present = Optional.ofNullable(result.getRight()).isPresent();
        if (present) {
            res = h.apply(result.getRight());
        }
        return res;
    }

    public <T> T handleExceptionAfter(SupplierAction<T> supplier, Function<T,T> h) {
        return handleException(() -> h.apply(handleException(null, supplier, null)));
    }

    public <T> T handleException(SupplierAction<T> supplier) {
        return handleException(null, supplier, null);
    }


    public <T> T handleExceptionAfter(String handlerName, SupplierAction<T> supplier, Function<T,T> h) {
        return handleException(() -> h.apply(handleException(handlerName, supplier)));
    }
    public <T> T handleException(String handlerName, SupplierAction<T> supplier) {
        Pair<T, Exception> result = execute(supplier);
        Optional.ofNullable(result.getRight()).ifPresent(e -> handleException(handlerName, e, StringUtils.EMPTY));
        return result.getLeft();
    }

    public <T> T handleExceptionAfter(String handlerName, SupplierAction<T> supplier, String title, Function<T,T> h) {
        return h.apply(handleException(handlerName, supplier, title));
    }

    public <T> T handleException(String handlerName, SupplierAction<T> supplier, String title) {
        Pair<T, Exception> result = execute(supplier);
        Optional.ofNullable(result.getRight()).ifPresent(e -> handleException(handlerName, e, title));
        return result.getLeft();
    }

    public <T> T handleExceptionAfter(String handlerName, SupplierAction<T> supplier, String msg, String title, Function<T,T> h) {
        return h.apply(handleException(handlerName, supplier, msg, title));
    }

    public <T> T handleException(String handlerName, SupplierAction<T> supplier, String msg, String title) {
        Pair<T, Exception> result = execute(supplier);
        Optional.ofNullable(result.getRight()).ifPresent(e -> handleException(handlerName, e, msg, title));
        return result.getLeft();
    }
    public  void handleExceptionAfter(ConsumerAction action, ConsumerAction h) {
        handleException(action);
        h.perform();
    }
    public void handleException(ConsumerAction action) {
        handleException(null, action, null);
    }

    public void handleExceptionAfter(String handlerName, ConsumerAction action, ConsumerAction h) {
        handleException(handlerName, action);
        h.perform();
    }
    public void handleException(String handlerName, ConsumerAction action) {
        Optional.ofNullable(execute(action)).ifPresent(e -> handleException(handlerName, e, StringUtils.EMPTY));
    }

    public void handleExceptionAfter(String handlerName, ConsumerAction action, String title, ConsumerAction h) {
        handleException(handlerName, action, title);
        h.perform();
    }
    public void handleException(String handlerName, ConsumerAction action, String title) {
        Optional.ofNullable(execute(action)).ifPresent(e -> handleException(handlerName, e, title));
    }

    public void handleExceptionAfter(String handlerName, ConsumerAction action, String msg, String title, ConsumerAction h) {
        handleException(handlerName, action, msg, title);
        h.perform();
    }
    public void handleException(String handlerName, ConsumerAction action, String msg, String title) {
        Optional.ofNullable(execute(action)).ifPresent(e -> handleException(handlerName, e, title, msg));
    }

    public void handleException(String handlerName, Exception e) {
        Optional.ofNullable(getHandler(handlerName, e)).ifPresent(adapter -> adapter.handleException(e));
    }

    public void handleException(String handlerName, Exception e, String title) {
        Optional.ofNullable(getHandler(handlerName, e)).ifPresent(adapter -> adapter.handleException(title, e));
    }

    public void handleException(String handlerName, String msg, String title) {
        Optional.ofNullable(getHandler(handlerName, null)).ifPresent(adapter -> adapter.handleException(title, msg));
    }

    public void handleException(String handlerName, Exception e, String msg, String title) {
        String message = String.format("msg:[%s], cause:[%s]", msg, e);
        Optional.ofNullable(getHandler(handlerName, e)).ifPresent(adapter -> adapter.handleException(title, message));
    }


    private <T> Pair<T, Exception> execute(SupplierAction<T> supplier) {
        Exception exception = null;
        T res = null;
        try {
            res = supplier.perform();
        } catch (Exception e) {
            setCaller();
            exception = e;
        }
        return Pair.of(res, exception);
    }

    private Exception execute(ConsumerAction action) {

        Exception exception = null;
        try {
            action.perform();
        } catch (Exception e) {
            setCaller();
            exception = e;
        }
        return exception;
    }

    private ExceptionHandler getHandler(String name, Exception e) {
        return StringUtils.isNotBlank(name) ? ExceptionHandlerRegister.get(name, exceptionSimpleName(e)) : getHandler(e);
    }

    private ExceptionHandler getHandler(Exception e) {
        if (e == null) {
            return null;
        }
        String name = exceptionSimpleName(e);
        return ExceptionHandlerRegister.get(name);
    }

    private String exceptionSimpleName(Exception e) {
        return e.getClass().getSimpleName();
    }

    private void setCaller() {
        Class<?> callerCaller = TracebackUtil.getPreviousCaller(getClass().getSimpleName());
        if (getClass().getSimpleName().equals(callerCaller.getSimpleName())) {
            return;
        }
        HandleExceptionContext.putCallerClassName(callerCaller.getSimpleName());
    }

}
