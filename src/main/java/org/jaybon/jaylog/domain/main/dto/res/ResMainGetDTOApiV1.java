package org.jaybon.jaylog.domain.main.dto.res;


import lombok.*;
import org.jaybon.jaylog.common.constants.Constants;
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

    public static ResMainGetDTOApiV1 of(Page<ArticleEntity> articleEntityPage, UserEntity loginUserEntity) {
        return ResMainGetDTOApiV1.builder()
                .articlePage(new ArticlePage(articleEntityPage, loginUserEntity))
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

        public ArticlePage(Page<ArticleEntity> npostScrapEntityPage, UserEntity loginUserEntity) {
            super(
                    Article.from(npostScrapEntityPage.getContent(), loginUserEntity),
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
            private Writer writer;
            private String title;
            private String thumbnail;
            private String summary;
            private Long likeCount;
            private Boolean isLikeClicked;
            private LocalDateTime createDate;

            public static List<Article> from(List<ArticleEntity> articleEntityList, UserEntity loginUserEntity) {
                return articleEntityList.stream()
                        .map(articleEntity -> Article.from(articleEntity, loginUserEntity))
                        .toList();
            }

            public static List<Article> from(List<ArticleEntity> articleEntityList) {
                return articleEntityList.stream()
                        .map(Article::from)
                        .toList();
            }

            public static Article from(ArticleEntity articleEntity, UserEntity loginUserEntity) {
                Writer writer;
                if (Objects.isNull(articleEntity.getUserEntity().getDeleteDate())) {
                    writer = Writer.builder()
                            .username(articleEntity.getUserEntity().getUsername())
                            .profileImage(articleEntity.getUserEntity().getProfileImage())
                            .build();
                } else {
                    writer = Writer.builder()
                            .username("탈퇴한 사용자")
                            .profileImage("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")
                            .build();
                }
                boolean isLikeClicked = articleEntity.getLikeEntityList()
                        .stream()
                        .anyMatch(likeEntity -> Objects.equals(likeEntity.getUserEntity().getId(), loginUserEntity.getId()));
                return Article.builder()
                        .id(articleEntity.getId())
                        .writer(writer)
                        .title(articleEntity.getTitle())
                        .thumbnail(articleEntity.getThumbnail())
                        .summary(
                                articleEntity.getContent()
                                        .replaceAll(Constants.Regex.MARKDOWN, "")
                                        .substring(0, 151)
                        )
                        .likeCount((long) articleEntity.getLikeEntityList().size())
                        .isLikeClicked(isLikeClicked)
                        .createDate(articleEntity.getCreateDate())
                        .build();
            }

            public static Article from(ArticleEntity articleEntity) {
                Writer writer;
                if (Objects.isNull(articleEntity.getUserEntity().getDeleteDate())) {
                    writer = Writer.builder()
                            .username(articleEntity.getUserEntity().getUsername())
                            .profileImage(articleEntity.getUserEntity().getProfileImage())
                            .build();
                } else {
                    writer = Writer.builder()
                            .username("탈퇴한 사용자")
                            .profileImage("https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png")
                            .build();
                }
                return Article.builder()
                        .id(articleEntity.getId())
                        .writer(writer)
                        .title(articleEntity.getTitle())
                        .thumbnail(articleEntity.getThumbnail())
                        .summary(
                                articleEntity.getContent()
                                        .replaceAll(Constants.Regex.MARKDOWN, "")
                                        .substring(0, 151)
                        )
                        .likeCount((long) articleEntity.getLikeEntityList().size())
                        .isLikeClicked(false)
                        .createDate(articleEntity.getCreateDate())
                        .build();
            }

            @Getter
            @Builder
            @NoArgsConstructor
            @AllArgsConstructor
            public static class Writer {
                private String username;
                private String profileImage;
            }

        }

    }

}
