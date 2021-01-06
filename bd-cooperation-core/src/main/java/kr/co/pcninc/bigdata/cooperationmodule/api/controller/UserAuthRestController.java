package kr.co.pcninc.bigdata.cooperationmodule.api.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.pcninc.bigdata.cooperationmodule.domain.UserAuth;
import kr.co.pcninc.bigdata.cooperationmodule.domain.response.UserAuthResponse;
import kr.co.pcninc.bigdata.cooperationmodule.service.UserAuthService;

@RestController
@RequestMapping(value = UserAuthRestController.URL_PREFIX)
public class UserAuthRestController extends RestControllerBase{
    public static final String URL_PREFIX = API_URI_PREFIX + "/userAuth";
    public static final String URL_RESOURCE = "/{userId}";
    public static final String CODE_ID = "/{codeId}";

    @Autowired
    UserAuthService userAuthService;

    /**
     * 사용자 권한 전체 조회
     * @param req
     * @param res
     * @return
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAuths(
        final HttpServletRequest req,
        final HttpServletResponse res
    ){
        return okResponse(userAuthService.findAll());
    }

    /**
     * 사용자 권한 조회
     * @param req
     * @param res
     * @param userId
     * @return
     */
    @RequestMapping(
        value = URL_RESOURCE,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAuth(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable String userId
    ){
        UserAuthResponse userAuthResponse = UserAuthResponse
                                                .builder()
                                                .authList(userAuthService.findByUserId(userId))
                                                .build();

        return okResponse(userAuthResponse);
    }

    /**
     * 사용자 권한 추가
     * @param req
     * @param res
     * @param userAuth
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createUserAuth(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @RequestBody UserAuth userAuth
    ){
        userAuthService.save(userAuth);

        return okResponse("새 사용자 권한 추가 완료");
    }

    /**
     * 사용자 권한 변경
     * @param req
     * @param res
     * @param userAuth
     */
    @RequestMapping(
        value = CODE_ID,
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUserAuth(
        final HttpServletRequest req,
    final HttpServletResponse res,
    @RequestBody UserAuth userAuth,
    @PathVariable String codeId
    ){
        UserAuth user = userAuthService.findById(userAuth.getUserAuthMultiId());
        user.getUserAuthMultiId().setCodeId(codeId);

        userAuthService.save(user);

        return okResponse(user.getUserAuthMultiId().getUserId() + " 사용자 권한 정보 수정 완료");
    }

    /**
     * 사용자 권한 삭제
     * @param req
     * @param res
     * @param userAuth
     */
    @RequestMapping(
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteUserAuth(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @RequestBody UserAuth userAuth
    ){
        userAuthService.delete(userAuth.getUserAuthMultiId());

        return okResponse(userAuth.getUserAuthMultiId().getUserId() + "사용자 권한 정보 삭제 완료");
    }
}
