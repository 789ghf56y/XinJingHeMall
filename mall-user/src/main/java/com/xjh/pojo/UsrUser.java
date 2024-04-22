package com.xjh.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName(value = "usr_user")
public class UsrUser {
    /**
     * id
     */
    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private String userId;

    /**
     * 账号
     */
    @TableField(value = "username")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;

    /**
     * 昵称
     */
    @TableField(value = "pick_name")
    private String pickName;

    /**
     * 头像
     */
    @TableField(value = "avatar")
    private String avatar;

    /**
     * 积分
     */
    @TableField(value = "integral")
    private Double integral;

    /**
     * 性别
     */
    @TableField(value = "sex")
    private String sex;

    /**
     * 年龄
     */
    @TableField(value = "age")
    private Integer age;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;

    /**
     * 地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 用户描述
     */
    @TableField(value = "user_desc")
    private String userDesc;

//    用户类型
    @TableField(value = "user_type")
    private String userType;


    /**
     * 账号状态（0禁用 1启用）
     */
    @TableField(value = "`status`")
    private Boolean status;

    /**
     * 删除标志（0未删除 1已删除）
     */
    @TableField(value = "del_flag")
    @TableLogic
    private Boolean delFlag;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 修改者
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 标准
     */
    @TableField(value = "remark")
    private String remark;
}