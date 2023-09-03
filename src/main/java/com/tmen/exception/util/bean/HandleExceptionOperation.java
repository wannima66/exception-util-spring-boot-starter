package com.tmen.exception.util.bean;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.logging.LogLevel;

@Builder
@Setter
@Getter
@ToString
public class HandleExceptionOperation {

    private String handlerName;

    private String logTemplate;

    private String title;

    private String tags;


    private boolean traceStack;

}
