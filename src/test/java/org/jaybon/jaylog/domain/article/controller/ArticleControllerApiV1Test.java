package org.jaybon.jaylog.domain.article.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jaybon.jaylog.common.constants.Constants;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.jaybon.jaylog.domain.article.dto.req.ReqArticlePostDTOApiV1;
import org.jaybon.jaylog.domain.article.dto.req.ReqArticlePutDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.req.ReqAuthPostLoginDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.res.ResAuthPostLoginDTOApiV1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class ArticleControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testArticleGetSuccessByAuth() throws Exception {
        ResDTO<ResAuthPostLoginDTOApiV1> resDto =  objectMapper.readValue(
                login().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/article/{id}", 2)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resDto.getData().getAccessJwt())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("ARTICLE 개별 조회 성공 (0.인증)",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("ARTICLE v1")
                                        .summary("ARTICLE 개별 조회")
                                        .description("""
                                                ## ARTICLE 개별 조회 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                """)
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.NUMBER).description("게시글 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testArticleGetSuccessByNoAuth() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/article/{id}", 2)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("ARTICLE 개별 조회 성공 (1.비인증)",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("ARTICLE v1")
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.NUMBER).description("게시글 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testArticlePostSuccess() throws Exception {
        ResDTO<ResAuthPostLoginDTOApiV1> resDto =  objectMapper.readValue(
                login().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        ReqArticlePostDTOApiV1 reqDto = ReqArticlePostDTOApiV1.builder()
                .article(
                        ReqArticlePostDTOApiV1.Article.builder()
                                .title("테스트 제목")
                                .content("테스트 내용")
                                .build()
                )
                .build();
        String reqDtoJson = objectMapper.writeValueAsString(reqDto);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/article")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resDto.getData().getAccessJwt())
                                .content(reqDtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("ARTICLE 삽입 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("ARTICLE v1")
                                        .summary("ARTICLE 삽입")
                                        .description("""
                                                ## ARTICLE 삽입 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                """)
                                        .requestFields(
                                                fieldWithPath("article.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                                fieldWithPath("article.content").type(JsonFieldType.STRING).description("게시글 내용")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testArticlePutSuccess() throws Exception {
        ResDTO<ResAuthPostLoginDTOApiV1> resDto =  objectMapper.readValue(
                login().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        ReqArticlePutDTOApiV1 reqDto = ReqArticlePutDTOApiV1.builder()
                .article(
                        ReqArticlePutDTOApiV1.Article.builder()
                                .title("테스트 수정 제목")
                                .content("테스트 수정 내용")
                                .build()
                )
                .build();
        String reqDtoJson = objectMapper.writeValueAsString(reqDto);
        mockMvc.perform(
                        RestDocumentationRequestBuilders.put("/v1/article/{id}", 1)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resDto.getData().getAccessJwt())
                                .content(reqDtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("ARTICLE 수정 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("ARTICLE v1")
                                        .summary("ARTICLE 수정")
                                        .description("""
                                                ## ARTICLE 수정 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                """)
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.NUMBER).description("게시글 ID")
                                        )
                                        .requestFields(
                                                fieldWithPath("article.title").type(JsonFieldType.STRING).description("게시글 제목"),
                                                fieldWithPath("article.content").type(JsonFieldType.STRING).description("게시글 내용")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testArticleDeleteSuccess() throws Exception {
        ResDTO<ResAuthPostLoginDTOApiV1> resDto =  objectMapper.readValue(
                login().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        mockMvc.perform(
                        RestDocumentationRequestBuilders.delete("/v1/article/{id}", 1)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resDto.getData().getAccessJwt())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("ARTICLE 삭제 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("ARTICLE v1")
                                        .summary("ARTICLE 삭제")
                                        .description("""
                                                ## ARTICLE 삭제 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                """)
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.NUMBER).description("게시글 ID")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testArticlePostLikeSuccess() throws Exception {
        ResDTO<ResAuthPostLoginDTOApiV1> resDto =  objectMapper.readValue(
                login().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/v1/article/{id}/like", 1)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resDto.getData().getAccessJwt())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("ARTICLE 좋아요 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("ARTICLE v1")
                                        .summary("ARTICLE 좋아요")
                                        .description("""
                                                ## ARTICLE 좋아요 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                개별 유저가 좋아요 한 게시글에 한번 더 좋아요를 하게 되면 좋아요가 취소됩니다.
                                                
                                                """)
                                        .pathParameters(
                                                parameterWithName("id").type(SimpleType.NUMBER).description("게시글 ID")
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
