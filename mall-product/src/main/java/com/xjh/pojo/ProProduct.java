package com.xjh.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
@TableName(value = "pro_product")
public class ProProduct {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 商品名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 单价
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 剩余
     */
    @TableField(value = "surplus")
    private Integer surplus;


    /**
     * 图片地址
     */
    @TableField(value = "img_url")
    private String imgUrl;

    /**
     * 商品描述
     */
    @TableField(value = "description")
    private String description;

    /**
     * 商品详情
     */
    @TableField(value = "details")
    private String details;

    /**
     * 删除状态
     */
    @TableField(value = "del_flag")
    @TableLogic
    private Boolean delFlag;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    private Boolean status;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 标注
     */
    @TableField(value = "remark")
    private String remark;
}