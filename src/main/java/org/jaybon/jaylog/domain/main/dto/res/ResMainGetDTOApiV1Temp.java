package org.jaybon.jaylog.domain.main.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jaybon.jaylog.common.constants.Constants;
import org.jaybon.jaylog.model.article.entity.ArticleEntity;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResMainGetDTOApiV1Temp {

    private List<Article> articleList;

    public static ResMainGetDTOApiV1Temp of(List<ArticleEntity> articleEntityList) {
        return ResMainGetDTOApiV1Temp.builder()
                .articleList(Article.from(articleEntityList))
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
        private String summary;
        private Integer likeCount;
        private LocalDateTime createDate;

        public static List<Article> from(List<ArticleEntity> articleEntityList) {
            return articleEntityList.stream()
                    .map(Article::from)
                    .toList();
        }

        public static Article from(ArticleEntity articleEntity) {
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
                    .summary(
                            articleEntity.getContent()
                                    .replaceAll(Constants.Regex.MARKDOWN, "")
                                    .substring(0, 151)
                    )
                    .likeCount(articleEntity.getLikeEntityList().size())
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
