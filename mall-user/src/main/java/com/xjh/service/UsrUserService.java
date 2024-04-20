package com.xjh.service;

import com.xjh.model.R;
import com.xjh.pojo.UsrUser;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

public interface UsrUserService extends IService<UsrUser>{


    R login(String account, String password,String userType);

    R refreshUserInfoByToken(HttpServletRequest request);
}
