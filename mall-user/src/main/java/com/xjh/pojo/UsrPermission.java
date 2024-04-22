package com.xjh.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "usr_permission")
public class UsrPermission {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    @TableField(value = "parent_id")
    private Integer parentId;
    /**
     * 权限类型。1：目录；2：菜单；3：功能；
     */
    @TableField(value = "`type`")
    private Integer type;

    /**
     * 权限标识，前端使用
     */
    @TableField(value = "code")
    private String code;

    /**
     * 路由名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 路由地址
     */
    @TableField(value = "path")
    private String path;

    @TableField(value = "redirect")
    private String redirect;

    @TableField(value = "component")
    private String component;

    @TableField(value = "meta")
    private String meta;

    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "update_time")
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