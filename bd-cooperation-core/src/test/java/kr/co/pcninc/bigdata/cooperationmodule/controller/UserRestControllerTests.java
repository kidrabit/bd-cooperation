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

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;
import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import kr.co.pcninc.bigdata.cooperationmodule.api.controller.UserRestController;
import kr.co.pcninc.bigdata.cooperationmodule.utils.ObjectConverter;
import kr.co.pcninc.bigdata.cooperationmodule.domain.User;


public class UserRestControllerTests extends ControllerTestBase{

    public static final String URL_PREFIX = UserRestController.URL_PREFIX;
    public static final String URL_RESOURCE = UserRestController.URL_RESOURCE;
    public static final String TEST_ID = "testUser2";


    @Test
    @Order(1)
    void createUserTest() throws Exception{

        User user = User.builder()
            .userId(TEST_ID)
            .userName("김영희")
            .userPwd("1234")
            .company("pcn")
            .position("사원")
            .build();


        mockMvc.perform(
            RestDocumentationRequestBuilders.post(URL_PREFIX)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectConverter.toJson(user))
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.STRING).description("사용자 아이디"),
                    fieldWithPath("userName").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("userPwd").type(JsonFieldType.STRING).description("사용자 패스워드"),
                    fieldWithPath("company").type(JsonFieldType.STRING).description("소속 회사").optional(),
                    fieldWithPath("position").type(JsonFieldType.STRING).description("직책").optional()
                )
            ));
    }


    @Test
    @Order(2)
    void getUserListTest() throws Exception{
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
                    fieldWithPath("body[0].userId").type(JsonFieldType.STRING).description("사용자 아이디"),
                    fieldWithPath("body[0].userName").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("body[0].userPwd").type(JsonFieldType.STRING).description("사용자 패스워드"),
                    fieldWithPath("body[0].company").type(JsonFieldType.STRING).description("소속 회사"),
                    fieldWithPath("body[0].position").type(JsonFieldType.STRING).description("직책")
                )
            ));
    }


    @Test
    @Order(3)
    void getUserByIdTest() throws Exception{
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
                    parameterWithName("userId").description("사용자 아이디")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공여부"),
                    fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                    fieldWithPath("body.userId").type(JsonFieldType.STRING).description("사용자 아이디"),
                    fieldWithPath("body.userName").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("body.userPwd").type(JsonFieldType.STRING).description("사용자 패스워드"),
                    fieldWithPath("body.company").type(JsonFieldType.STRING).description("소속 회사"),
                    fieldWithPath("body.position").type(JsonFieldType.STRING).description("직책")
                )
            ));
    }





    @Test
    @Order(4)
    void updateUserTest() throws Exception{

        User user = User.builder()
            .userId(TEST_ID)
            .userName("김영희")
            .userPwd("abcde")
            .company("pcn")
            .position("대리")
            .build();


        mockMvc.perform(
            RestDocumentationRequestBuilders.put(URL_PREFIX + URL_RESOURCE, TEST_ID)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectConverter.toJson(user))
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                  parameterWithName("userId").description("수정할 사용자 아이디")
                ),
                requestFields(
                    fieldWithPath("userId").type(JsonFieldType.STRING).description("사용자 아이디"),
                    fieldWithPath("userName").type(JsonFieldType.STRING).description("사용자 이름"),
                    fieldWithPath("userPwd").type(JsonFieldType.STRING).description("사용자 패스워드"),
                    fieldWithPath("company").type(JsonFieldType.STRING).description("소속 회사").optional(),
                    fieldWithPath("position").type(JsonFieldType.STRING).description("직책").optional()
                )
            ));



    }

    @Test
    @Order(5)
    void deleteUserTest() throws Exception{

        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(URL_PREFIX + URL_RESOURCE, TEST_ID)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("userId").description("삭제할 사용자 아이디")
                )
            ));
    }
}
