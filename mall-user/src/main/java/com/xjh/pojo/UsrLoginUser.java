package com.xjh.pojo;

import lombok.Data;

@Data
public class UsrLoginUser {
    private String username;

    private String password;

    private String captchaCode;

    private String codeSessionId;

    private String userType;

}
