package org.jaybon.jaylog.model.like.repository;

import org.jaybon.jaylog.model.like.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    long countByUserEntity_IdAndArticleEntity_Id(Long userId, Long articleId);

    long countByArticleEntity_Id(Long articleId);

    void deleteByUserEntity_IdAndArticleEntity_Id(Long userId, Long articleId);

}
