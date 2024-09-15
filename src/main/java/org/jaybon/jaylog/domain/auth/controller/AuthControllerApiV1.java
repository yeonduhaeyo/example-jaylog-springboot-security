package org.jaybon.jaylog.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.jaybon.jaylog.domain.auth.dto.req.ReqAuthPostJoinDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.req.ReqAuthPostLoginDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.req.ReqAuthPostRefreshDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.res.ResAuthPostLoginDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.res.ResAuthPostRefreshDTOApiV1;
import org.jaybon.jaylog.domain.auth.service.AuthServiceApiV1;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthControllerApiV1 {

    private final AuthServiceApiV1 authServiceApiV1;

    @PostMapping("/join")
    public ResponseEntity<ResDTO<Object>> joinBy(@Valid @RequestBody ReqAuthPostJoinDTOApiV1 dto) {
        return authServiceApiV1.joinBy(dto);
    }

    @PostMapping("/login")
    public ResponseEntity<ResDTO<ResAuthPostLoginDTOApiV1>> loginBy(@Valid @RequestBody ReqAuthPostLoginDTOApiV1 dto) {
        return authServiceApiV1.loginBy(dto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResDTO<ResAuthPostRefreshDTOApiV1>> refreshBy(@Valid @RequestBody ReqAuthPostRefreshDTOApiV1 dto) {
        return authServiceApiV1.refreshBy(dto);
    }

}
