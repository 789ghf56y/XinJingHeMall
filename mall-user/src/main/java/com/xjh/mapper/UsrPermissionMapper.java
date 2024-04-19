package com.xjh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjh.pojo.UsrPermission;

import java.util.List;

public interface UsrPermissionMapper extends BaseMapper<UsrPermission> {
    List<UsrPermission> selectByRoleId(Integer roleId);
}