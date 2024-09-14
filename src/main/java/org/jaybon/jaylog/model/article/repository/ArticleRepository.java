package org.jaybon.jaylog.model.article.repository;

import org.jaybon.jaylog.model.article.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findByIdAndDeleteDateIsNull(Long id);

}
