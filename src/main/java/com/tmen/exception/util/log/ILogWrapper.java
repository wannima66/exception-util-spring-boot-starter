package com.tmen.exception.util.log;

/**
 * @author guofazhang
 * @date 2023/8/22 16:47
 */
public interface ILogWrapper {

    void info(String message);


    void info(String message, Throwable throwable);


    void warn(String message);

    void warn(String message, Throwable throwable);


    void error(String message);

    void error(String message, Throwable throwable);

}
