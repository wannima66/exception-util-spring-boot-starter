package com.tmen.exception.util.utils;

import cn.hutool.core.lang.caller.CallerUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author guofazhang
 * @date 2023/8/23 11:50
 */
public class TracebackUtil {


    /**
     * Returns the name of the immediately previous caller class
     * on the call stack whose name matches the given class name.
     *
     * @param className the class name to search for
     * @return the name of the immediately previous caller class
     *         whose name matches the given class name, or
     *         Current Caller if no such caller is found.
     */
    public static Class<?> getPreviousCaller(String className) {
        int initDeep = 2;
        if (StringUtils.isBlank(className)) {
            return CallerUtil.getCaller();
        }
        Class<?> caller = CallerUtil.getCaller(initDeep);
        while (caller != null) {
            if (!caller.getSimpleName().equals(className)) {
                return caller;
            }
            caller = CallerUtil.getCaller(++initDeep);
        }
        return caller;
    }


}
