package kr.co.pcninc.bigdata.cooperationmodule.api.controller.auth;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.omg.CORBA.Current;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import kr.co.pcninc.bigdata.cooperationmodule.configuration.authorization.UserAuthentication;
import kr.co.pcninc.bigdata.cooperationmodule.configuration.credencial.JwtTokenProvider;
import kr.co.pcninc.bigdata.cooperationmodule.api.controller.RestControllerBase;
import kr.co.pcninc.bigdata.cooperationmodule.domain.Token;
import kr.co.pcninc.bigdata.cooperationmodule.domain.Token.Response;
import kr.co.pcninc.bigdata.cooperationmodule.domain.User;
import kr.co.pcninc.bigdata.cooperationmodule.service.UserAuthService;
import kr.co.pcninc.bigdata.cooperationmodule.service.UserService;

@Slf4j
@RestController
@RequestMapping(AuthRestController.URL_PREFIX)
public class AuthRestController extends RestControllerBase {
    public static final String URL_PREFIX = API_URI_PREFIX + "/auth";
    public static final String LOGIN = "/login";
    public static final String CURRENT = "/current";

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthService userAuthService;

    /**
     * @param req
     * @param res
     * @param request
     * @return
     */
    @RequestMapping(
        value = LOGIN,
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> login(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @Valid @RequestBody Token.Request request){

        User user = userService.findByIdPw(request.getId()).orElseThrow(() -> new IllegalArgumentException("없는 사용자입니다."));

        if(!request.getSecret().equals(user.getUserPwd())){
            throw new IllegalArgumentException("비밀번호를 확인하세요.");
        }

        List<String> getRoles = userAuthService.findByUserId(request.getId());

        List<GrantedAuthority> roles = new ArrayList<>();

        for(String code : getRoles){
            roles.add(new SimpleGrantedAuthority(code));
        }

        Authentication authentication = new UserAuthentication(request.getId(), null, roles);
        String token = JwtTokenProvider.generateToken(authentication);

        Response response = Response.builder().token(token).build();

        return okResponse(response);
    }


    /**
     * @param req
     * @param res
     * @return
     */
    @RequestMapping(
        value = CURRENT,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getCurrentUser(
        final HttpServletRequest req,
        final HttpServletResponse res){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String id = authentication.getPrincipal().toString();
        User curUser = userService.findById(id);
        return okResponse(curUser.getUserName());
    }

}
