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
import org.springframework.test.web.servlet.ResultActions;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;
import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import kr.co.pcninc.bigdata.cooperationmodule.api.controller.CodeRestController;
import kr.co.pcninc.bigdata.cooperationmodule.code.Role;
import kr.co.pcninc.bigdata.cooperationmodule.utils.ObjectConverter;
import kr.co.pcninc.bigdata.cooperationmodule.domain.Code;

public class CodeRestControllerTests extends ControllerTestBase{

    public static final String URL_PREFIX = CodeRestController.URL_PREFIX;
    public static final String URL_RESOURCE = CodeRestController.URL_RESOURCE;
    public static final String VALUE = "TEST";//Role.VISUALIZATION_PRO.toString();

    @Test
    @Order(1)
    void createCodeTest() throws Exception{

        Code code = Code.builder().codeId(VALUE).codeName("시각화 프로파일링").build();

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(URL_PREFIX)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectConverter.toJson(code))
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                requestFields(
                    fieldWithPath("codeId").type(JsonFieldType.STRING).description("코드 아이디"),
                    fieldWithPath("codeName").type(JsonFieldType.STRING).description("코드 이름")
                )
            ));
    }


    @Test
    @Order(2)
    void getCodeListTest() throws Exception{
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
                    fieldWithPath("body[0].codeId").type(JsonFieldType.STRING).description("코드 아이디"),
                    fieldWithPath("body[0].codeName").type(JsonFieldType.STRING).description("코드 이름")
                )
            ));
    }


    @Test
    @Order(3)
    void getCodeTest() throws Exception{
        mockMvc.perform(
            RestDocumentationRequestBuilders.get(URL_PREFIX + URL_RESOURCE, VALUE)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                  parameterWithName("codeId").description("조회할 코드 아이디")
                ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공여부"),
                    fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                    fieldWithPath("body.codeId").type(JsonFieldType.STRING).description("코드 아이디"),
                    fieldWithPath("body.codeName").type(JsonFieldType.STRING).description("코드 이름")
                )
            ));
    }


    @Test
    @Order(4)
    void updateCodeTest() throws Exception{
        Code code = Code.builder().codeId(VALUE).codeName("테스트").build();

        mockMvc.perform(
            RestDocumentationRequestBuilders.put(URL_PREFIX + URL_RESOURCE, VALUE)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(ObjectConverter.toJson(code))
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(
                    parameterWithName("codeId").description("수정할 코드의 아이디")
                ),
                requestFields(
                    fieldWithPath("codeId").type(JsonFieldType.STRING).description("코드 아이디"),
                    fieldWithPath("codeName").type(JsonFieldType.STRING).description("코드 이름")
                )
            ));
    }


    @Test
    @Order(5)
    void deleteCodeTest() throws Exception{
        mockMvc.perform(
            RestDocumentationRequestBuilders.delete(URL_PREFIX + URL_RESOURCE, VALUE)
                .header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                pathParameters(parameterWithName("codeId").description("삭제할 코드 아이디"))
            ));
    }
}
