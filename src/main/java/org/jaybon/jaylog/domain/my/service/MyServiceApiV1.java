package org.jaybon.jaylog.domain.my.service;


import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.common.constants.Constants;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.jaybon.jaylog.config.security.auth.CustomUserDetails;
import org.jaybon.jaylog.domain.my.dto.req.ReqMyPutDTOApiV1;
import org.jaybon.jaylog.domain.my.dto.res.ResMyGetDTOApiV1;
import org.jaybon.jaylog.model.article.entity.ArticleEntity;
import org.jaybon.jaylog.model.like.entity.LikeEntity;
import org.jaybon.jaylog.model.like.repository.LikeRepository;
import org.jaybon.jaylog.model.user.entity.UserEntity;
import org.jaybon.jaylog.model.user.repository.UserRepository;
import org.jaybon.jaylog.util.UtilFunction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyServiceApiV1 {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<ResDTO<ResMyGetDTOApiV1>> getBy(CustomUserDetails customUserDetails) {
        UserEntity userEntity = UtilFunction.getUserEntityBy(userRepository, customUserDetails);
        List<ArticleEntity> myArticleEntityList = userEntity.getArticleEntityList();
        List<LikeEntity> myLikeEntityList = likeRepository.findByUserEntity_Id(userEntity.getId());
        List<ArticleEntity> likeArticleEntityList = myLikeEntityList
                .stream()
                .filter(likeEntity -> likeEntity.getArticleEntity().getDeleteDate() != null)
                .map(likeEntity -> likeEntity.getArticleEntity())
                .toList();
        return new ResponseEntity<>(
                ResDTO.<ResMyGetDTOApiV1>builder()
                        .code(Constants.ResCode.OK)
                        .message("마이페이지 조회에 성공했습니다.")
                        .data(ResMyGetDTOApiV1.of(userEntity, myArticleEntityList, likeArticleEntityList))
                        .build(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<ResDTO<Object>> putBy(ReqMyPutDTOApiV1 dto, CustomUserDetails customUserDetails) {
        UserEntity userEntity = UtilFunction.getUserEntityBy(userRepository, customUserDetails);
        dto.getUser().updateWith(userEntity, passwordEncoder);
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(Constants.ResCode.OK)
                        .message("회원 정보 수정에 성공했습니다.")
                        .build(),
                HttpStatus.OK
        );
    }
}
