package com.xjh.pojo.query;

import lombok.Data;

@Data
public class ProductClassifyQuery {
    private Long pageNum;
    private Long pageSize;
    private String typeName;
}
