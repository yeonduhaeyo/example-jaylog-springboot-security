package org.jaybon.jaylog.domain.my.dto.req;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.jaybon.jaylog.model.user.entity.UserEntity;
import org.jaybon.jaylog.util.UtilFunction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
//@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class ReqMyPutInfoDTOApiV1 {
    // @NoArgsConstructor(기본 생성자)가 있으면 multipart/form-data로 받을 때 데이터 바인딩이 정상적으로 이루어지지 않는다.
    // 기본 생성자를 사용하려면 setter와 같이 사용해야함

    private String password;
    private String simpleDescription;
    private MultipartFile profileImage;

    public void updateWith(PasswordEncoder passwordEncoder, UserEntity userEntity) {
        if (password != null) {
            String passwordByEncoding = passwordEncoder.encode(this.password);
            userEntity.setPassword(passwordByEncoding);
        }
        if (simpleDescription != null) {
            userEntity.setSimpleDescription(simpleDescription);
        }
        if (profileImage != null && !profileImage.isEmpty()) {
            userEntity.setProfileImage(UtilFunction.convertImageToBase64(profileImage));
        }
    }


}
