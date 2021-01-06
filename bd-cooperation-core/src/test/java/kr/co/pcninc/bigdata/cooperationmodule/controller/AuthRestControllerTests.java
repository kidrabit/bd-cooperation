package kr.co.pcninc.bigdata.cooperationmodule.controller;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import kr.co.pcninc.bigdata.cooperationmodule.api.controller.auth.AuthRestController;
import kr.co.pcninc.bigdata.cooperationmodule.domain.Token;
import kr.co.pcninc.bigdata.cooperationmodule.domain.Workspace;
import kr.co.pcninc.bigdata.cooperationmodule.utils.ObjectConverter;

public class AuthRestControllerTests extends ControllerTestBase {
    static final String URL_PREFIX = AuthRestController.URL_PREFIX;
    static final String LOGIN = AuthRestController.LOGIN;

    @Test
    @Order(1)
    void createWsTest() throws Exception{

        Token.Request request = new Token.Request("digitalship", "1234");

        String reqStr = ObjectConverter.toJson(request);

        mockMvc.perform(
            RestDocumentationRequestBuilders.post(URL_PREFIX + LOGIN)
                //.header(ApiConstants.API_ACCESS_TOKEN_ATTRIBUTE_NAME,accessToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(reqStr)
        )
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document.document(
                requestFields(
                    fieldWithPath("id").type(JsonFieldType.STRING).description("사용자 아이디"),
                    fieldWithPath("secret").type(JsonFieldType.STRING).description("사용자 패스워드")
                    ),
                responseFields(
                    fieldWithPath("result").type(JsonFieldType.STRING).description("성공 여부"),
                    fieldWithPath("failure").type(JsonFieldType.STRING).description("실패").optional(),
                    fieldWithPath("body.token").type(JsonFieldType.STRING).description("토큰 값")
                )
            ));
    }
}
