package org.jaybon.jaylog.model.article.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLRestriction;
import org.jaybon.jaylog.model.like.entity.LikeEntity;
import org.jaybon.jaylog.model.user.constraint.UserLoginType;
import org.jaybon.jaylog.model.user.entity.UserEntity;
import org.jaybon.jaylog.model.user.entity.UserRoleEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ARTICLE")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false, nullable = false)
    private UserEntity userEntity;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "thumbnail", nullable = false)
    private String thumbnail;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "summary", nullable = false)
    private String summary;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "delete_date")
    private LocalDateTime deleteDate;

    @OneToMany(mappedBy = "articleEntity", fetch = FetchType.LAZY)
    private List<LikeEntity> likeEntityList;

}
