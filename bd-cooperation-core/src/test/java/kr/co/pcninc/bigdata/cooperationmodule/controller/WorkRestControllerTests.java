package kr.co.pcninc.bigdata.cooperationmodule.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import kr.co.pcninc.bigdata.cooperationmodule.api.controller.WorkspaceRestController;
import kr.co.pcninc.bigdata.cooperationmodule.utils.ObjectConverter;
import kr.co.pcninc.bigdata.cooperationmodule.domain.User;
import kr.co.pcninc.bigdata.cooperationmodule.domain.Works;
import kr.co.pcninc.bigdata.cooperationmodule.domain.WorksMultiId;


public class WorkRestControllerTests extends ControllerTestBase{

    public static final String URL_PREFIX = WorkspaceRestController.URL_PREFIX;
    public static final String WORKS = WorkspaceRestController.WORKS;
    public static final String WS_ID = WorkspaceRestController.URL_RESOURCE;
    public static final String WORK_ID = WorkspaceRestController.WORK_ID;


    public static final int TEST_WS_ID = 119;


    @Test
    @Order(1)
    void createWorkTest() throws Exception{
        WorksMultiId multiId = WorksMultiId.builder().workId(2).wsId(TEST_WS_ID).build();

        Works work = Works.builder()
            .worksMultiId(multiId)
            .build();

        System.out.print(ObjectConverter.toJson(work));

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(URL_PREFIX + WORKS)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectConverter.toJson(work))
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                requestFields(
                    fieldWithPath("worksMultiId.workId").type(JsonFieldType.NUMBER).description("작업 아이디"),
                    fieldWithPath("worksMultiId.wsId").type(JsonFieldType.NUMBER).description("워크스페이스 아이디"),
                    fieldWithPath("lockActiveUser").type(JsonFieldType.STRING).description("LOCK 활성화 사용자").optional(),
                    fieldWithPath("filePath").type(JsonFieldType.STRING).description("파일 위치").optional(),
                    fieldWithPath("thput").type(JsonFieldType.STRING).description("데이터 처리량").optional(),
                    fieldWithPath("worker").type(JsonFieldType.STRING).description("작업자").optional(),
                    fieldWithPath("workerCmt").type(JsonFieldType.STRING).description("작업 코멘트").optional(),
                    fieldWithPath("crtDt").type(JsonFieldType.STRING).description("작업 생성 날짜").optional(),
                    fieldWithPath("modDt").type(JsonFieldType.STRING).description("작업 수정 날짜").optional(),
                    fieldWithPath("finishYn").type(JsonFieldType.STRING).description("작업 완료 여부 \n\n\n *Default Value : 'N'*"
                    )
                )
            ));
    }


    @Test
    @Order(2)
    void getWorkListTest() throws Exception{
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(URL_PREFIX + WORKS)
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
                        fieldWithPath("body[0].worksMultiId.workId").type(JsonFieldType.NUMBER).description("작업 아이디"),
                        fieldWithPath("body[0].worksMultiId.wsId").type(JsonFieldType.NUMBER).description("워크스페이스 아이디"),
                        fieldWithPath("body[0].lockActiveUser.userId").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 아이디"),
                        fieldWithPath("body[0].lockActiveUser.userName").type(JsonFieldType.STRING).description("LOCK 활성화 사용자"),
                        fieldWithPath("body[0].lockActiveUser.userPwd").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 패스워드"),
                        fieldWithPath("body[0].lockActiveUser.company").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 소속"),
                        fieldWithPath("body[0].lockActiveUser.position").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 직책"),
                        fieldWithPath("body[0].filePath").type(JsonFieldType.STRING).description("파일 위치").optional(),
                        fieldWithPath("body[0].thput").type(JsonFieldType.STRING).description("데이터 처리량").optional(),
                        fieldWithPath("body[0].worker").type(JsonFieldType.STRING).description("작업자").optional(),
                        fieldWithPath("body[0].workerCmt").type(JsonFieldType.STRING).description("작업 코멘트").optional(),
                        fieldWithPath("body[0].crtDt").type(JsonFieldType.STRING).description("작업 생성 날짜").optional(),
                        fieldWithPath("body[0].modDt").type(JsonFieldType.STRING).description("작업 수정 날짜").optional(),
                        fieldWithPath("body[0].finishYn").type(JsonFieldType.STRING).description("작업 완료 여부 \n\n\n *Default Value : 'N'*"))
                )
            );
    }


    @Test
    @Order(3)
    void getWorkByWsIdTest() throws Exception{
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(URL_PREFIX + WS_ID + WORKS, TEST_WS_ID)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                  parameterWithName("wsId").description("워크스페이스 아이디")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                    fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                    fieldWithPath("body[0].worksMultiId.workId").type(JsonFieldType.NUMBER).description("작업 아이디"),
                    fieldWithPath("body[0].worksMultiId.wsId").type(JsonFieldType.NUMBER).description("워크스페이스 아이디"),
                    fieldWithPath("body[0].lockActiveUser.userId").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 아이디"),
                    fieldWithPath("body[0].lockActiveUser.userName").type(JsonFieldType.STRING).description("LOCK 활성화 사용자"),
                    fieldWithPath("body[0].lockActiveUser.userPwd").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 패스워드"),
                    fieldWithPath("body[0].lockActiveUser.company").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 소속"),
                    fieldWithPath("body[0].lockActiveUser.position").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 직책"),
                    fieldWithPath("body[0].filePath").type(JsonFieldType.STRING).description("파일 위치").optional(),
                    fieldWithPath("body[0].thput").type(JsonFieldType.STRING).description("데이터 처리량").optional(),
                    fieldWithPath("body[0].worker").type(JsonFieldType.STRING).description("작업자").optional(),
                    fieldWithPath("body[0].workerCmt").type(JsonFieldType.STRING).description("작업 코멘트").optional(),
                    fieldWithPath("body[0].crtDt").type(JsonFieldType.STRING).description("작업 생성 날짜").optional(),
                    fieldWithPath("body[0].modDt").type(JsonFieldType.STRING).description("작업 수정 날짜").optional(),
                    fieldWithPath("body[0].finishYn").type(JsonFieldType.STRING).description("작업 완료 여부 \n\n\n *Default Value : 'N'*"))
                )
            );
    }


    @Test
    @Order(4)
    void getWorkByIdTest() throws Exception{

        mockMvc.perform(
            RestDocumentationRequestBuilders.get(URL_PREFIX + WS_ID + WORKS + WORK_ID, TEST_WS_ID, 1)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("wsId").description("조회 할 워크스페이스 아이디"),
                    parameterWithName("workId").description("조회 할 작업 아이디")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                    fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                    fieldWithPath("body.worksMultiId.workId").type(JsonFieldType.NUMBER).description("작업 아이디"),
                    fieldWithPath("body.worksMultiId.wsId").type(JsonFieldType.NUMBER).description("워크스페이스 아이디"),
                    fieldWithPath("body.lockActiveUser.userId").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 아이디"),
                    fieldWithPath("body.lockActiveUser.userName").type(JsonFieldType.STRING).description("LOCK 활성화 사용자"),
                    fieldWithPath("body.lockActiveUser.userPwd").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 패스워드"),
                    fieldWithPath("body.lockActiveUser.company").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 소속"),
                    fieldWithPath("body.lockActiveUser.position").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 직책"),
                    fieldWithPath("body.filePath").type(JsonFieldType.STRING).description("파일 위치").optional(),
                    fieldWithPath("body.thput").type(JsonFieldType.STRING).description("데이터 처리량").optional(),
                    fieldWithPath("body.worker").type(JsonFieldType.STRING).description("작업자").optional(),
                    fieldWithPath("body.workerCmt").type(JsonFieldType.STRING).description("작업 코멘트").optional(),
                    fieldWithPath("body.crtDt").type(JsonFieldType.STRING).description("작업 생성 날짜").optional(),
                    fieldWithPath("body.modDt").type(JsonFieldType.STRING).description("작업 수정 날짜").optional(),
                    fieldWithPath("body.finishYn").type(JsonFieldType.STRING).description("작업 완료 여부 \n\n\n *Default Value : 'N'*")
                )
            ));
    }




    @Test
    @Order(5)
    void updateWorkTest() throws Exception{
        WorksMultiId multiId = new WorksMultiId();
        multiId.setWorkId(2);
        multiId.setWsId(TEST_WS_ID);

        User user = User.builder().userId("hong-gildong").userName("홍길동=").build();

        Works work = Works.builder()
            .worksMultiId(multiId)
            .lockActiveUser(user)
            .finishYn('Y')
            .build();

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(URL_PREFIX + WORKS)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectConverter.toJson(work))
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                requestFields(
                    fieldWithPath("worksMultiId.workId").type(JsonFieldType.NUMBER).description("작업 아이디"),
                    fieldWithPath("worksMultiId.wsId").type(JsonFieldType.NUMBER).description("워크스페이스 아이디"),
                    fieldWithPath("lockActiveUser.userId").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 아이디").optional(),
                    fieldWithPath("lockActiveUser.userName").type(JsonFieldType.STRING).description("LOCK 활성화 사용자").optional(),
                    fieldWithPath("lockActiveUser.userPwd").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 패스워드").optional(),
                    fieldWithPath("lockActiveUser.company").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 소속").optional(),
                    fieldWithPath("lockActiveUser.position").type(JsonFieldType.STRING).description("LOCK 활성화 사용자 직책").optional(),
                    fieldWithPath("filePath").type(JsonFieldType.STRING).description("파일 위치").optional(),
                    fieldWithPath("thput").type(JsonFieldType.STRING).description("데이터 처리량").optional(),
                    fieldWithPath("worker").type(JsonFieldType.STRING).description("작업자").optional(),
                    fieldWithPath("workerCmt").type(JsonFieldType.STRING).description("작업 코멘트").optional(),
                    fieldWithPath("crtDt").type(JsonFieldType.STRING).description("작업 생성 날짜").optional(),
                    fieldWithPath("modDt").type(JsonFieldType.STRING).description("작업 수정 날짜").optional(),
                    fieldWithPath("finishYn").type(JsonFieldType.STRING).description("작업 완료 여부 \n\n\n *Default Value : 'N'*")
                )
            ));
    }

    @Test
    @Order(6)
    void deleteWorkTest() throws Exception{

        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(URL_PREFIX + WS_ID + WORKS, TEST_WS_ID)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("wsId").description("삭제할 워크스페이스 아이디")
                )
            ));
    }


}
