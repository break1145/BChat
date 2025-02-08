package com.bchat.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JWTAuthVO {
    private String accessToken;
    private String tokenType = "Bearer";
    public JWTAuthVO(String accessToken) {
        this.accessToken = accessToken;
        this.tokenType = "Bearer";
    }
}
