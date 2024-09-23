package org.jaybon.jaylog.domain.my.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jaybon.jaylog.common.exception.BadRequestException;
import org.jaybon.jaylog.model.user.entity.UserEntity;
import org.jaybon.jaylog.util.UtilFunction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
        boolean isInvalid = false;
        if (password != null) {
            isInvalid = true;
            String passwordByEncoding = passwordEncoder.encode(this.password);
            userEntity.setPassword(passwordByEncoding);
        }
        if (simpleDescription != null) {
            isInvalid = true;
            userEntity.setSimpleDescription(simpleDescription);
        }
        if (profileImage != null && !profileImage.isEmpty()) {
            if (profileImage.getSize() > 2048) {
                throw new BadRequestException("2킬로바이트 이하의 이미지로 요청해주세요.( https://favicon.io 사이트 활용 )");
            }
            isInvalid = true;
            userEntity.setProfileImage(UtilFunction.convertImageToBase64(profileImage));
        }
        if (isInvalid) {
            userEntity.setJwtValidator(Timestamp.valueOf(LocalDateTime.now()).getTime());
        }
    }


}
