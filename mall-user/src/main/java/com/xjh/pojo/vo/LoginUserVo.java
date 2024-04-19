package com.xjh.pojo.vo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.xjh.pojo.UsrRole;
import lombok.Data;

@Data
public class LoginUserVo {
    private String userId;
    private String username;

    private String sex;


    private Integer age;


    private String email;


    private String phone;

    private LoginUserRoleVo role;

}
