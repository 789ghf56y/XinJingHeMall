package com.xjh.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "usr_role")
public class UsrRole {
    /**
     * id
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @TableField(value = "role_key")
    private String roleKey;

    /**
     * 显示顺序
     */
    @TableField(value = "role_sort")
    private String roleSort;

    /**
     * 删除标志（0未删除 1已删除）
     */
    @TableField(value = "del_flag")
    @TableLogic
    private Boolean delFlag;

    /**
     * 角色状态（0禁用 1启用）
     */
    @TableField(value = "`status`")
    private Boolean status;

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