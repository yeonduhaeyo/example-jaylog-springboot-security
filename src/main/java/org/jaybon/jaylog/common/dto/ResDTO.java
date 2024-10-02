package org.jaybon.jaylog.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ResDTO<T> {

    // 공통 최상위 DTO

    private Integer code;
    private String message;
    private T data;
}
