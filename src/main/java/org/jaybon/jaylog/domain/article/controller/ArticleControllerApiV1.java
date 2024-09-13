package org.jaybon.jaylog.domain.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/article")
public class ArticleControllerApiV1 {


    @GetMapping
    public ResponseEntity<ResDTO<Object>> get() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> getById(@PathVariable Long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<ResDTO<Object>> post(@Valid @RequestBody Object dto) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> put(
            @PathVariable Long id,
            @Valid @RequestBody Object dto
    ) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> delete(
            @PathVariable Long id,
            @Valid @RequestBody Object dto
    ) {
        return null;
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<ResDTO<Object>> postLikeByArticleId(
            @PathVariable Long id,
            @Valid @RequestBody Object dto
    ) {
        return null;
    }

}
