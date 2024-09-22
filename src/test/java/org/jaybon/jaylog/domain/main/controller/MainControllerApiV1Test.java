package org.jaybon.jaylog.domain.main.controller;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.SimpleType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jaybon.jaylog.common.constants.Constants;
import org.jaybon.jaylog.common.dto.ResDTO;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class MainControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testMainGetSuccessByAuth() throws Exception {
        ResDTO<ResAuthPostLoginDTOApiV1> resDto =  objectMapper.readValue(
                login().getResponse().getContentAsString(),
                new TypeReference<>() {}
        );
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/main")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resDto.getData().getAccessJwt())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("MAIN 페이징리스트 조회 성공 (0.인증)",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("MAIN v1")
                                        .summary("MAIN 페이징리스트 조회")
                                        .description("""
                                                ## MAIN 페이징리스트 조회 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                QueryString으로 검색이 가능합니다.
                                                                                                
                                                ex) searchValue=회고
                                                
                                                정렬 기준을 지정할 수 있습니다. 기본 정렬은 id,desc입니다.
                                                                                
                                                ex) sort=id,asc
                                                
                                                페이징이 가능합니다. 기본 페이지 번호는 0번입니다. 페이지 당 기본 조회 개수는 10개입니다.
                                                                     
                                                ex) size=5
                                                                                                
                                                ex) page=1
                                                                                        
                                                ex) page=2&size=5
                                                
                                                """)
                                        .queryParameters(
                                                parameterWithName("searchValue").type(SimpleType.STRING).optional().description("검색어"),
                                                parameterWithName("sort").type(SimpleType.STRING).optional().description("정렬 기준"),
                                                parameterWithName("page").type(SimpleType.NUMBER).optional().description("페이지 번호"),
                                                parameterWithName("size").type(SimpleType.NUMBER).optional().description("페이지 당 조회 개수")
                                        )
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testMainGetSuccessByNoAuth() throws Exception {
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/main")
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("MAIN 페이징리스트 조회 성공 (1.비인증)",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("MAIN v1")
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
