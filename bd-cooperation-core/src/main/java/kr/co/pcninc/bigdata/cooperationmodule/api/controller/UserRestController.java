package kr.co.pcninc.bigdata.cooperationmodule.api.controller;

import java.time.LocalDateTime;
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

import kr.co.pcninc.bigdata.cooperationmodule.AppConstants;
import kr.co.pcninc.bigdata.cooperationmodule.domain.User;
import kr.co.pcninc.bigdata.cooperationmodule.service.UserService;

@RestController
@RequestMapping(value = UserRestController.URL_PREFIX)
public class UserRestController extends RestControllerBase{

    public static final String URL_PREFIX = API_URI_PREFIX + "/users";
    public static final String URL_RESOURCE = "/{userId}";

    public static final String curPackage = UserRestController.class.getPackage().toString();

    @Autowired
    UserService userService;

    /**
     * 사용자 목록 조회
     * @param req
     * @param res
     * @return
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getUsers(
        final HttpServletRequest req,
        final HttpServletResponse res
    ){
        List<User> users = null;
        try{
            users = userService.findAll();
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, "모든 사용자 조회");

        return okResponse(users);
    }

    /**
     * 사용자 정보 조회
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
    public ResponseEntity<?> getUser(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable String userId
    ){
        User user = null;

        try{
            user = userService.findById(userId);
        }catch (Exception e) {
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }
        makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, userId + " 사용자 정보 조회");

        return okResponse(user);
    }

    /**
     * 새 사용자 추가
     * @param req
     * @param res
     * @param user
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createUser(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @RequestBody User user
    ){
        try{
            userService.save(user);
        }catch (Exception e) {
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, user.getUserId() + " 사용자 추가 완료");

        return okResponse(user.getUserId() + " 사용자 추가 완료");
    }

    /**
     * 사용자 정보 수정
     * @param req
     * @param res
     * @param userId
     * @param user
     */
    @RequestMapping(
        value = URL_RESOURCE,
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateUser(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable String userId,
        @RequestBody User user
    ){

        try{
            User modUser = userService.findById(userId);
            modUser.setUserName(user.getUserName());
            modUser.setUserPwd(user.getUserPwd());
            modUser.setCompany(user.getCompany());
            modUser.setPosition(user.getPosition());

            userService.save(modUser);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, user.getUserId() + " 사용자 정보 수정 완료");

        return okResponse(userId + " 사용자 정보 수정 완료");
    }

    /**
     * 사용자 삭제
     * @param req
     * @param res
     * @param userId
     */
    @RequestMapping(
        value = URL_RESOURCE,
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteWs(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable String userId
    ){
        try{
            userService.delete(userId);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }
        makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, userId + " 사용자 정보 삭제 완료");

        return okResponse(userId + " 사용자 정보 삭제 완료");
    }


}
