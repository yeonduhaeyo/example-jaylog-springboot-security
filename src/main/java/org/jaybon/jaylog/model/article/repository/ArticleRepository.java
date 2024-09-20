package org.jaybon.jaylog.model.article.repository;

import org.jaybon.jaylog.model.article.entity.ArticleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {

    Optional<ArticleEntity> findByIdAndDeleteDateIsNull(Long id);

    Page<ArticleEntity> findByDeleteDateIsNull(Pageable pageable);

    Page<ArticleEntity> findByTitleContainingOrContentContainingAndDeleteDateIsNull(String title, String content, Pageable pageable);

}
