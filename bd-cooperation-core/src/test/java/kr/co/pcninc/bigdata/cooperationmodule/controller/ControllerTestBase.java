package kr.co.pcninc.bigdata.cooperationmodule.controller;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import kr.co.pcninc.bigdata.cooperationmodule.ApiConstants;
import kr.co.pcninc.bigdata.cooperationmodule.common.Constants;
import kr.co.pcninc.bigdata.cooperationmodule.configuration.authorization.UserAuthentication;
import kr.co.pcninc.bigdata.cooperationmodule.configuration.credencial.JwtTokenProvider;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ControllerTestBase{

    @Autowired
    protected MockMvc mockMvc;
    protected RestDocumentationResultHandler document;


    protected String accessToken() {
        return ApiConstants.API_ACCESS_TOKEN_PREFIX + JwtTokenProvider.generateToken(new UserAuthentication("hong-gildong", null));
    }

    @BeforeEach
    public void setup(
        final WebApplicationContext webApplicationContext,
        final RestDocumentationContextProvider restDocumentation
    ) {
        this.document = document("{class-name}/{method-name}",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint())
            );

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter(new CharacterEncodingFilter(Constants.DEFAULT_CHARSET_VALUE, true))
            .apply(documentationConfiguration(restDocumentation).snippets().withEncoding(Constants.DEFAULT_CHARSET_VALUE))
            .alwaysDo(document)
            .build();
    }
}
