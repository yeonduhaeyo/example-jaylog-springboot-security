package org.jaybon.jaylog.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.domain.auth.dto.req.ReqAuthPostRefreshDTOApiV1;
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

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@Valid @RequestBody ReqLoginDTOApiV1 dto, HttpSession session) {
//        return authServiceApiV1.login(dto, session);
//    }

//    @PostMapping("/join")
//    public ResponseEntity<?> join(@Valid @RequestBody ReqJoinDTOApiV1 dto) {
//        return authServiceApiV1.join(dto);
//    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody ReqAuthPostRefreshDTOApiV1 dto) {
        return authServiceApiV1.refresh(dto);
    }

}
