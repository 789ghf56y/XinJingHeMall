package com.xjh.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjh.pojo.UsrPermission;
import com.xjh.mapper.UsrPermissionMapper;
import com.xjh.service.UsrPermissionService;
@Service
public class UsrPermissionServiceImpl extends ServiceImpl<UsrPermissionMapper, UsrPermission> implements UsrPermissionService{

}
