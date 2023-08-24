package com.tmen.exception.util.function;

@FunctionalInterface
public interface SupplierAction<T> {

    T perform();
}
