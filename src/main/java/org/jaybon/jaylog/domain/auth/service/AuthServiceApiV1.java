package org.jaybon.jaylog.domain.auth.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.jaybon.jaylog.common.constants.Constants;
import org.jaybon.jaylog.common.dto.ResDTO;
import org.jaybon.jaylog.common.exception.AuthenticationException;
import org.jaybon.jaylog.common.exception.BadRequestException;
import org.jaybon.jaylog.config.security.auth.CustomUserDetails;
import org.jaybon.jaylog.domain.auth.dto.req.ReqAuthPostJoinDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.req.ReqAuthPostLoginDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.req.ReqAuthPostRefreshDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.res.ResAuthPostLoginDTOApiV1;
import org.jaybon.jaylog.domain.auth.dto.res.ResAuthPostRefreshDTOApiV1;
import org.jaybon.jaylog.model.user.constraint.LoginType;
import org.jaybon.jaylog.model.user.constraint.RoleType;
import org.jaybon.jaylog.model.user.entity.UserEntity;
import org.jaybon.jaylog.model.user.entity.UserRoleEntity;
import org.jaybon.jaylog.model.user.repository.UserRepository;
import org.jaybon.jaylog.model.user.repository.UserRoleRepository;
import org.jaybon.jaylog.util.UtilFunction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthServiceApiV1 {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<ResDTO<Object>> joinBy(ReqAuthPostJoinDTOApiV1 dto) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsername(dto.getUser().getUsername());
        if (userEntityOptional.isPresent()) {
            throw new BadRequestException("이미 존재하는 아이디입니다.");
        }
        // ---
        // TODO: 아이디 정규식 검사
        // ---
        // TODO: 비밀번호 정규식 검사
        // ---
        UserEntity userEntityForSaving = UserEntity.builder()
                .username(dto.getUser().getUsername())
                .password(passwordEncoder.encode(dto.getUser().getPassword()))
                .loginType(LoginType.DEFAULT)
                .jwtValidator(0L)
                .createDate(LocalDateTime.now())
                .build();
        UserRoleEntity userRoleEntityForSaving = UserRoleEntity.builder()
                .userEntity(userEntityForSaving)
                .role(RoleType.USER)
                .build();
        userRepository.save(userEntityForSaving);
        userRoleRepository.save(userRoleEntityForSaving);
        return new ResponseEntity<>(
                ResDTO.builder()
                        .code(0)
                        .message("회원가입에 성공하였습니다.")
                        .build(),
                HttpStatus.OK
        );
    }

    @Transactional
    public ResponseEntity<ResDTO<ResAuthPostLoginDTOApiV1>> loginBy(ReqAuthPostLoginDTOApiV1 dto) {
        Optional<UserEntity> userEntityOptional = userRepository.findByUsernameAndDeleteDateIsNull(dto.getUser().getUsername());
        if (userEntityOptional.isEmpty()) {
            throw new BadRequestException("아이디를 정확히 입력해주세요.");
        }
        UserEntity userEntity = userEntityOptional.get();
        if (!passwordEncoder.matches(dto.getUser().getPassword(), userEntity.getPassword())) {
            throw new BadRequestException("비밀번호를 정확히 입력해주세요.");
        }
        CustomUserDetails customUserDetails = CustomUserDetails.of(userEntityOptional.get());
        String accessJwt = UtilFunction.generateAccessJwtBy(customUserDetails);
        String refreshJwt = UtilFunction.generateRefreshJwtBy(customUserDetails);
        return new ResponseEntity<>(
                ResDTO.<ResAuthPostLoginDTOApiV1>builder()
                        .code(0)
                        .message("로그인에 성공하였습니다.")
                        .data(ResAuthPostLoginDTOApiV1.of(accessJwt, refreshJwt))
                        .build(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<ResDTO<ResAuthPostRefreshDTOApiV1>> refreshBy(ReqAuthPostRefreshDTOApiV1 dto) {
        DecodedJWT decodedRefreshJWT;
        try {
            decodedRefreshJWT = JWT.require(Algorithm.HMAC512(Constants.Jwt.SECRET))
                    .build()
                    .verify(dto.getRefreshJwt());
        } catch (JWTVerificationException e) {
            throw new AuthenticationException("유효하지 않은 토큰입니다.");
        }
        Optional<UserEntity> userEntityOptional = userRepository.findByUsernameAndDeleteDateIsNull(decodedRefreshJWT.getClaim("username").asString());
        if (userEntityOptional.isEmpty()) {
            throw new AuthenticationException("존재하지 않는 사용자입니다.");
        }
        UserEntity userEntity = userEntityOptional.get();
        if (userEntity.getJwtValidator() > decodedRefreshJWT.getClaim("timestamp").asLong()) {
            throw new AuthenticationException("유효하지 않은 토큰입니다.");
        }
        CustomUserDetails customUserDetails = CustomUserDetails.of(userEntityOptional.get());
        String accessJwt = UtilFunction.generateAccessJwtBy(customUserDetails);
        String refreshJwt = UtilFunction.generateRefreshJwtBy(customUserDetails);
        return new ResponseEntity<>(
                ResDTO.<ResAuthPostRefreshDTOApiV1>builder()
                        .code(0)
                        .message("토큰 갱신에 성공하였습니다.")
                        .data(ResAuthPostRefreshDTOApiV1.of(accessJwt, refreshJwt))
                        .build(),
                HttpStatus.OK
        );
    }

}
