package com.xjh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjh.mapper.UsrPermissionMapper;
import com.xjh.mapper.UsrRoleMapper;
import com.xjh.model.R;
import com.xjh.pojo.UsrPermission;
import com.xjh.pojo.UsrRole;
import com.xjh.pojo.dto.CurrentUserDto;
import com.xjh.pojo.vo.LoginUserRoleVo;
import com.xjh.pojo.vo.LoginUserVo;
import com.xjh.pojo.vo.MenuVo;
import com.xjh.status.StatusCode;
import com.xjh.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xjh.pojo.UsrUser;
import com.xjh.mapper.UsrUserMapper;
import com.xjh.service.UsrUserService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UsrUserServiceImpl extends ServiceImpl<UsrUserMapper, UsrUser> implements UsrUserService{
    @Resource
    private UsrUserMapper userMapper;
    @Resource
    private UsrRoleMapper roleMapper;
    @Resource
    private UsrPermissionMapper permissionMapper;


    @Override
    public R login(String username, String password,String userType) {

        List<UsrUser> userList = userMapper.selectList(new LambdaQueryWrapper<UsrUser>()
                .eq(StringUtils.hasLength(username), UsrUser::getUsername, username)
                .eq(StringUtils.hasLength(userType),UsrUser::getUserType,userType));
        if (CollectionUtils.isEmpty(userList)) {
            return R.fail(StatusCode.USER_LOGIN, "登录失败，未找到账号信息");
        }
        if (userList.size() > 1) {
            return R.fail(StatusCode.USER_LOGIN,  "登录失败，找到多个账号");
        }
        UsrUser userDO = userList.get(0);
        if (!password.equals(userDO.getPassword())) {
            return R.fail(StatusCode.USER_LOGIN, "登录失败：密码错误");
        }
        if ("0".equals(userDO.getDelFlag())) {
            return R.fail(StatusCode.USER_LOGIN, "登录失败，用户已删除");
        }
        if ("1".equals(userDO.getStatus())) {
            return R.fail(StatusCode.USER_LOGIN, "登录失败，用户已禁用");
        }
//        查询用户角色，目前用户只用有一个角色，后续可以变成一个用户拥有多个角色，权限取并集
        List<UsrRole> roleList = roleMapper.selectByUserId(userDO.getUserId());
        if(roleList.size() > 1){
            return R.fail(StatusCode.USER_LOGIN,"登录失败吗，有多个角色");
        }
        UsrRole usrRole = roleList.get(0);
        CurrentUserDto currentUserDto = new CurrentUserDto();
        currentUserDto.setUserId(userDO.getUserId());
        currentUserDto.setUsername(userDO.getUsername());
//        查询该用户角色的权限(权限目前是只有菜单权限)
        List<UsrPermission> permissions = permissionMapper.selectByRoleId(usrRole.getRoleId());
        List<String> permissionUrlList = permissions.stream().map(item -> item.getUrl()).collect(Collectors.toList());
        currentUserDto.setUrlPatternList(permissionUrlList);
//          生成token
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("user-info",currentUserDto);
        String token = JwtUtil.createJwt(userMap);
//      返回用户信息Vo（包括用户一些信息和角色信息）
        LoginUserVo loginUserVo = new LoginUserVo();
        BeanUtils.copyProperties(userDO,loginUserVo);
        LoginUserRoleVo userRoleVo = new LoginUserRoleVo();
        BeanUtils.copyProperties(usrRole,userRoleVo);
        loginUserVo.setRole(userRoleVo);

//        返回菜单
        List<MenuVo> menuVoList = permissions.stream().map(permission->{
            ObjectMapper objectMapper = new ObjectMapper();
            Object meta;
            try {
                meta = objectMapper.readValue(permission.getMeta(),Object.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            MenuVo menuVo = new MenuVo();
            BeanUtils.copyProperties(permission,menuVo);
            menuVo.setMeta(meta);
            return menuVo;
        }).collect(Collectors.toList());
        menuVoList = buildTree(menuVoList);
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("token",token);
        resultMap.put("user-info",loginUserVo);
        resultMap.put("menuList",menuVoList);
        return R.ok(resultMap);
    }

//    构建树形菜单
    public List<MenuVo> buildTree(List<MenuVo> menuVoList){
        List<MenuVo> resultMenuVoList = new ArrayList<>();
        for(MenuVo rootMenuVo : getRootMenu(menuVoList)){
            rootMenuVo = buildChildTree(rootMenuVo,menuVoList);
            resultMenuVoList.add(rootMenuVo);
        }
        return resultMenuVoList;
    }

//    构建根菜单的子节点
    public MenuVo buildChildTree(MenuVo rootMenuVo,List<MenuVo> menuVoList){
        List<MenuVo> childMenuVoList = new ArrayList<>();
        for(MenuVo menuVo : menuVoList){
            if(menuVo.getParentId().equals(rootMenuVo.getId())){
                childMenuVoList.add(buildChildTree(menuVo,menuVoList));
            }
        }
        rootMenuVo.setChildren(childMenuVoList);
        return rootMenuVo;
    }

//  获取根菜单
    public List<MenuVo> getRootMenu(List<MenuVo> menuVoList){
        List<MenuVo> rootMenuVoList = new ArrayList<>();
        for(MenuVo menuVo : menuVoList){
            if(menuVo.getParentId() == 0){
                rootMenuVoList.add(menuVo);
            }
        }
        return rootMenuVoList;
    }

}
