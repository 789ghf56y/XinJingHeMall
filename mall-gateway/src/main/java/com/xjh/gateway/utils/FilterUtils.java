package com.xjh.gateway.utils;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjh.model.R;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.AntPathMatcher;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class FilterUtils {

    public static Mono<Void> getRefuseResult(ServerHttpResponse response, R result, HttpStatus httpStatus) {
        ObjectMapper mapper = new ObjectMapper();
        String resultStr = "";
        try {
            resultStr = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        byte[] bits = resultStr.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bits);
        response.setStatusCode(httpStatus);
        response.getHeaders().set("Content-Type", "application/json;charset=UTF-8");
        return response.writeWith(Mono.just(buffer));
    }

    public static Mono<Void> getRefuseResult(ServerHttpResponse response, R result) {
        return getRefuseResult(response, result, HttpStatus.OK);
    }

    public static boolean isPathMatch(String[] excludePathPatterns, ServerHttpRequest request) {
        if (excludePathPatterns != null && excludePathPatterns.length > 0) {
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (String excludePathPattern : excludePathPatterns) {
                excludePathPattern = excludePathPattern.replace(" ", "").toLowerCase();
                excludePathPattern = excludePathPattern.startsWith("/") ? excludePathPattern : "/" + excludePathPattern;
                String url = request.getURI().getPath().toLowerCase();
                if (antPathMatcher.match(excludePathPattern, url)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getToken(ServerHttpRequest request) {
        // token,authorization,TOKEN,AUTHORIZATION,Authorization
        String token = request.getHeaders().getFirst("Authorization");
        token = StringUtils.isBlank(token) ? token : token.replace("Bearer ", "");
        return token;
    }


}
