package com.xjh.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class MenuVo {
    private Integer id;
    private Integer type;
    private String code;
    private String name;
    private Integer parentId;
    private String path;
    private String redirect;
    private String component;
    private Object meta;
    private List<MenuVo> children;

}
