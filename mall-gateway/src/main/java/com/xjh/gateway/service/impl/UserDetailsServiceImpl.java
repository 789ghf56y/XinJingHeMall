package com.xjh.gateway.service.impl;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {
    @Override
    public Mono<UserDetails> findByUsername(String username) {
        //TODO 从数据库查询用户


        return Mono.just(null);
    }
}
