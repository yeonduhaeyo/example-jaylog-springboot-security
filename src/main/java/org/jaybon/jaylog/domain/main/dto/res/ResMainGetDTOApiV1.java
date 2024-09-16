package org.jaybon.jaylog.domain.main.dto.res;


import lombok.*;
import org.jaybon.jaylog.domain.article.dto.res.ResArticleGetByIdDTOApiV1;
import org.jaybon.jaylog.model.article.entity.ArticleEntity;
import org.jaybon.jaylog.model.user.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResMainGetDTOApiV1 {

    private ArticlePage articlePage;

    public static ResMainGetDTOApiV1 of(Page<ArticleEntity> articleEntityPage, UserEntity userEntity) {
        return ResMainGetDTOApiV1.builder()
                .articlePage(new ArticlePage(articleEntityPage, userEntity))
                .build();
    }

    public static ResMainGetDTOApiV1 of(Page<ArticleEntity> articleEntityPage) {
        return ResMainGetDTOApiV1.builder()
                .articlePage(new ArticlePage(articleEntityPage))
                .build();
    }

    @Getter
    @ToString
    public static class ArticlePage extends PageImpl<ArticlePage.Article> {

        public ArticlePage(Page<ArticleEntity> npostScrapEntityPage, UserEntity userEntity) {
            super(
                    Article.from(npostScrapEntityPage.getContent(), userEntity),
                    npostScrapEntityPage.getPageable(),
                    npostScrapEntityPage.getTotalElements()
            );
        }

        public ArticlePage(Page<ArticleEntity> npostScrapEntityPage) {
            super(
                    Article.from(npostScrapEntityPage.getContent()),
                    npostScrapEntityPage.getPageable(),
                    npostScrapEntityPage.getTotalElements()
            );
        }

        @Getter
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Article {

            private Long id;
            private Article.User user;
            private String title;
            private String thumbnail;
            private String content;
            private Long likeCount;
            private Boolean isLikeClicked;
            private LocalDateTime createDate;

            public static List<Article> from(List<ArticleEntity> articleEntityList, UserEntity userEntity) {
                return articleEntityList.stream()
                        .map(articleEntity -> Article.from(articleEntity, userEntity))
                        .toList();
            }

            public static List<Article> from(List<ArticleEntity> articleEntityList) {
                return articleEntityList.stream()
                        .map(Article::from)
                        .toList();
            }

            public static Article from(ArticleEntity articleEntity, UserEntity userEntity) {
                return Article.builder()
                        .id(articleEntity.getId())
                        .user(
                                Article.User.builder()
                                        .username(articleEntity.getUserEntity().getUsername())
                                        .profileImage(articleEntity.getUserEntity().getProfileImage())
                                        .build()
                        )
                        .title(articleEntity.getTitle())
                        .thumbnail(articleEntity.getThumbnail())
                        .content(articleEntity.getContent())
                        .likeCount((long) articleEntity.getLikeEntityList().size())
                        .isLikeClicked(
                                articleEntity.getLikeEntityList()
                                        .stream()
                                        .anyMatch(likeEntity -> Objects.equals(likeEntity.getUserEntity().getId(), userEntity.getId()))
                        )
                        .createDate(articleEntity.getCreateDate())
                        .build();
            }

            public static Article from(ArticleEntity articleEntity) {
                return Article.builder()
                        .id(articleEntity.getId())
                        .user(
                                Article.User.builder()
                                        .username(articleEntity.getUserEntity().getUsername())
                                        .profileImage(articleEntity.getUserEntity().getProfileImage())
                                        .build()
                        )
                        .title(articleEntity.getTitle())
                        .thumbnail(articleEntity.getThumbnail())
                        .content(articleEntity.getContent())
                        .likeCount((long) articleEntity.getLikeEntityList().size())
                        .isLikeClicked(false)
                        .createDate(articleEntity.getCreateDate())
                        .build();
            }

            @Getter
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class User {
                private String username;
                private String profileImage;
            }

        }

    }

}
