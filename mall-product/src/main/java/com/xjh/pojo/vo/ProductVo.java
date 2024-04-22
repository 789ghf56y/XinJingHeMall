package com.xjh.pojo.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.List;

@Data
public class ProductVo {
    private String id;
    private String name;
    private Double price;
    private String imgUrl;
    private Integer surplus;
    private List<String> productClassify;
    private String description;
    private String details;

    private Boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

}
