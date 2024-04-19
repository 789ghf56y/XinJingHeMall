package com.xjh.gateway.filter;

import com.xjh.gateway.utils.FilterUtils;
import com.xjh.model.R;
import com.xjh.status.StatusCode;
import com.xjh.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticateFilter implements GlobalFilter, Ordered {
    @Value("${xjh.url.white}")
    private String[] excludePathPatterns;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 1、放行不需要拦截的请求
        if (FilterUtils.isPathMatch(excludePathPatterns, request)) {
            return chain.filter(exchange);
        }
        // 2、校验token的合法性
        String token = FilterUtils.getToken(request);
        if(JwtUtil.parseJwt(token)==null){
            return FilterUtils.getRefuseResult(response,
                                                R.fail(StatusCode.USER_UNAUTHORIZED.getCode(), StatusCode.USER_UNAUTHORIZED.getDescription(), "TOKEN无效，请重新登录"),
                                                HttpStatus.UNAUTHORIZED);
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 10;
    }
}
