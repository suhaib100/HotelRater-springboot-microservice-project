package com.icwd.gateway.ApiGateway.models;

import lombok.*;

import java.util.Collection;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor 
public class AuthResponse {

    private String userId;
    private String accessToken;
    private String refreshToken;
    private Long expireAt;
    private Collection<String> authorities;
}
