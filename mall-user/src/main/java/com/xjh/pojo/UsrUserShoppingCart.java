package com.xjh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import lombok.Data;

@Data
@TableName(value = "usr_user_shopping_cart")
public class UsrUserShoppingCart {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;

    @TableField(value = "user_id")
    private String userId;

    @TableField(value = "product_id")
    private String productId;

    @TableField(value = "product_name")
    private String productName;

    @TableField(value = "price")
    private BigDecimal price;

    @TableField(value = "num")
    private Integer num;

    @TableField(value = "imag_url")
    private String imagUrl;

    @TableField(value = "description")
    private String description;
}