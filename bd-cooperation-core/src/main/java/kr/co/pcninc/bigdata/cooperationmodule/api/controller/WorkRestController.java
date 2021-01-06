package kr.co.pcninc.bigdata.cooperationmodule.api.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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

import kr.co.pcninc.bigdata.cooperationmodule.domain.Works;
import kr.co.pcninc.bigdata.cooperationmodule.domain.WorksMultiId;
import kr.co.pcninc.bigdata.cooperationmodule.service.WorkService;

//@RestController
@RequestMapping(value = WorkRestController.URL_PREFIX)
public class WorkRestController extends RestControllerBase{

    public static final String URL_PREFIX = API_URI_PREFIX + WORKSPACE_API_PREFIX;
    public static final String WORKS = "/works";
    public static final String DETAIL = "/detail";
    public static final String WS_ID = "/{wsId}";


    @Autowired
    WorkService workService;

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
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getWorksByWsId(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable int wsId
    ){
        return okResponse(workService.findByWsId(wsId));
    }


    /**
     * 하나의 작업 조회
     * @param req
     * @param res
     * @param worksMultiId
     * @return
     */
    @RequestMapping(
        value = DETAIL,
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> getWorkById(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @RequestBody WorksMultiId worksMultiId
    ){
        return okResponse(workService.findById(worksMultiId));
    }


    /**
     * 새 작업 추가
     * @param req
     * @param res
     * @param works
     */
    @RequestMapping(
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void createWorks(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @RequestBody Works works
    ){
        workService.save(works);
    }


    /**
     * 작업 내용 수정
      * @param req
     * @param res
     * @param work
     */
    @RequestMapping(
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void updateWork(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @RequestBody Works work
    ){
        Works getWork = workService.findById(work.getWorksMultiId());

        getWork.setLockActiveUser(work.getLockActiveUser());
        getWork.setWorker(work.getWorker());
        getWork.setWorkerCmt(work.getWorkerCmt());
        getWork.setModDt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm:ss")));
        getWork.setThput(work.getThput() == null ? null : work.getThput());
        getWork.setFinishYn(work.getFinishYn());

        workService.save(getWork);
    }


    /**
     * 작업 삭제
     * @param req
     * @param res
     * @param wsId
     */
    @RequestMapping(
        value = WS_ID,
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void deleteWorks(
        final HttpServletRequest req,
        final HttpServletResponse res,
        @PathVariable int wsId
    ){
        workService.deleteByWsId(wsId);
    }
}
