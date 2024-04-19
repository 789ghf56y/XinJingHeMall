package com.xjh.gateway.filter;

import com.xjh.gateway.utils.FilterUtils;
import com.xjh.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;


@Component
public class AuthorizationFilter implements GlobalFilter, Ordered {

    @Value("${xjh.url.white}")
    private String[] excludePathPatterns;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        if ("OPTIONS".equals(request.getMethodValue())) {
            return chain.filter(exchange);
        }
        // 1、放行不需要拦截的请求
        if (FilterUtils.isPathMatch(excludePathPatterns, request)) {
            return chain.filter(exchange);
        }
        String token = FilterUtils.getToken(request);
        Claims claims = JwtUtil.parseJwt(token);
        LinkedHashMap userMap = (LinkedHashMap) claims.get("user-info");
//        userMap.get("permissions")


        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 20;
    }
}
