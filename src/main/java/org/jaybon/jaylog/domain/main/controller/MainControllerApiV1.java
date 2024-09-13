package org.jaybon.jaylog.domain.main.controller;

import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/main")
public class MainControllerApiV1 {
    
    @GetMapping
    public ResponseEntity<ResDTO<Object>> get() {
        return null;
    }

}
