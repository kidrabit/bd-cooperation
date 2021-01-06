package kr.co.pcninc.bigdata.cooperationmodule.configuration.credencial;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import kr.co.pcninc.bigdata.cooperationmodule.code.Role;
import kr.co.pcninc.bigdata.cooperationmodule.configuration.authorization.UserAuthentication;
import kr.co.pcninc.bigdata.cooperationmodule.domain.User;
import kr.co.pcninc.bigdata.cooperationmodule.service.UserService;

@Component
@Slf4j
public class DefaultAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String id = authentication.getName();
        String pwd = authentication.getCredentials().toString();

        return authenticate(id, pwd);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

    private Authentication authenticate(String loginId, String loginPwd) throws AuthenticationException{
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(Role.ADMINISTRATION.name()));

        User user = userService.findById(loginId);
        if(!user.getUserId().equals(loginId) || !user.getUserPwd().equals(loginPwd)){
            return null;
        }

        return new UserAuthentication(loginId, loginPwd, authorities);
    }
}
