package com.xjh.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ProductClassifyVo {
    private Integer id;
    private String typeName;
    private Boolean status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
}
