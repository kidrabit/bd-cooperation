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
import kr.co.pcninc.bigdata.cooperationmodule.domain.Code;
import kr.co.pcninc.bigdata.cooperationmodule.service.CodeService;

@RestController
@RequestMapping(value = CodeRestController.URL_PREFIX)
public class CodeRestController extends RestControllerBase{
    public static final String URL_PREFIX = API_URI_PREFIX + "/codes";
    public static final String URL_RESOURCE = "/{codeId}";

    public static final String curPackage = CodeRestController.class.getPackage().toString();
    @Autowired
    CodeService codeService;

    /**
     * 권한 목록 조회
     * @param req
     * @param res
     * @return
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getCodes(
        final HttpServletRequest req,
        final HttpServletResponse res
    ){

        List<Code> codes = null;

        try{
            codes = codeService.findAll();
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, "권한 목록 조회");

        return okResponse(codes);
    }

    /**
     * 권한 정보 조회
     * @param req
     * @param res
     * @param codeId
     * @return
     */
    @RequestMapping(
        value = URL_RESOURCE,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getCode(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable String codeId
    ){
        Code code = null;

        try{
            code = codeService.findById(codeId);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage,  code.getCodeName() + " 코드 정보 조회");
        return okResponse(code);
    }

    /**
     * 새 권한 추가
     * @param req
     * @param res
     * @param code
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createCode(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @RequestBody Code code
    ){
        try{
            codeService.save(code);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage,  code.getCodeName() + " 권한 정보 추가");

        return okResponse("새 권한 정보 추가 완료(" + code.getCodeId() + " : " + code.getCodeName() + ")");
    }

    /**
     * 권한 정보 수정
     * @param req
     * @param res
     * @param code
     */
    @RequestMapping(
        value = URL_RESOURCE,
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateCodeInfo(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable String codeId,
        @RequestBody Code code
    ){
        Code getCode = null;
        try{
            getCode = codeService.findById(codeId);
            getCode.setCodeName(code.getCodeName());
            codeService.save(getCode);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }
        makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, "code ID : " + codeId + " 정보 수정 완료");

        return okResponse(codeId + "번 권한 정보 수정 완료");
    }

    /**
     * 권한 삭제
     * @param req
     * @param res
     * @param codeId
     */
    @RequestMapping(
        value = URL_RESOURCE,
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteUserAuth(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable String codeId
    ){
        try {
            codeService.delete(codeId);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }
        makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, codeId + " 권한 삭제");

        return okResponse(codeId + "번 권한 정보 삭제 완료");
    }
}



