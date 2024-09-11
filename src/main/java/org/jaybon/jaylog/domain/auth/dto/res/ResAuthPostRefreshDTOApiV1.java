package org.jaybon.jaylog.domain.auth.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResAuthPostRefreshDTOApiV1 {

    private String accessJwt;
    private String refreshJwt;

    public static ResAuthPostRefreshDTOApiV1 of(String accessJwt, String refreshJwt) {
        return ResAuthPostRefreshDTOApiV1.builder()
                .accessJwt(accessJwt)
                .refreshJwt(refreshJwt)
                .build();
    }

}
