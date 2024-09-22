package org.jaybon.jaylog.config.security.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jaybon.jaylog.model.user.constraint.LoginType;
import org.jaybon.jaylog.model.user.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
public class CustomUserDetails implements UserDetails {

    private User user;

    public static CustomUserDetails of(UserEntity userEntity) {
        return CustomUserDetails.builder()
                .user(User.fromEntity(userEntity))
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    public static class User {
        private Long id;
        private String username;
        private String password;
        private String simpleDescription;
        private String profileImage;
        private LoginType loginType;
        private Long jwtValidator;
        private List<String> roleList;

        public static User fromEntity(UserEntity userEntity) {
            return User.builder()
                    .id(userEntity.getId())
                    .username(userEntity.getUsername())
                    .password(userEntity.getPassword())
                    .simpleDescription(userEntity.getSimpleDescription())
                    .profileImage(userEntity.getProfileImage())
                    .loginType(userEntity.getLoginType())
                    .jwtValidator(userEntity.getJwtValidator())
                    .roleList(
                            userEntity.getUserRoleEntityList()
                                    .stream()
                                    .map(userRoleEntity -> userRoleEntity.getRole().toString())
                                    .toList())
                    .build();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoleList()
                .stream()
                .map(role -> (GrantedAuthority) () -> "ROLE_" + role)
                .toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
