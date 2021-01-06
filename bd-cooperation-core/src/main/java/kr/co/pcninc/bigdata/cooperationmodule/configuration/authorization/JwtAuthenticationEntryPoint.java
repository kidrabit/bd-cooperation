package kr.co.pcninc.bigdata.cooperationmodule.configuration.authorization;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import kr.co.pcninc.bigdata.cooperationmodule.AppConstants;
import kr.co.pcninc.bigdata.cooperationmodule.ErrorCode;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException e) throws IOException {

        log.error("Responding with unauthorized error. Message - {}", e.getMessage());

        ErrorCode unAuthorizationCode = (ErrorCode) request.getAttribute(ApiConstants.API_UNAUTHORIZATION_ATTRIBUTE_NAME);

        request.setAttribute(AppConstants.FAILURE_CODE_ATTRIBUTE_NAME, unAuthorizationCode.name());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, unAuthorizationCode.message());
    }
}
