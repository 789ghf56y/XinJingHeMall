package com.xjh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "pro_product_product_classify")
public class ProProductProductClassify {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 商品id
     */
    @TableField(value = "product_id")
    private String productId;

    /**
     * 商品分类id
     */
    @TableField(value = "product_classify_id")
    private Integer productClassifyId;
}