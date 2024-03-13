package com.xjh.gateway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AuthUserDetails implements UserDetails {

    private String username;
    @JsonIgnore//不需要返回给前端的字段
    private String password;
    private int state;
    private List<GrantedAuthority> permissions;

    //权限
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return permissions;
    }

    //密码
    @Override
    public String getPassword() {
        return password;
    }

    //账号
    @Override
    public String getUsername() {
        return username;
    }

    //是否过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //是否锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //认证是否过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //是否启用
    @Override
    public boolean isEnabled() {
        return state==0;
    }
}
