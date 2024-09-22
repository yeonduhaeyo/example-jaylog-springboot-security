package org.jaybon.jaylog.domain.auth.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jaybon.jaylog.common.constants.Constants;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.jaybon.jaylog.domain.auth.dto.req.ReqAuthPostJoinDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.req.ReqAuthPostLoginDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.req.ReqAuthPostRefreshDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.res.ResAuthPostLoginDTOApiV1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class AuthControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAuthPostJoinSuccess() throws Exception {
        ReqAuthPostJoinDTOApiV1 reqDto = ReqAuthPostJoinDTOApiV1.builder()
                .user(
                        ReqAuthPostJoinDTOApiV1.User.builder()
                                .username("test")
                                .password("123")
                                .build()
                )
                .build();
        String reqDtoJson = objectMapper.writeValueAsString(reqDto);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/auth/join")
                                .content(reqDtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("AUTH 회원가입 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("AUTH v1")
                                        .summary("AUTH 회원가입")
                                        .description("""
                                                ## AUTH 회원가입 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                username과 password를 받아 회원가입을 진행합니다.
                                                """)
                                        .requestFields(
                                                fieldWithPath("user.username").type(JsonFieldType.STRING).description("사용자 아이디"),
                                                fieldWithPath("user.password").type(JsonFieldType.STRING).description("사용자 비밀번호")
                                        )
                                        .build()
                                )
                        )
                )
                .andReturn();
    }

    @Test
    public void testAuthPostLoginSuccess() throws Exception {
        ReqAuthPostLoginDTOApiV1 reqDto = ReqAuthPostLoginDTOApiV1.builder()
                .user(
                        ReqAuthPostLoginDTOApiV1.User.builder()
                                .username("temp1")
                                .password("123")
                                .build()
                )
                .build();
        String reqDtoJson = objectMapper.writeValueAsString(reqDto);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/auth/login")
                                .content(reqDtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("AUTH 로그인 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("AUTH v1")
                                        .summary("AUTH 로그인")
                                        .description("""
                                                ## AUTH 로그인 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                username과 password를 받아 로그인을 진행합니다.
                                                """)
                                        .requestFields(
                                                fieldWithPath("user.username").type(JsonFieldType.STRING).description("사용자 아이디"),
                                                fieldWithPath("user.password").type(JsonFieldType.STRING).description("사용자 비밀번호")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testAuthPostRefreshSuccess() throws Exception {
        ResDTO<ResAuthPostLoginDTOApiV1> resDto = objectMapper.readValue(
                login().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        ReqAuthPostRefreshDTOApiV1 reqDto = ReqAuthPostRefreshDTOApiV1.builder()
                .refreshJwt(resDto.getData().getRefreshJwt())
                .build();
        String reqDtoJson = objectMapper.writeValueAsString(reqDto);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/auth/refresh")
                                .content(reqDtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("AUTH JWT 재발급 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("AUTH v1")
                                        .summary("AUTH JWT 재발급")
                                        .description("""
                                                ## AUTH JWT 재발급 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                refreshJWT를 받아 토큰 재발급을 진행합니다.
                                                """)
                                        .requestFields(
                                                fieldWithPath("refreshJwt").type(JsonFieldType.STRING).description("리프레시 토큰")
                                        )
                                        .build()
                                )
                        )
                );
    }

    private MvcResult login() throws Exception {
        ReqAuthPostLoginDTOApiV1 reqDto = ReqAuthPostLoginDTOApiV1.builder()
                .user(
                        ReqAuthPostLoginDTOApiV1.User.builder()
                                .username("temp1")
                                .password("123")
                                .build()
                )
                .build();
        String reqDtoJson = objectMapper.writeValueAsString(reqDto);
        return mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/auth/login")
                                .content(reqDtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andReturn();
    }

}
