package org.jaybon.jaylog.domain.my.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jaybon.jaylog.common.constants.Constants;
import org.jaybon.jaylog.model.article.entity.ArticleEntity;
import org.jaybon.jaylog.model.user.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResMyGetDTOApiV1 {

    private LoginUser loginUser;
    private List<Article> myArticleList;
    private List<Article> likeArticleList;

    public static ResMyGetDTOApiV1 of(
            UserEntity loginUserEntity,
            List<ArticleEntity> myArticleEntityList,
            List<ArticleEntity> likeArticleEntityList
    ) {
        return ResMyGetDTOApiV1.builder()
                .loginUser(LoginUser.from(loginUserEntity))
                .myArticleList(Article.from(myArticleEntityList, loginUserEntity))
                .likeArticleList(Article.from(likeArticleEntityList, loginUserEntity))
                .build();
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginUser {

        private String username;
        private String simpleDescription;
        private String profileImage;

        public static LoginUser from(UserEntity loginUserEntity) {
            return LoginUser.builder()
                    .username(loginUserEntity.getUsername())
                    .simpleDescription(loginUserEntity.getSimpleDescription())
                    .profileImage(loginUserEntity.getProfileImage())
                    .build();
        }

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
