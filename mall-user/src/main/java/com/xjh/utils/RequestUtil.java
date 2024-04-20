package com.xjh.utils;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.xjh.pojo.dto.CurrentUserDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

//@Component
public class RequestUtil {
    private static HttpServletRequest request;

    @Autowired(required = false)
    public void setRequest(HttpServletRequest request) {
        RequestUtil.request = request;
    }

    /**
     * 获取token，登录后可用
     *
     * @return
     */
    public static String getToken() {
//        return request.getHeader(KeyEnum.AUTHORIZATION.getValue());
        String token = request.getHeader("Authorization");
        token = StringUtils.isBlank(token) ? token : token.replace("Bearer ", "");
        return token;
    }

    /**
     * 获取当前登录用户信息，登录后可用
     *
     * @return
     */
    public static CurrentUserDto getCurrentUser() {
        CurrentUserDto currentUser;
        if (request.getAttribute("user") == null) {
            currentUser = (CurrentUserDto)JwtUtil.parseJwt(getToken());
            request.setAttribute("user", currentUser);
        } else {
            currentUser = (CurrentUserDto) request.getAttribute("user");
        }
        return currentUser;
    }


}
