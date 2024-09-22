package org.jaybon.jaylog.model.user.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;
import org.jaybon.jaylog.model.article.entity.ArticleEntity;
import org.jaybon.jaylog.model.like.entity.LikeEntity;
import org.jaybon.jaylog.model.user.constraint.LoginType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "`USER`")
@DynamicInsert
@DynamicUpdate
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "simple_description")
    private String simpleDescription;

    @Column(name = "profile_image", length = 1024 * 1024 * 10, nullable = false)
    @ColumnDefault("'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png'")
    private String profileImage;

    @Column(name = "login_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    @Column(name = "jwt_validator", nullable = false)
    private Long jwtValidator;

    @Column(name = "create_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<UserRoleEntity> userRoleEntityList;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    @SQLRestriction("delete_date is null")
    private List<ArticleEntity> articleEntityList;

    @OneToMany(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private List<LikeEntity> likeEntityList;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSimpleDescription(String simpleDescription) {
        this.simpleDescription = simpleDescription;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setJwtValidator(Long jwtValidator) {
        this.jwtValidator = jwtValidator;
    }
}
