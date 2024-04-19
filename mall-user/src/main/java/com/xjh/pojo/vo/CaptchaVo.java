package com.xjh.pojo.vo;

import lombok.Data;

@Data
public class CaptchaVo {
    private String captchaBase64;
    private String codeSessionId;
}
