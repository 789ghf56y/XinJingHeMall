package com.xjh.pojo.vo;


import lombok.Data;

@Data
public class LoginUserVo {
    private String userId;
    private String username;
    private String name;
    private String sex;


    private Integer age;


    private String email;


    private String phone;

    private LoginUserRoleVo role;

}
