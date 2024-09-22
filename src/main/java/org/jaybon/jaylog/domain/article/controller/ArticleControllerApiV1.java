package org.jaybon.jaylog.domain.article.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.jaybon.jaylog.config.security.auth.CustomUserDetails;
import org.jaybon.jaylog.domain.article.dto.req.ReqArticlePostDTOApiV1;
import org.jaybon.jaylog.domain.article.dto.req.ReqArticlePutDTOApiV1;
import org.jaybon.jaylog.domain.article.dto.res.ResArticleGetByIdDTOApiV1;
import org.jaybon.jaylog.domain.article.dto.res.ResArticlePostDTOApiV1;
import org.jaybon.jaylog.domain.article.dto.res.ResArticlePostLikeByIdDTOApiV1;
import org.jaybon.jaylog.domain.article.service.ArticleServiceApiV1;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/v1/article")
public class ArticleControllerApiV1 {

    private final ArticleServiceApiV1 articleServiceApiV1;

    @GetMapping("/{id}")
    public ResponseEntity<ResDTO<ResArticleGetByIdDTOApiV1>> getByIdAndCustomUserDetails(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return articleServiceApiV1.getByIdAndCustomUserDetails(id, customUserDetails);
    }

    @PostMapping
    public ResponseEntity<ResDTO<ResArticlePostDTOApiV1>> postBy(
            @Valid @RequestBody ReqArticlePostDTOApiV1 dto,
            @NotNull @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return articleServiceApiV1.postBy(dto, customUserDetails);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> putBy(
            @PathVariable Long id,
            @Valid @RequestBody ReqArticlePutDTOApiV1 dto,
            @NotNull @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return articleServiceApiV1.putBy(id, dto, customUserDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResDTO<Object>> deleteByIdAndCustomUserDetails(
            @PathVariable Long id,
            @NotNull @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return articleServiceApiV1.deleteByIdAndCustomUserDetails(id, customUserDetails);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<ResDTO<ResArticlePostLikeByIdDTOApiV1>> postLikeByIdAndCustomUserDetails(
            @PathVariable Long id,
            @NotNull @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return articleServiceApiV1.postLikeByIdAndCustomUserDetails(id, customUserDetails);
    }

}
