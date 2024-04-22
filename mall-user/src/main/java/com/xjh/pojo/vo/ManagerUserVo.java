package com.xjh.pojo.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.xjh.pojo.UsrPermission;
import lombok.Data;

import java.util.List;

@Data
public class ManagerUserVo {
    private String userId;
    private String username;
    private String password;
    private String phone;
    private Boolean status;
    private String roleKey;
    private List<UsrPermission> permissions;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
 }
