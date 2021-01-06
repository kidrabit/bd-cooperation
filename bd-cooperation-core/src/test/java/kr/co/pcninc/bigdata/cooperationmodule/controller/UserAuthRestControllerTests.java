package kr.co.pcninc.bigdata.cooperationmodule.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;
import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import kr.co.pcninc.bigdata.cooperationmodule.api.controller.UserAuthRestController;
import kr.co.pcninc.bigdata.cooperationmodule.code.Role;
import kr.co.pcninc.bigdata.cooperationmodule.utils.ObjectConverter;
import kr.co.pcninc.bigdata.cooperationmodule.domain.UserAuth;
import kr.co.pcninc.bigdata.cooperationmodule.domain.UserAuthMultiId;


public class UserAuthRestControllerTests extends ControllerTestBase{

    public static final String URL_PREFIX = UserAuthRestController.URL_PREFIX;
    public static final String URL_RESOURCE = UserAuthRestController.URL_RESOURCE;
    public static final String CODE_ID = UserAuthRestController.CODE_ID;
    public static final String TEST_ID = "testUser";
    public static final String TEST_CODE = Role.TEST2.toString();


    @Test
    @Order(1)
    void createUserAuthTest() throws Exception{

        UserAuthMultiId multiId = new UserAuthMultiId();
        multiId.setUserId(TEST_ID);
        multiId.setCodeId(TEST_CODE);

        UserAuth us = UserAuth.builder().userAuthMultiId(multiId).build();

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(URL_PREFIX)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectConverter.toJson(us))
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                requestFields(
                    fieldWithPath("userAuthMultiId.userId").type(JsonFieldType.STRING).description("사용자 아이디"),
                    fieldWithPath("userAuthMultiId.codeId").type(JsonFieldType.STRING).description("코드 아이디")
                )
            ));
    }


    @Test
    @Order(2)
    void getUserAuthListTest() throws Exception{
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
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공여부"),
                    fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                    fieldWithPath("body[0].userAuthMultiId.userId").type(JsonFieldType.STRING).description("사용자 아이디"),
                    fieldWithPath("body[0].userAuthMultiId.codeId").type(JsonFieldType.STRING).description("권한 코드 아이디")
                )
            ));
    }


    @Test
    @Order(3)
    void getUserAuthTest() throws Exception{
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(URL_PREFIX + URL_RESOURCE, TEST_ID)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("userId").description("권한을 조회할 사용자")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공여부"),
                    fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                    fieldWithPath("body.authList").type(JsonFieldType.ARRAY).description("권한 리스트")
                )
            ));
    }



    @Test
    @Order(4)
    void updateUserAuthTest() throws Exception{

        UserAuthMultiId multiId = new UserAuthMultiId();
        multiId.setUserId(TEST_ID);
        multiId.setCodeId(TEST_CODE);

        UserAuth us = UserAuth.builder().userAuthMultiId(multiId).build();

        mockMvc.perform(
            RestDocumentationRequestBuilders.put(URL_PREFIX + CODE_ID, Role.PREPROCESSING.toString()) //수정할 codeId
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectConverter.toJson(us))
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("codeId").description("수정하려는 codeId의 값")
                ),
                requestFields(
                    fieldWithPath("userAuthMultiId.userId").type(JsonFieldType.STRING).description("사용자 아이디"),
                    fieldWithPath("userAuthMultiId.codeId").type(JsonFieldType.STRING).description("코드 아이디")
                )
            ));
    }


    @Test
    @Order(5)
    void deleteUserAuthTest() throws Exception{

        UserAuthMultiId multiId = new UserAuthMultiId();
        multiId.setUserId(TEST_ID);
        multiId.setCodeId(TEST_CODE);

        UserAuth us = UserAuth.builder().userAuthMultiId(multiId).build();

        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(URL_PREFIX)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectConverter.toJson(us))
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                requestFields(
                    fieldWithPath("userAuthMultiId.userId").type(JsonFieldType.STRING).description("사용자 아이디"),
                    fieldWithPath("userAuthMultiId.codeId").type(JsonFieldType.STRING).description("코드 아이디")
                )
            ));
    }
}
