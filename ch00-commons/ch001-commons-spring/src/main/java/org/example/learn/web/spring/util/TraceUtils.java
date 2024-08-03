package org.example.learn.web.spring.util;

import org.example.learn.web.spring.constant.TraceConstants;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

public class TraceUtils {

    public static String getTraceId() {
        return (String) RequestContextHolder.getRequestAttributes()
                .getAttribute(TraceConstants.TRACE_ID_NAME, RequestAttributes.SCOPE_REQUEST);
    }
}
