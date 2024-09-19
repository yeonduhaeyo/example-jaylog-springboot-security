package org.jaybon.jaylog.domain.my.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.jaybon.jaylog.config.security.auth.CustomUserDetails;
import org.jaybon.jaylog.domain.my.dto.req.ReqMyPutDTOApiV1;
import org.jaybon.jaylog.domain.my.dto.res.ResMyGetDTOApiV1;
import org.jaybon.jaylog.domain.my.service.MyServiceApiV1;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/my")
public class MyControllerApiV1 {

    private final MyServiceApiV1 myServiceApiV1;

    @GetMapping
    public ResponseEntity<ResDTO<ResMyGetDTOApiV1>> getBy(
            @NotNull @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return myServiceApiV1.getBy(customUserDetails);
    }

    @PutMapping
    public ResponseEntity<ResDTO<Object>> putBy(
            @Valid @RequestBody ReqMyPutDTOApiV1 dto,
            @NotNull @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return myServiceApiV1.putBy(dto, customUserDetails);
    }

}
