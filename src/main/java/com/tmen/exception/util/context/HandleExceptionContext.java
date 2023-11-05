package com.tmen.exception.util.context;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class HandleExceptionContext {


    private static final InheritableThreadLocal<Deque<String>> CALLER_NAME_STACK = new InheritableThreadLocal<>();

    private static final InheritableThreadLocal<Deque<Map<String, Object>>> VARIABLE_MAP_STACK = new InheritableThreadLocal<>();

    private static final InheritableThreadLocal<Map<String, Object>> GLOBAL_VARIABLE_MAP = new InheritableThreadLocal<>();
    private HandleExceptionContext() {
        throw new IllegalStateException("Utility class");
    }

    public static void putCallerClassName(String name) {
        if (CALLER_NAME_STACK.get() == null) {
            Deque<String> stack = new ArrayDeque<>();
            CALLER_NAME_STACK.set(stack);
        }
        CALLER_NAME_STACK.get().push(name);
    }

    public static String getCallerClassName() {
        Deque<String> deque = CALLER_NAME_STACK.get();
        if (deque == null) {
            return null;
        }
        return deque.poll();
    }

    public static void putVariable(String name, Object value) {
        if (VARIABLE_MAP_STACK.get() == null) {
            Deque<Map<String, Object>> stack = new ArrayDeque<>();
            VARIABLE_MAP_STACK.set(stack);
        }
        Deque<Map<String, Object>> mapStack = VARIABLE_MAP_STACK.get();
        if (mapStack.isEmpty()) {
            VARIABLE_MAP_STACK.get().push(new HashMap<>());
        }
        VARIABLE_MAP_STACK.get().element().put(name, value);
    }

    public static void putGlobalVariable(String name, Object value) {
        if (GLOBAL_VARIABLE_MAP.get() == null) {
            GLOBAL_VARIABLE_MAP.set(new HashMap<>());
        }
        GLOBAL_VARIABLE_MAP.get().put(name, value);
    }

    public static Object getVariable(String key) {
        Map<String, Object> variableMap = VARIABLE_MAP_STACK.get().peek();
        return variableMap == null ? null : variableMap.get(key);
    }

    public static Map<String, Object> getGlobalVariableMap() {
        return GLOBAL_VARIABLE_MAP.get();
    }
    public static Map<String, Object> getVariables() {
        Deque<Map<String, Object>> mapStack = VARIABLE_MAP_STACK.get();
        return mapStack.peek();
    }

    public static void clear() {
        if (VARIABLE_MAP_STACK.get() != null) {
            VARIABLE_MAP_STACK.get().pop();
        }
    }

    public static void clearGlobal() {
        if (GLOBAL_VARIABLE_MAP.get() != null) {
            GLOBAL_VARIABLE_MAP.get().clear();
        }
    }

    public static void putEmptySpan() {
        Deque<Map<String, Object>> mapStack = VARIABLE_MAP_STACK.get();
        if (mapStack == null) {
            Deque<Map<String, Object>> stack = new ArrayDeque<>();
            VARIABLE_MAP_STACK.set(stack);
        }
        VARIABLE_MAP_STACK.get().push(new HashMap<>());
    }
}
