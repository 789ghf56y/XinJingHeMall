package com.xjh.pojo.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class CommonUserVo {
    private String userId;

    /**
     * 账号
     */
    private String username;



    /**
     * 昵称
     */
    private String pickName;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 积分
     */
    private Double integral;

    /**
     * 性别
     */
    private String sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 账号状态（0禁用 1启用）
     */
    private Boolean status;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;




}
