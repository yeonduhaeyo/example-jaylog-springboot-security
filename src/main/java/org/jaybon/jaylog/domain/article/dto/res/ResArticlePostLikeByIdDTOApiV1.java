package org.jaybon.jaylog.domain.article.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResArticlePostLikeByIdDTOApiV1 {

    private Article article;

    public static ResArticlePostLikeByIdDTOApiV1 of(Long likeCount, Boolean isLikeClicked) {
        return ResArticlePostLikeByIdDTOApiV1.builder()
                .article(Article.from(likeCount, isLikeClicked))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Article {

        private Long likeCount;
        private Boolean isLikeClicked;

        public static Article from(Long likeCount, Boolean isLikeClicked) {
            return Article.builder()
                    .likeCount(likeCount)
                    .isLikeClicked(isLikeClicked)
                    .build();
        }

    }

}
