package com.xjh.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class CurrentUserDto {
    private String userId;
    private String username;
    private String roleType;
    private Integer currentRoleId;
    private List<String> urlPatternList;

}
