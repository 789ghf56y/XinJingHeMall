package com.xjh.controller;


import com.alibaba.nacos.common.utils.MD5Utils;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.xjh.model.R;
import com.xjh.pojo.UsrLoginUser;
import com.xjh.pojo.vo.CaptchaVo;
import com.xjh.service.UsrUserService;
import com.xjh.status.StatusCode;
import com.xjh.utils.RedisUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.awt.*;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UsrUserService userService;

    @PostMapping("/login")
    public R login(@RequestBody UsrLoginUser loginUser){
        // 校验图形验证码
        String redisCode = (String) redisUtil.get("captcha:graph" + ":" + loginUser.getCodeSessionId());
        if(StringUtils.isEmpty(redisCode) || !redisCode.equals(loginUser.getCaptchaCode())) {
            return R.fail(StatusCode.USER_PARAMETER.getCode(), "验证码错误！");
        }
        R result = userService.login(loginUser.getUsername(),loginUser.getPassword(),loginUser.getUserType());
        return result;
    }


    @GetMapping("/captcha")
    public R graphCaptcha(){
        SpecCaptcha specCaptcha = new SpecCaptcha(100, 35, 4, new Font("Arial", 1, 32));
        String codeSessionId = UUID.randomUUID().toString().replaceAll("-","");
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        redisUtil.set("captcha:graph"+":"+codeSessionId,specCaptcha.text(),300L);
        CaptchaVo captchaVo = new CaptchaVo();
        captchaVo.setCaptchaBase64(specCaptcha.toBase64());
        captchaVo.setCodeSessionId(codeSessionId);
        return R.ok(captchaVo);
    }

}
