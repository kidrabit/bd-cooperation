package kr.co.pcninc.bigdata.cooperationmodule.configuration.interceptor;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import kr.co.pcninc.bigdata.cooperationmodule.common.Constants;
import kr.co.pcninc.bigdata.cooperationmodule.utils.DateUtils;
import kr.co.pcninc.bigdata.cooperationmodule.utils.StringUtils;


@Slf4j
public class RequestTraceInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) {
        final String traceId = String.valueOf(UUID.randomUUID());
        request.setAttribute(ApiConstants.API_TRACE_ID_ATTRIBUTE_NAME, traceId);

        final StringBuilder logString = new StringBuilder();

        String token = StringUtils.emptyTo(request.getHeader(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME));

        logString.append("REQUEST : ");
        logString.append("\n  TRACE ID : {}");
        logString.append("\n  URL : {}");
        logString.append("\n  PARAMETER : {}");
        logString.append("\n  TOKEN : {}");
        logString.append("\n  METHOD : {}");
        logString.append("\n  DATETIME : {}");

        log.info(logString.toString(), traceId, request.getRequestURI(), request.getQueryString(), token, request.getMethod(),
            DateUtils.today(Constants.DEFAULT_DATE_PREETY_FORMAT));

        return true;
    }
}
