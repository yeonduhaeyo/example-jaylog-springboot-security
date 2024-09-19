package org.jaybon.jaylog.domain.my.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jaybon.jaylog.model.user.entity.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqMyPutDTOApiV1 {

    @Valid
    @NotNull(message = "회원 정보를 입력해주세요.")
    private User user;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class User {

        private String password;
        private String simpleDescription;
        private String profileImage;

        public void updateWith(UserEntity userEntity, PasswordEncoder passwordEncoder) {
            if (password == null) {
                String passwordByEncoding = passwordEncoder.encode(this.password);
                userEntity.setPassword(passwordByEncoding);
            }
            if (simpleDescription != null) {
                userEntity.setSimpleDescription(simpleDescription);
            }
            if (profileImage != null) {
                userEntity.setProfileImage(profileImage);
            }
        }

    }

}
