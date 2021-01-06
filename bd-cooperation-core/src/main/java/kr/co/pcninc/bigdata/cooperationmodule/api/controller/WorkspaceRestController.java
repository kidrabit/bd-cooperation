package kr.co.pcninc.bigdata.cooperationmodule.api.controller;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.ConsoleHandler;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.EntityManagerFactoryUtils;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.co.pcninc.bigdata.cooperationmodule.AppConstants;
import kr.co.pcninc.bigdata.cooperationmodule.common.Constants;
import kr.co.pcninc.bigdata.cooperationmodule.common.CustomLogFormatter;
import kr.co.pcninc.bigdata.cooperationmodule.domain.Log;
import kr.co.pcninc.bigdata.cooperationmodule.domain.Works;
import kr.co.pcninc.bigdata.cooperationmodule.domain.WorksMultiId;
import kr.co.pcninc.bigdata.cooperationmodule.domain.Workspace;
import kr.co.pcninc.bigdata.cooperationmodule.service.WorkService;
import kr.co.pcninc.bigdata.cooperationmodule.service.WorkspaceService;

@Slf4j
@RestController
@RequestMapping(value = WorkspaceRestController.URL_PREFIX)
public class WorkspaceRestController extends RestControllerBase{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String URL_PREFIX = API_URI_PREFIX + WORKSPACE_API_PREFIX;
    public static final String URL_RESOURCE = "/{wsId}";
    public static final String WS_STATUS = "/stats";
    public static final String WS_LOCK = "/lock";
    public static final String INCOM = "/incom";

    public static final String WORKS = "/works";
    public static final String WORK_ID = "/{workId}";

    public static final String LOG = "/log";

    public static final String curPackage = WorkspaceRestController.class.getPackage().toString();

        @Autowired
    WorkspaceService workspaceService;

    @Autowired
    WorkService workService;

    /**
     * 워크스페이스 목록 조회
     * @param req
     * @param res
     * @param request
     */
    @RequestMapping(
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getWsList(
        final HttpServletRequest req,
        final HttpServletResponse res,
        final Pageable request){
        Page<Workspace> workspace = null;
        try{
           workspace = workspaceService.findAll(request);
        }catch(Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage, "워크스페이스 목록 조회");

        return okResponse(workspace);
    }


    /**
     * 미완료 워크스페이스 조회
     * @param req
     * @param res
     * @return
     */
    @RequestMapping(
            value = INCOM,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getIncompleteWs(
            final HttpServletRequest req,
            final HttpServletResponse res
    ){
        List<Workspace> result = null;
        try{
            result = workspaceService.findIncomplete();
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage,"미완료 워크스페이스 목록 조회");

        return okResponse(result);
    }


    /**
     * 워크스페이스 조회
     * @param req
     * @param res
     * @param wsId
     * @return
     */
    @RequestMapping(
        value = URL_RESOURCE,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE

    )
    public ResponseEntity<?> getWs(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable int wsId){

        Workspace workspace = null;
        try{
            workspace = workspaceService.findById(wsId);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage,wsId + "번 워크스페이스 목록 조회");

        return okResponse(workspace);
    }


    /**
     * 워크스페이스 생성
     * @param req
     * @param res
     * @param workspace
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createWs(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @RequestBody Workspace workspace
    ) throws JsonProcessingException {
        workspace.setWsCrtDt(LocalDateTime.now().format(Constants.dateFormat));

        try{
            workspaceService.save(workspace);

            //works 4개 추가 하기
            workspaceService.save(workspace);
            if(workspaceService.findById(workspace.getWsId()) != null){
                for(int i = 0; i < 4; i++) {
                    Works newWork = Works.builder()
                        .worksMultiId(
                            WorksMultiId.builder().workId(i).wsId(workspace.getWsId()).build()
                        ).build();
                    workService.save(newWork);
                }
            }
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage, workspace.getWsName()+ " 워크스페이스 생성 완료");

        return okResponse(workspace.getWsId() + "번 워크스페이스 생성 완료");
    }


    /**
     * 워크스페이스 정보 수정
     * @param req
     * @param res
     * @param wsId
     * @param workspace
     */
    @RequestMapping(
        value = URL_RESOURCE,
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> modifyWsInfo(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable int wsId,
        @RequestBody Workspace workspace
    ){
        Workspace pickWs = null;

        try{
            pickWs = workspaceService.findById(wsId);
            pickWs.setWsName(workspace.getWsName());
            pickWs.setWsDs(workspace.getWsDs());
            pickWs.setWsModDt(LocalDateTime.now().format(Constants.dateFormat));
            pickWs.setLogFilePath(workspace.getLogFilePath());

            workspaceService.save(pickWs);
        }catch(Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }
        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage, workspace.getWsId()+ "번 워크스페이스 수정 완료");

        return okResponse(wsId + "번 워크스페이스 정보 수정 완료");
    }


    /**
     * 마지막 작업, 진척률 변경
     * @param req
     * @param res
     * @param wsId
     * @param workspace
     */
    @RequestMapping(
        value = URL_RESOURCE + WS_STATUS,
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> modifyStatus(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable int wsId,
        @RequestBody Workspace workspace
    ){
        Workspace pickWs = null;

        try{
            pickWs = workspaceService.findById(wsId);

            pickWs.setLastWork(workspace.getLastWork());
            pickWs.setLastWorker(workspace.getLastWorker());
            pickWs.setLastWorkComt(workspace.getLastWorkComt());
            pickWs.setWsModDt(LocalDateTime.now().format(Constants.dateFormat));
            pickWs.setPercent(workspace.getPercent());

            workspaceService.save(pickWs);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage,
            workspace.getWsName()+ " 워크스페이스" + workspace.getLastWork()+ "작업 완료");

        return okResponse(wsId + "번 워크스페이스 마지막 작업 및 진척률 수정 완료");
    }

    /**
     * 사용자 Lock 상태 변경
     * @param req
     * @param res
     * @param wsId
     * @param workspace
     */
    @RequestMapping(
        value = URL_RESOURCE + WS_LOCK,
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> modifyLockStat(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable int wsId,
        @RequestBody Workspace workspace
    ){
        Workspace pickWs = null;

        try{
            pickWs = workspaceService.findById(wsId);

            pickWs.setUserLockYn(workspace.getUserLockYn());
            pickWs.setWsModDt(LocalDateTime.now().format(Constants.dateFormat));

            workspaceService.save(pickWs);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage,workspace.getWsName()+ " 워크스페이스 Lock 상태 변경");

        return okResponse(wsId + "번 워크스페이스 Lock 상태" + workspace.getUserLockYn() + "로 변경");
    }

    /**
     * 워크스페이스 삭제
     * @param req
     * @param res
     * @param wsId
     */
    @RequestMapping(
        value = URL_RESOURCE,
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteWs(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable int wsId
    ){
        try{
            workspaceService.delete(wsId);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }
        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage,wsId + "번 워크스페이스 삭제");

        return okResponse(wsId + "번 워크스페이스 삭제 완료");
    }



    /**
     * 모든 작업 조회
     * @param req
     * @param res
     * @return
     */
    @RequestMapping(
        value = WORKS,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getAllWorks(
        final HttpServletRequest req,
        final HttpServletResponse res
    ){
        return okResponse(workService.findAll());
    }


    /**
     * 워크스페이스 아이디로 작업 조회(각 ws 당 수집, 전처리, 프로파일링)
     * @param req
     * @param res
     * @param wsId
     * @return
     */
    @RequestMapping(
        value = URL_RESOURCE + WORKS,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getWorksByWsId(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable int wsId
    ){
        List<Works> works = null;

        try{
            works = workService.findByWsId(wsId);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage, wsId + "번 워크스페이스 작업 조회");
        return okResponse(works);
    }

    /**
     * 하나의 작업 조회
     * @param req
     * @param res
     * @param wsId
     * @param workId
     * @return
     */
    @RequestMapping(
        value = URL_RESOURCE + WORKS + WORK_ID,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getWorkById(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable int wsId,
        @PathVariable int workId
    ){
        Works works = null;

        try{
            WorksMultiId multiId = new WorksMultiId();
            multiId.setWsId(wsId);
            multiId.setWorkId(workId);

            works = workService.findById(multiId);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage,wsId + "번 워크스페이스의 " + AppConstants.getWorkName(workId) + " 작업 조회");

        return okResponse(works);
    }


    /**
     * 새 작업 추가
     * @param req
     * @param res
     * @param works
     */
    @RequestMapping(
        value = WORKS,
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createWorks(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @RequestBody Works works
    ){
        workService.save(works);
        return okResponse(works.getWorksMultiId().getWsId() + "번 워크스페이스의 작업 생성 완료");
    }


    /**
     * 작업 내용 수정
     * @param req
     * @param res
     * @param work
     */
    @RequestMapping(
        value = WORKS,
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> updateWork(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @RequestBody Works work
    ){
        try{
            Works getWork = workService.findById(work.getWorksMultiId());

            getWork.setLockActiveUser(work.getLockActiveUser());
            getWork.setFilePath(work.getFilePath());
            getWork.setWorker(work.getWorker());
            getWork.setWorkerCmt(work.getWorkerCmt());
            getWork.setModDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss")));
            getWork.setThput(work.getThput() == null ? null : work.getThput());
            getWork.setFinishYn(work.getFinishYn());

            workService.save(getWork);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage,AppConstants.getWorkName(work.getWorksMultiId().getWorkId()) + " 작업 내용 수정 완료");

        return okResponse(work.getWorksMultiId().getWsId() + "번 워크스페이스의" + work.getWorksMultiId().getWorkId() + "번 작업 수정 완료");
    }


    /**
     * 작업 삭제
     * @param req
     * @param res
     * @param wsId
     */
    @RequestMapping(
        value = URL_RESOURCE + WORKS,
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> deleteWorks(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable int wsId
    ){

        try{
            workService.deleteByWsId(wsId);
        }catch (Exception e){
            makeLogs(AppConstants.ERROR, LocalDateTime.now(), curPackage, e.getMessage());
        }

        makeLogs(AppConstants.INFO, LocalDateTime.now(), curPackage,wsId + "번 작업 삭제 완료");

        return okResponse("작업 삭제 완료");
    }


    @RequestMapping(
        value = LOG,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getLog(
        final HttpServletRequest req,
        final HttpServletResponse res
    ){
        return okResponse(curLog);
    }

    @RequestMapping(
            value="/module",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void load(
            final HttpServletRequest req,
            final HttpServletResponse res,
            @RequestHeader(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME) final String accessKey
        ) throws IOException {
        res.sendRedirect("http://www.naver.com");
    }

}
