//package org.jaybon.jaylog.domain.main.dto.res;
//
//
//import kr.co.nomadlab.workplatform.model.npost.constraint.NpostScrapStatusType;
//import kr.co.nomadlab.workplatform.model.npost.entity.NpostScrapEntity;
//import lombok.*;
//import org.jaybon.jaylog.model.article.entity.ArticleEntity;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class ResMainGetDTOApiV1 {
//
//    private ArticlePage articlePage;
//
//    public static ResMainGetDTOApiV1 of(Page<ArticleEntity> articleEntityPage) {
//        return ResMainGetDTOApiV1.builder()
//                .articlePage(new ArticlePage(articleEntityPage))
//                .build();
//    }
//
//    @Getter
//    @ToString
//    public static class ArticlePage extends PageImpl<ArticlePage.Article> {
//
//        public ArticlePage(Page<NpostScrapEntity> npostScrapEntityPage) {
//            super(
//                    NpostScrap.fromEntityList(npostScrapEntityPage.getContent()),
//                    npostScrapEntityPage.getPageable(),
//                    npostScrapEntityPage.getTotalElements()
//            );
//        }
//
//        public ArticlePage(List<NpostScrapEntity> npostScrapEntityList) {
//            super(NpostScrap.fromEntityList(npostScrapEntityList));
//        }
//
//        @Data
//        @Builder
//        @NoArgsConstructor
//        @AllArgsConstructor
//        public static class Article {
//
//            private Long id;
//            private String url;
//            private String memo;
//            private NpostScrapStatusType status;
//            private String description;
//            private Integer requestCount;
//            private Integer startCount;
//            private Integer workCount;
//            private Integer endCount;
//            private LocalDateTime startDate;
//            private LocalDateTime endDate;
//            private LocalDateTime createDate;
//
//            public static List<NpostScrap> fromEntityList(List<NpostScrapEntity> npostScrapEntityList) {
//                return npostScrapEntityList
//                        .stream()
//                        .map(NpostScrap::fromEntity)
//                        .toList();
//            }
//
//            public static NpostScrap fromEntity(NpostScrapEntity npostScrapEntity) {
//                return NpostScrap.builder()
//                        .id(npostScrapEntity.getId())
//                        .url(npostScrapEntity.getUrl())
//                        .memo(npostScrapEntity.getMemo())
//                        .status(npostScrapEntity.getStatus())
//                        .description(npostScrapEntity.getDescription())
//                        .requestCount(npostScrapEntity.getRequestCount())
//                        .startCount(npostScrapEntity.getStartCount())
//                        .workCount(npostScrapEntity.getWorkCount())
//                        .endCount(npostScrapEntity.getEndCount())
//                        .startDate(npostScrapEntity.getStartDate())
//                        .endDate(npostScrapEntity.getEndDate())
//                        .createDate(npostScrapEntity.getCreateDate())
//                        .build();
//            }
//
//        }
//
//    }
//
//}
