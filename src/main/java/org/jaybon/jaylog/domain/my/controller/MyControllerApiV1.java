package org.jaybon.jaylog.domain.my.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/my")
public class MyControllerApiV1 {

    @GetMapping
    public ResponseEntity<ResDTO<Object>> get() {
        return null;
    }

    @PutMapping("/change-info")
    public ResponseEntity<ResDTO<Object>> putChangeInfoBy(@PathVariable Long id, @Valid @RequestBody Object dto) {
        return null;
    }

}
