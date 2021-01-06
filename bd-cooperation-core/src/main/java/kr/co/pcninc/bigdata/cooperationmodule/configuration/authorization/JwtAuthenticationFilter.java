package kr.co.pcninc.bigdata.cooperationmodule.configuration.authorization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

import kr.co.pcninc.bigdata.cooperationmodule.ErrorCode;
import kr.co.pcninc.bigdata.cooperationmodule.configuration.credencial.DefaultAuthenticationProvider;
import kr.co.pcninc.bigdata.cooperationmodule.configuration.credencial.JwtTokenProvider;
import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import kr.co.pcninc.bigdata.cooperationmodule.utils.StringUtils;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.isNotEmpty(jwt) && JwtTokenProvider.validateToken(jwt)) {
                String userId = JwtTokenProvider.getUserIdFromJWT(jwt);

                UserAuthentication authentication = new UserAuthentication(userId, null, JwtTokenProvider.getAuthorities(jwt));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } else {
                if (StringUtils.isEmpty(jwt)) {
                    request.setAttribute(ApiConstants.API_UNAUTHORIZATION_ATTRIBUTE_NAME, ErrorCode.UN_AUTHORIZATION);
                }

                if (JwtTokenProvider.validateToken(jwt)) {
                    request.setAttribute(ApiConstants.API_UNAUTHORIZATION_ATTRIBUTE_NAME, ErrorCode.TOKEN_EXPIRED);
                }
            }
        } catch (Exception ex) {
            logger.error("Could not set user authentication in security context", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith(ApiConstants.API_ACCESS_TOKEN_PREFIX)) {
            return bearerToken.substring(ApiConstants.API_ACCESS_TOKEN_PREFIX.length());
        }
        return null;
    }
}
