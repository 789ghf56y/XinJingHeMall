package com.xjh.gateway.handler;

import com.xjh.gateway.config.JwtProperties;
import com.xjh.gateway.entity.AuthUserDetails;
import com.xjh.model.R;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class AuthenticationSuccessHandler extends WebFilterChainServerAuthenticationSuccessHandler {
    public final JwtProperties jwtProperties;

    public AuthenticationSuccessHandler(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        HttpHeaders headers = response.getHeaders();
        headers.add("Content-Type","application/json;charset=UTF-8");
        headers.add("Cache-control","no-store,no-cache,must-revalidate,max-age-8");
        R<AuthUserDetails> result = R.ok();

    }
}
