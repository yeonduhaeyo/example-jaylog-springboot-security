package org.jaybon.jaylog.domain.article.dto.req;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jaybon.jaylog.model.article.entity.ArticleEntity;
import org.jaybon.jaylog.util.UtilFunction;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqArticlePutDTOApiV1 {

    @Valid
    @NotNull(message = "게시글 정보를 입력해주세요.")
    private Article article;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Article {

        @NotBlank(message = "제목을 입력해주세요.")
        private String title;

        @NotBlank(message = "내용을 입력해주세요.")
        private String content;

        public void update(ArticleEntity articleEntity) {
            articleEntity.setTitle(title);
            articleEntity.setThumbnail(UtilFunction.getFirstUrlFromMarkdown(content));
            articleEntity.setContent(content);
        }

    }

}
