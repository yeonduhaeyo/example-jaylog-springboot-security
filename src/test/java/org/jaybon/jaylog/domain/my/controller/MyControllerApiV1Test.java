package org.jaybon.jaylog.domain.my.controller;

import com.epages.restdocs.apispec.FieldDescriptors;
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
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;

@SpringBootTest
@AutoConfigureRestDocs
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("dev")
public class MyControllerApiV1Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testMyGetSuccess() throws Exception {
        ResDTO<ResAuthPostLoginDTOApiV1> resDto = objectMapper.readValue(
                login().getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/my")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resDto.getData().getAccessJwt())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("MY 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("MY v1")
                                        .summary("MY 조회")
                                        .description("""
                                                ## MY 조회 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                """)
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testMyGetInfoSuccess() throws Exception {
        ResDTO<ResAuthPostLoginDTOApiV1> resDto = objectMapper.readValue(
                login().getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/v1/my/info")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resDto.getData().getAccessJwt())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("MY 인포 조회 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("MY v1")
                                        .summary("MY 인포 조회")
                                        .description("""
                                                ## MY 인포 조회 엔드포인트 입니다.
                                                
                                                ---
                                                
                                                """)
                                        .build()
                                )
                        )
                );
    }

    @Test
    public void testMyPutInfoSuccess() throws Exception {
        ResDTO<ResAuthPostLoginDTOApiV1> resDto = objectMapper.readValue(
                login().getResponse().getContentAsString(),
                new TypeReference<>() {
                }
        );
        MockMultipartFile profileImage = new MockMultipartFile(
                "profileImage",            // form field 이름
                "test.png",        // 파일 이름
                MediaType.IMAGE_PNG_VALUE, // 파일의 MIME 타입
                "Test file content".getBytes() // 파일 내용
        );
        mockMvc.perform(
                        RestDocumentationRequestBuilders.multipart("/v1/my/info")
                                .file(profileImage)
                                .param("password", "1234")
                                .param("simpleDescription", "간단한 자기소개 입니다!")
//                                .part(
//                                        new MockPart("password", "1234".getBytes()),
//                                        new MockPart("simpleDescription", "간단한 자기소개 입니다!".getBytes())
//                                )
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .with(request -> {
                                    request.setMethod("PUT");
                                    return request;
                                })
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + resDto.getData().getAccessJwt())
                )
                .andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.jsonPath("code").value(Constants.ResCode.OK)
                )
                .andDo(
                        document("MY 인포 수정 성공",
                                preprocessRequest(prettyPrint()),
                                preprocessResponse(prettyPrint()),
                                resource(ResourceSnippetParameters.builder()
                                        .tag("MY v1")
                                        .summary("MY 인포 수정")
                                        .description("""
                                                ## MY 인포 수정 엔드포인트 입니다.
                                                
                                                ---
                                                문서화가 되지 않아서 아래에 안내 합니다.
                                                
                                                multipart/form-data 타입으로 요청이 가능합니다.
                                                
                                                파일 profileImage
                                                
                                                문자열 password, simpleDescription
                                                
                                                """)
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
