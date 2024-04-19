package com.xjh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xjh.pojo.UsrRole;

import java.util.List;

public interface UsrRoleMapper extends BaseMapper<UsrRole> {
    List<UsrRole> selectByUserId(String userId);
}