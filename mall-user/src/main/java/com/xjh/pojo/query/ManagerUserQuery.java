package com.xjh.pojo.query;

import lombok.Data;

@Data
public class ManagerUserQuery {
    private Long pageNum;
    private Long pageSize;
    private String username;
    private String phone;

    private String createTimeStart;
    private String createTimeEnd;
    private String userType;
}
