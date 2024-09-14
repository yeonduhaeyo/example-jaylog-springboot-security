package org.jaybon.jaylog.domain.article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.jaybon.jaylog.config.security.auth.CustomUserDetails;
import org.jaybon.jaylog.domain.article.dto.req.ReqArticlePostDTOApiV1;
import org.jaybon.jaylog.domain.article.dto.req.ReqArticlePutDTOApiV1;
import org.jaybon.jaylog.domain.article.dto.res.ResArticleGetByIdDTOApiV1;
import org.jaybon.jaylog.domain.article.dto.res.ResArticlePostLikeByIdDTOApiV1;
import org.jaybon.jaylog.domain.article.service.ArticleServiceApiV1;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/article")
public class ArticleControllerApiV1 {

    private final ArticleServiceApiV1 articleServiceApiV1;

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResArticleGetByIdDTOApiV1>> getById(@PathVariable Long id) {
        return articleServiceApiV1.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResDTO<Object>> post(
            @Valid @RequestBody ReqArticlePostDTOApiV1 dto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return articleServiceApiV1.post(dto, customUserDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> put(
            @PathVariable Long id,
            @Valid @RequestBody ReqArticlePutDTOApiV1 dto,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return articleServiceApiV1.put(id, dto, customUserDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return articleServiceApiV1.delete(id, customUserDetails);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<ResDTO<ResArticlePostLikeByIdDTOApiV1>> postLikeById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return articleServiceApiV1.postLikeById(id, customUserDetails);
    }

}
