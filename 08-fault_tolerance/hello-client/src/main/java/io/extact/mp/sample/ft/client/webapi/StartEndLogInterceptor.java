package io.extact.mp.sample.ft.client.webapi;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.annotation.Priority;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InterceptorBinding;
import jakarta.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.extact.mp.sample.ft.client.webapi.StartEndLogInterceptor.StartEndLog;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@StartEndLog
public class StartEndLogInterceptor {

    private static final Logger log = LoggerFactory.getLogger(StartEndLogInterceptor.class);

    @AroundInvoke
    public Object obj(InvocationContext ic) throws Exception {
        log.info("Start");
        try {
            var ret = ic.proceed();
            log.info("End:{}", ret.toString());
            return ret;
        } catch (Exception e) {
            log.info("End:{}", e.getClass().getSimpleName());
            return e.getClass().getSimpleName() + "(" + e.getMessage() + ") Occured!";
        }
    }

    @Inherited
    @InterceptorBinding
    @Retention(RUNTIME)
    @Target({ METHOD, TYPE })
    public @interface StartEndLog {
    }
}
