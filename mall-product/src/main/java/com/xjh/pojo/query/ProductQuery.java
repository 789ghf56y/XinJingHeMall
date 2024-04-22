package com.xjh.pojo.query;


import lombok.Data;

@Data
public class ProductQuery {
    private Long pageNum;
    private Long pageSize;

    private String name;
    private String productClassify;
    private String createTimeStart;
    private String createTimeEnd;
}
