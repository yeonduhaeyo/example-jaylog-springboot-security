package org.jaybon.jaylog.domain.article.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jaybon.jaylog.model.article.entity.ArticleEntity;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResArticleGetByIdDTOApiV1 {

    private Article article;

    public static ResArticleGetByIdDTOApiV1 of(ArticleEntity articleEntity) {
        return ResArticleGetByIdDTOApiV1.builder()
                .article(Article.fromEntity(articleEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Article {

        private Long id;
        private User user;
        private String title;
        private String thumbnail;
        private String content;
        private Integer likeCount;
        private Boolean isLikeClicked;
        private LocalDateTime createDate;

        public static Article fromEntity(ArticleEntity articleEntity) {
            return Article.builder()
                    .id(articleEntity.getId())
                    .user(
                            User.builder()
                                    .username(articleEntity.getUserEntity().getUsername())
                                    .profileImage(articleEntity.getUserEntity().getProfileImage())
                                    .build()
                    )
                    .title(articleEntity.getTitle())
                    .thumbnail(articleEntity.getThumbnail())
                    .content(articleEntity.getContent())
                    .likeCount(articleEntity.getLikeEntityList().size())
                    .isLikeClicked(
                            articleEntity.getLikeEntityList()
                                    .stream()
                                    .anyMatch(likeEntity -> Objects.equals(likeEntity.getUserEntity().getId(), articleEntity.getUserEntity().getId()))
                    )
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
