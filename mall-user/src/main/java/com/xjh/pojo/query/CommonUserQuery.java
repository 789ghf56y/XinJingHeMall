package com.xjh.pojo.query;


import lombok.Data;

@Data
public class CommonUserQuery {
    private Long pageNum;
    private Long pageSize;
    private String username;
    private String pickName;
    private String email;
    private String phone;
    private String address;
    private String createTimeStart;
    private String createTimeEnd;
    private String userType;
}
