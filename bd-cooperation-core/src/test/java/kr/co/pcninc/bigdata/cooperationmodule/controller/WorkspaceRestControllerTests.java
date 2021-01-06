package kr.co.pcninc.bigdata.cooperationmodule.controller;


import java.time.LocalDateTime;

import org.springframework.http.MediaType;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import jdk.nashorn.api.scripting.URLReader;
import jdk.nashorn.internal.ir.annotations.Ignore;
import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import kr.co.pcninc.bigdata.cooperationmodule.api.controller.WorkspaceRestController;
import kr.co.pcninc.bigdata.cooperationmodule.common.Constants;
import kr.co.pcninc.bigdata.cooperationmodule.utils.ObjectConverter;
import kr.co.pcninc.bigdata.cooperationmodule.domain.User;

import kr.co.pcninc.bigdata.cooperationmodule.domain.Workspace;



public class WorkspaceRestControllerTests extends ControllerTestBase{
    public static final String URL_PREFIX = WorkspaceRestController.URL_PREFIX;
    public static final String WS_STATUS = WorkspaceRestController.WS_STATUS;
    public static final String WS_LOCK = WorkspaceRestController.WS_LOCK;
    public static final String INCOM = WorkspaceRestController.INCOM;

    public static final String URL_RESOURCE = WorkspaceRestController.URL_RESOURCE;
    public static final String TEST_WS_ID = "94";

    /*@Test
    @Order(1)
    void createWsTest() throws Exception{
        
        Workspace ws = Workspace.builder()
            .wsName("******테스트 데이터 셋********")
            .wsDs("테스트 데이터 셋입니다.테스트 데이터 셋입니다.테스트 데이터 셋입니다.")
            .build();

        String wsStr = ObjectConverter.toJson(ws);

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(URL_PREFIX)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(wsStr)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                requestFields(
                    fieldWithPath("wsId").type(JsonFieldType.NUMBER).description("워크스페이스 아이디 \n\n *Auto Increment*"),
                    fieldWithPath("wsName").type(JsonFieldType.STRING).description("워크스페이스 이름"),
                    fieldWithPath("wsDs").type(JsonFieldType.STRING).description("워크스페이스 설명"),
                    fieldWithPath("lastWork").type(JsonFieldType.STRING).description("마지막 작업").optional(),
                    fieldWithPath("lastWorkComt").type(JsonFieldType.STRING).description("마지막 작업 코멘트").optional(),
                    fieldWithPath("wsCrtDt").type(JsonFieldType.STRING).description("워크스페이스 생성 날짜 \n\n *Current Time*").optional(),
                    fieldWithPath("wsModDt").type(JsonFieldType.STRING).description("워크스페이스 수정 날짜").optional(),
                    fieldWithPath("percent").type(JsonFieldType.STRING).description("진척률 \n\n *Default Value : 0*").optional(),
                    fieldWithPath("userLockYn").type(JsonFieldType.STRING).description("사용자 Lock 여부 \n\n *Default Value : 'N'*"),
                    fieldWithPath("logFilePath").type(JsonFieldType.STRING).description("로그 파일 경로").optional(),
                    fieldWithPath("lastWorker").type(JsonFieldType.STRING).description("마지막 작업자").optional()
                )
            ));
    }


    @Test
    @Order(2)
    void getWsListTest() throws Exception{

        mockMvc.perform(
            RestDocumentationRequestBuilders.get(URL_PREFIX)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                    fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                    fieldWithPath("body.content[0].wsId").type(JsonFieldType.NUMBER).description("워크스페이스 아이디"),
                    fieldWithPath("body.content[0].wsName").type(JsonFieldType.STRING).description("워크스페이스 이름"),
                    fieldWithPath("body.content[0].wsDs").type(JsonFieldType.STRING).description("워크스페이스 설명").optional(),
                    fieldWithPath("body.content[0].lastWork").type(JsonFieldType.STRING).description("마지막 작업").optional(),
                    fieldWithPath("body.content[0].lastWorkComt").type(JsonFieldType.STRING).description("마지막 작업 코멘트").optional(),
                    fieldWithPath("body.content[0].wsCrtDt").type(JsonFieldType.STRING).description("워크스페이스 생성 날짜 \n\n *Current Time*").optional(),
                    fieldWithPath("body.content[0].wsModDt").type(JsonFieldType.STRING).description("워크스페이스 수정 날짜").optional(),
                    fieldWithPath("body.content[0].percent").type(JsonFieldType.STRING).description("진척률 \n\n *Default Value : 0*").optional(),
                    fieldWithPath("body.content[0].userLockYn").type(JsonFieldType.STRING).description("사용자 Lock 여부 \n\n *Default Value : 'N'*"),
                    fieldWithPath("body.content[0].logFilePath").type(JsonFieldType.STRING).description("로그 파일 경로").optional(),
                    fieldWithPath("body.content[0].lastWorker").type(JsonFieldType.OBJECT).description("마지막 작업자").optional(),
                    fieldWithPath("body.content[0].lastWorker.userId").type(JsonFieldType.STRING).description("마지막 작업자 아이디").optional(),
                    fieldWithPath("body.content[0].lastWorker.userName").type(JsonFieldType.STRING).description("마지막 작업자 이름").optional(),
                    fieldWithPath("body.content[0].lastWorker.userPwd").type(JsonFieldType.STRING).description("마지막 작업자 패스워트").optional(),
                    fieldWithPath("body.content[0].lastWorker.company").type(JsonFieldType.STRING).description("마지막 작업자 소속").optional(),
                    fieldWithPath("body.content[0].lastWorker.position").type(JsonFieldType.STRING).description("마지막 작업자 직책").optional(),
                    fieldWithPath("body.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬 여부").optional(),
                    fieldWithPath("body.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("정렬 미여부").optional(),
                    fieldWithPath("body.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬 정보 empty 여부").optional(),
                    fieldWithPath("body.pageable.offset").type(JsonFieldType.NUMBER).description("페이징 오프셋").optional(),
                    fieldWithPath("body.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 수 단위").optional(),
                    fieldWithPath("body.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호").optional(),
                    fieldWithPath("body.pageable.paged").type(JsonFieldType.BOOLEAN).description("페이징 여부").optional(),
                    fieldWithPath("body.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("페이징 미여부").optional(),
                    fieldWithPath("body.totalPages").type(JsonFieldType.NUMBER).description("총 페이지 수").optional(),
                    fieldWithPath("body.last").type(JsonFieldType.BOOLEAN).description("마지막 페이지 여부").optional(),
                    fieldWithPath("body.totalElements").type(JsonFieldType.NUMBER).description("총 데이터 수").optional(),
                    fieldWithPath("body.number").type(JsonFieldType.NUMBER).description("현재 페이지").optional(),
                    fieldWithPath("body.size").type(JsonFieldType.NUMBER).description("페이지 크기").optional(),
                    fieldWithPath("body.sort.sorted").type(JsonFieldType.BOOLEAN).description("현재 페이지 정렬 여부").optional(),
                    fieldWithPath("body.sort.unsorted").type(JsonFieldType.BOOLEAN).description("현재 페이지 미정렬 여부").optional(),
                    fieldWithPath("body.sort.empty").type(JsonFieldType.BOOLEAN).description("현재 페이지 empty 여부").optional(),
                    fieldWithPath("body.first").type(JsonFieldType.BOOLEAN).description("첫 번째 페이지 여부").optional(),
                    fieldWithPath("body.numberOfElements").type(JsonFieldType.NUMBER).description("요소들의 개수").optional(),
                    fieldWithPath("body.empty").type(JsonFieldType.BOOLEAN).description("empty 여부").optional()
                    )
            ));
    }

    @Test
    @Order(3)
    void getWsTest() throws Exception{
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(URL_PREFIX + URL_RESOURCE, TEST_WS_ID)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("wsId").description("조회할 워크스페이스 아이디")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                    fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                    fieldWithPath("body.wsId").type(JsonFieldType.NUMBER).description("워크스페이스 아이디"),
                    fieldWithPath("body.wsName").type(JsonFieldType.STRING).description("워크스페이스 이름"),
                    fieldWithPath("body.wsDs").type(JsonFieldType.STRING).description("워크스페이스 설명").optional(),
                    fieldWithPath("body.lastWork").type(JsonFieldType.STRING).description("마지막 작업").optional(),
                    fieldWithPath("body.lastWorkComt").type(JsonFieldType.STRING).description("마지막 작업 코멘트").optional(),
                    fieldWithPath("body.wsCrtDt").type(JsonFieldType.STRING).description("워크스페이스 생성 날짜 \n\n *Current Time*").optional(),
                    fieldWithPath("body.wsModDt").type(JsonFieldType.STRING).description("워크스페이스 수정 날짜").optional(),
                    fieldWithPath("body.percent").type(JsonFieldType.STRING).description("진척률 \n\n *Default Value : 0*").optional(),
                    fieldWithPath("body.userLockYn").type(JsonFieldType.STRING).description("사용자 Lock 여부 \n\n *Default Value : 'N'*"),
                    fieldWithPath("body.logFilePath").type(JsonFieldType.STRING).description("로그 파일 경로").optional(),
                    fieldWithPath("body.lastWorker").type(JsonFieldType.OBJECT).description("마지막 작업자").optional(),
                    fieldWithPath("body.lastWorker.userId").type(JsonFieldType.STRING).description("마지막 작업자 아이디").optional(),
                    fieldWithPath("body.lastWorker.userName").type(JsonFieldType.STRING).description("마지막 작업자 이름").optional(),
                    fieldWithPath("body.lastWorker.userPwd").type(JsonFieldType.STRING).description("마지막 작업자 패스워트").optional(),
                    fieldWithPath("body.lastWorker.company").type(JsonFieldType.STRING).description("마지막 작업자 소속").optional(),
                    fieldWithPath("body.lastWorker.position").type(JsonFieldType.STRING).description("마지막 작업자 직책").optional()
                )
            ));
    }


    @Test
    @Order(4)
    void updateWsInfoTest() throws Exception{

        Workspace ws = Workspace.builder()
            .wsName("데이터 셋")
            .wsDs("데이터 셋입니다.")
            .wsModDt(LocalDateTime.now().format(Constants.dateFormat))
            .logFilePath("C://")
            .build();


        String wsStr = ObjectConverter.toJson(ws);

        mockMvc.perform(
            RestDocumentationRequestBuilders.put(URL_PREFIX + URL_RESOURCE, TEST_WS_ID)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(wsStr)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("wsId").description("수정 할 워크스페이스 아이디")
                ),
                requestFields(
                    fieldWithPath("wsId").type(JsonFieldType.NUMBER).description("워크스페이스 아이디"),
                    fieldWithPath("wsName").type(JsonFieldType.STRING).description("워크스페이스 이름"),
                    fieldWithPath("wsDs").type(JsonFieldType.STRING).description("워크스페이스 설명"),
                    fieldWithPath("lastWork").type(JsonFieldType.STRING).description("마지막 작업").optional(),
                    fieldWithPath("lastWorkComt").type(JsonFieldType.STRING).description("마지막 작업 코멘트").optional(),
                    fieldWithPath("wsCrtDt").type(JsonFieldType.STRING).description("워크스페이스 생성 날짜 \n\n *Current Time*").optional(),
                    fieldWithPath("wsModDt").type(JsonFieldType.STRING).description("워크스페이스 수정 날짜 \n\n *Current Time*").optional(),
                    fieldWithPath("percent").type(JsonFieldType.STRING).description("진척률 \n\n *Default Value : 0*").optional(),
                    fieldWithPath("userLockYn").type(JsonFieldType.STRING).description("사용자 Lock 여부 \n\n *Default Value : 'N'*"),
                    fieldWithPath("logFilePath").type(JsonFieldType.STRING).description("로그 파일 경로").optional(),
                    fieldWithPath("lastWorker").type(JsonFieldType.STRING).description("마지막 작업자").optional()
                )
            ));
    }


    @Test
    @Order(5)
    void updateWsStatusTest() throws Exception{

        User user = User.builder().userId("hong-gildong").userName("홍길동=").build();

        Workspace ws = Workspace.builder()
            .lastWork("수집 작업")
           // .lastWorkComt("수집 작업 완료되었습니다.")
           // .lastWorker(user)
           // .wsModDt(LocalDateTime.now().format(Constants.dateFormat))
            .percent("20")
            .build();


        String wsStr = ObjectConverter.toJson(ws);

        mockMvc.perform(
            RestDocumentationRequestBuilders.put(URL_PREFIX+ URL_RESOURCE + WS_STATUS, TEST_WS_ID)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(wsStr)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("wsId").description("마지막 작업 정보를 변경할 워크스페이스 아이디")
                ),
                requestFields(
                    fieldWithPath("wsId").type(JsonFieldType.NUMBER).description("워크스페이스 아이디").optional(),
                    fieldWithPath("wsName").type(JsonFieldType.STRING).description("워크스페이스 이름").optional(),
                    fieldWithPath("wsDs").type(JsonFieldType.STRING).description("워크스페이스 설명").optional(),
                    fieldWithPath("lastWork").type(JsonFieldType.STRING).description("마지막 작업"),
                    fieldWithPath("lastWorkComt").type(JsonFieldType.STRING).description("마지막 작업 코멘트").optional(),
                    fieldWithPath("wsCrtDt").type(JsonFieldType.STRING).description("워크스페이스 생성 날짜 \n\n *Current Time*").optional(),
                    fieldWithPath("wsModDt").type(JsonFieldType.STRING).description("워크스페이스 수정 날짜 \n\n *Current Time*").optional(),
                    fieldWithPath("percent").type(JsonFieldType.STRING).description("진척률 \n\n *Default Value : 0*"),
                    fieldWithPath("userLockYn").type(JsonFieldType.STRING).description("사용자 Lock 여부 \n\n *Default Value : 'N'*").optional(),
                    fieldWithPath("logFilePath").type(JsonFieldType.STRING).description("로그 파일 경로").optional(),
                    fieldWithPath("lastWorker").type(JsonFieldType.STRING).description("마지막 작업자").optional()
                    *//*fieldWithPath("lastWorker.userId").type(JsonFieldType.STRING).description("마지막 작업자 아이디").optional(),
                    fieldWithPath("lastWorker.userName").type(JsonFieldType.STRING).description("마지막 작업자 이름").optional(),
                    fieldWithPath("lastWorker.userPwd").type(JsonFieldType.STRING).description("마지막 작업자 이름").optional(),
                    fieldWithPath("lastWorker.company").type(JsonFieldType.STRING).description("마지막 작업자 소속").optional(),
                    fieldWithPath("lastWorker.position").type(JsonFieldType.STRING).description("마지막 작업자 직책").optional()*//*
                    )
            ));
    }



    @Test
    @Order(6)
    void updateWsLockStatTest() throws Exception{

        Workspace ws = Workspace.builder()
            .userLockYn('N')
            .wsModDt(LocalDateTime.now().format(Constants.dateFormat))
            .build();

        String wsStr = ObjectConverter.toJson(ws);

        mockMvc.perform(
            RestDocumentationRequestBuilders.put(URL_PREFIX + URL_RESOURCE + WS_LOCK , TEST_WS_ID)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(wsStr)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("wsId").description("Lock 상태를 바꿀 워크스페이스 아이디")
                ),
                requestFields(
                    fieldWithPath("wsId").type(JsonFieldType.NUMBER).description("워크스페이스 아이디").optional(),
                    fieldWithPath("wsName").type(JsonFieldType.STRING).description("워크스페이스 이름").optional(),
                    fieldWithPath("wsDs").type(JsonFieldType.STRING).description("워크스페이스 설명").optional(),
                    fieldWithPath("lastWork").type(JsonFieldType.STRING).description("마지막 작업").optional(),
                    fieldWithPath("lastWorkComt").type(JsonFieldType.STRING).description("마지막 작업 코멘트").optional(),
                    fieldWithPath("wsCrtDt").type(JsonFieldType.STRING).description("워크스페이스 생성 날짜 \n\n *Current Time*").optional(),
                    fieldWithPath("wsModDt").type(JsonFieldType.STRING).description("워크스페이스 수정 날짜 \n\n *Current Time*").optional(),
                    fieldWithPath("percent").type(JsonFieldType.STRING).description("진척률 \n\n *Default Value : 0*").optional(),
                    fieldWithPath("userLockYn").type(JsonFieldType.STRING).description("사용자 Lock 여부 \n\n *Default Value : 'N'*"),
                    fieldWithPath("logFilePath").type(JsonFieldType.STRING).description("로그 파일 경로").optional(),
                    fieldWithPath("lastWorker").type(JsonFieldType.STRING).description("마지막 작업자 아이디").optional()
                )
            ));
    }


    @Test
    @Order(7)
    void deleteWsTest() throws Exception{

        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(URL_PREFIX + URL_RESOURCE, TEST_WS_ID)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("wsId").description("삭제 할 워크스페이스 아이디")
                )
            ));
    }*/

    @Test
    @Order(8)
    void getIncompleteWs() throws Exception{

        mockMvc.perform(
                RestDocumentationRequestBuilders.get(URL_PREFIX + INCOM)
                        .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document.document(
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                                fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                                fieldWithPath("body[0].wsId").type(JsonFieldType.NUMBER).description("미완료 워크스페이스 아이디"),
                                fieldWithPath("body[0].wsName").type(JsonFieldType.STRING).description("미완료 워크스페이스 이름"),
                                fieldWithPath("body[0].wsDs").type(JsonFieldType.STRING).description("워크스페이스 설명").optional(),
                                fieldWithPath("body[0].lastWork").type(JsonFieldType.STRING).description("마지막 작업").optional(),
                                fieldWithPath("body[0].lastWorkComt").type(JsonFieldType.STRING).description("마지막 작업 코멘트").optional(),
                                fieldWithPath("body[0].wsCrtDt").type(JsonFieldType.STRING).description("워크스페이스 생성 날짜 \n\n *Current Time*").optional(),
                                fieldWithPath("body[0].wsModDt").type(JsonFieldType.STRING).description("워크스페이스 수정 날짜").optional(),
                                fieldWithPath("body[0].percent").type(JsonFieldType.STRING).description("진척률 \n\n *Default Value : 0*").optional(),
                                fieldWithPath("body[0].userLockYn").type(JsonFieldType.STRING).description("사용자 Lock 여부 \n\n *Default Value : 'N'*"),
                                fieldWithPath("body[0].logFilePath").type(JsonFieldType.STRING).description("로그 파일 경로").optional(),
                                fieldWithPath("body[0].lastWorker").type(JsonFieldType.OBJECT).description("마지막 작업자").optional(),
                                fieldWithPath("body[0].lastWorker.userId").type(JsonFieldType.STRING).description("마지막 작업자 아이디").optional(),
                                fieldWithPath("body[0].lastWorker.userName").type(JsonFieldType.STRING).description("마지막 작업자 이름").optional(),
                                fieldWithPath("body[0].lastWorker.userPwd").type(JsonFieldType.STRING).description("마지막 작업자 패스워트").optional(),
                                fieldWithPath("body[0].lastWorker.company").type(JsonFieldType.STRING).description("마지막 작업자 소속").optional(),
                                fieldWithPath("body[0].lastWorker.position").type(JsonFieldType.STRING).description("마지막 작업자 직책").optional()
                        )
                ));
    }



}
