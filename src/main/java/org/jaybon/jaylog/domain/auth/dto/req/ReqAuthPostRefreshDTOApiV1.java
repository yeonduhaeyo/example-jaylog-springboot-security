package org.jaybon.jaylog.domain.auth.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReqAuthPostRefreshDTOApiV1 {

    @NotBlank(message = "리프레시 토큰이 없습니다.")
    private String refreshJwt;

}
