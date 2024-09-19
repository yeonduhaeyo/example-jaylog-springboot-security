package org.jaybon.jaylog.domain.main.service;


import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.common.constants.Constants;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.jaybon.jaylog.config.security.auth.CustomUserDetails;
import org.jaybon.jaylog.domain.main.dto.res.ResMainGetDTOApiV1;
import org.jaybon.jaylog.model.article.entity.ArticleEntity;
import org.jaybon.jaylog.model.article.repository.ArticleRepository;
import org.jaybon.jaylog.model.user.entity.UserEntity;
import org.jaybon.jaylog.model.user.repository.UserRepository;
import org.jaybon.jaylog.util.UtilFunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainServiceApiV1 {

    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public ResponseEntity<ResDTO<ResMainGetDTOApiV1>> getBy(
            String searchValue,
            Pageable pageable,
            CustomUserDetails customUserDetails
    ) {
        UserEntity userEntity;
        if (customUserDetails != null) {
            userEntity = UtilFunction.getUserEntityBy(userRepository, customUserDetails);
        } else {
            userEntity = null;
        }
        Page<ArticleEntity> articleEntityPage;
        if (searchValue == null || searchValue.isEmpty()) {
            articleEntityPage = articleRepository.findByDeleteDateIsNull(pageable);
        } else {
            articleEntityPage = articleRepository.findByTitleContainingOrContentContainingAndDeleteDateIsNull(searchValue, pageable);
        }
        ResMainGetDTOApiV1 resMainGetDTOApiV1;
        if (userEntity != null) {
            resMainGetDTOApiV1 = ResMainGetDTOApiV1.of(articleEntityPage, userEntity);
        } else {
            resMainGetDTOApiV1 = ResMainGetDTOApiV1.of(articleEntityPage);
        }
        return new ResponseEntity<>(
                ResDTO.<ResMainGetDTOApiV1>builder()
                        .code(Constants.ResCode.OK)
                        .message("메인 조회에 성공했습니다.")
                        .data(resMainGetDTOApiV1)
                        .build(),
                HttpStatus.OK
        );
    }
}
