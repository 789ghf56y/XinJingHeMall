package com.xjh.pojo;

import lombok.Data;

@Data
public class UsrLoginUser {
    private String account;

    private String password;

    private String captchaCode;

    private String codeSessionId;

}
