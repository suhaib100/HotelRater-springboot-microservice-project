package com.icwd.user.service.UserService.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class interceptor implements RequestInterceptor {

//    @Autowired
//    private OAuth2AuthorizedClientManager manager;
//
//
//    @Override
//    public void apply(RequestTemplate requestTemplate) {
//        OAuth2AccessToken.TokenType token = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client")
//                .principal("internal").build()).getAccessToken().getTokenType();
//
//        requestTemplate.header("Authorization","Bearer "+token);
//
//    }

    @Autowired
    private OAuth2AuthorizedClientManager manager;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        OAuth2AccessToken token = manager.authorize(OAuth2AuthorizeRequest.withClientRegistrationId("my-internal-client")
                .principal("internal").build()).getAccessToken();

        if (token != null) {
            requestTemplate.header("Authorization", "Bearer " + token.getTokenValue());
        }
    }
}
