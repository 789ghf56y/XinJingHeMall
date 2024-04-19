package com.xjh.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjh.pojo.UsrRole;
import com.xjh.mapper.UsrRoleMapper;
import com.xjh.service.UsrRoleService;
@Service
public class UsrRoleServiceImpl extends ServiceImpl<UsrRoleMapper, UsrRole> implements UsrRoleService{

}
