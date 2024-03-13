package com.xjh.gateway.config;

import com.xjh.gateway.handler.CustomServerAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DelegatingReactiveAuthenticationManager;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    //TODO 从配置文件中拿到不需要认证的路径
    @Value("")
    private String[] excludeAuthPages;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         CustomServerAuthenticationEntryPoint serverAuthenticationEntryPoint,
                                                         JwtProperties jwtProperties,
                                                         ReactiveAuthenticationManager manager){
        http.csrf().disable()
                .httpBasic().disable()
                .authenticationManager(manager)
                .exceptionHandling()
                .authenticationEntryPoint(serverAuthenticationEntryPoint)//自定义未认证Handler
                .accessDeniedHandler((serverWebExchange,exception)->{//无权限访问，拒绝访问处理器返回403
                    serverWebExchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return serverWebExchange.getResponse().writeWith(Mono.just(new DefaultDataBufferFactory().wrap("FORBIDDEN".getBytes())));
                })
                .and()
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .authorizeExchange()
                .pathMatchers(excludeAuthPages).permitAll()//白名单
                .pathMatchers(HttpMethod.OPTIONS).permitAll()//options请求放行
                .anyExchange().authenticated()//其他的需要认证
                .and()
                .formLogin().loginPage("/auth/login")//配置登录路径 //TODO  目前是formLogin模式只有账号密码验证，需要更换成自定义Controller
                .authenticationSuccessHandler(null)//TODO 登录成功处理器
                .authenticationFailureHandler(null)//TODO 登录失败处理器
                .and()
                .logout().logoutUrl("/auth/logout")
                .logoutSuccessHandler(null) //TODO 登出成功处理器
                .and()
                .addFilterAt(null, SecurityWebFiltersOrder.HTTP_BASIC);//TODO token过滤器
        return http.build();
    }



    //密码加密器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    //认证管理器
    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService reactiveUserDetailsService,PasswordEncoder passwordEncoder){
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(reactiveUserDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return new DelegatingReactiveAuthenticationManager();
    }

}
