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
public class ResMyGetInfoDTOApiV1 {

    private LoginUser loginUser;

    public static ResMyGetInfoDTOApiV1 of(
            UserEntity loginUserEntity
    ) {
        return ResMyGetInfoDTOApiV1.builder()
                .loginUser(LoginUser.from(loginUserEntity))
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

}
