package com.tmen.exception.util;

import com.tmen.exception.util.annotation.EnableExceptionHandle;
import com.tmen.exception.util.autoconfigure.HandleExceptionAutoConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.lang.Nullable;

public class ExceptionHandleConfigureSelector extends AdviceModeImportSelector<EnableExceptionHandle> {
    @Override
    @Nullable
    public String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                return new String[]{AutoProxyRegistrar.class.getName(), HandleExceptionAutoConfiguration.class.getName()};
            case ASPECTJ:
                return new String[] {HandleExceptionAutoConfiguration.class.getName()};
            default:
                return null;
        }
    }
}
