package org.jaybon.jaylog.domain.article.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jaybon.jaylog.model.article.entity.ArticleEntity;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResArticlePostDTOApiV1 {

    private Article article;

    public static ResArticlePostDTOApiV1 of(ArticleEntity articleEntity) {
        return ResArticlePostDTOApiV1.builder()
                .article(ResArticlePostDTOApiV1.Article.from(articleEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Article {

        private Long id;

        public static Article from(ArticleEntity articleEntity) {
            return Article.builder()
                    .id(articleEntity.getId())
                    .build();
        }

    }

}
