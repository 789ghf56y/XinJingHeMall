package com.xjh.controller;


import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import com.xjh.mapper.UsrPermissionMapper;
import com.xjh.mapper.UsrRoleMapper;
import com.xjh.model.PageResult;
import com.xjh.model.R;
import com.xjh.pojo.UsrLoginUser;
import com.xjh.pojo.UsrRole;
import com.xjh.pojo.UsrUser;
import com.xjh.pojo.query.CommonUserQuery;
import com.xjh.pojo.query.ManagerUserQuery;
import com.xjh.pojo.vo.CaptchaVo;
import com.xjh.pojo.vo.CommonUserVo;
import com.xjh.pojo.vo.ManagerUserVo;
import com.xjh.service.UsrPermissionService;
import com.xjh.service.UsrUserService;
import com.xjh.status.StatusCode;
import com.xjh.utils.RedisUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private UsrUserService userService;

    @Resource
    private UsrRoleMapper roleMapper;

    @PostMapping("/login")
    public R login(@RequestBody UsrLoginUser loginUser){
        // 校验图形验证码
        String redisCode = (String) redisUtil.get("captcha:graph" + ":" + loginUser.getCodeSessionId());
        if(StringUtils.isEmpty(redisCode) || !redisCode.equals(loginUser.getCaptchaCode())) {
            return R.fail(StatusCode.USER_PARAMETER.getCode(), "验证码错误！");
        }
        R result = userService.login(loginUser.getUsername(),loginUser.getPassword(),loginUser.getUserType());
        return result;
    }


    @GetMapping("/captcha")
    public R graphCaptcha(){
        SpecCaptcha specCaptcha = new SpecCaptcha(100, 35, 4, new Font("Arial", 1, 32));
        String codeSessionId = UUID.randomUUID().toString().replaceAll("-","");
        specCaptcha.setCharType(Captcha.TYPE_ONLY_NUMBER);
        redisUtil.set("captcha:graph"+":"+codeSessionId,specCaptcha.text(),300L);
        CaptchaVo captchaVo = new CaptchaVo();
        captchaVo.setCaptchaBase64(specCaptcha.toBase64());
        captchaVo.setCodeSessionId(codeSessionId);
        return R.ok(captchaVo);
    }


    @PostMapping("/refreshUserInfoByToken")
    public R refreshUserInfoByToken(HttpServletRequest request) {
        R result = userService.refreshUserInfoByToken(request);
        return result;
    }


    @GetMapping("/getCommonUserList")
    public R getCommonUserList(CommonUserQuery commonUserQuery){
        LambdaQueryWrapper<UsrUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UsrUser::getUserType, commonUserQuery.getUserType())
                .like(StringUtils.isNotBlank(commonUserQuery.getUsername()),UsrUser::getUsername,commonUserQuery.getUsername())
                .like(StringUtils.isNotBlank(commonUserQuery.getUsername()),UsrUser::getUsername,commonUserQuery.getUsername())
                .like(StringUtils.isNotBlank(commonUserQuery.getPickName()),UsrUser::getPickName,commonUserQuery.getPickName())
                .eq(StringUtils.isNotBlank(commonUserQuery.getPhone()),UsrUser::getPhone,commonUserQuery.getPhone())
                .eq(StringUtils.isNotBlank(commonUserQuery.getEmail()),UsrUser::getEmail,commonUserQuery.getEmail())
                .like(StringUtils.isNotBlank(commonUserQuery.getAddress()),UsrUser::getAddress,commonUserQuery.getAddress())
                .between(StringUtils.isNotBlank(commonUserQuery.getCreateTimeStart()) && StringUtils.isNotBlank(commonUserQuery.getCreateTimeStart()),UsrUser::getCreateTime,commonUserQuery.getCreateTimeStart(),commonUserQuery.getCreateTimeEnd());
        Page<UsrUser> usrUserPage = userService.page(new Page<>(commonUserQuery.getPageNum(), commonUserQuery.getPageSize()), queryWrapper);
        List<UsrUser> usrUsers = usrUserPage.getRecords();
        List<CommonUserVo> commonUserVoList = usrUsers.stream().map(usrUser -> {
            CommonUserVo commonUserVo = new CommonUserVo();
            BeanUtils.copyProperties(usrUser, commonUserVo);
            return commonUserVo;
        }).collect(Collectors.toList());
        PageResult<CommonUserVo> pageResult = new PageResult<>();
        pageResult.setList(commonUserVoList);
        pageResult.setTotal(usrUserPage.getTotal());
        pageResult.setPageNum(usrUserPage.getCurrent());
        pageResult.setPageSize(usrUserPage.getSize());
        return R.ok(pageResult);
    }


    @GetMapping("/getManagerUserList")
    public R getManagerUserList(ManagerUserQuery managerUserQuery){
        LambdaQueryWrapper<UsrUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UsrUser::getUserType, managerUserQuery.getUserType())
                .like(StringUtils.isNotBlank(managerUserQuery.getUsername()),UsrUser::getUsername,managerUserQuery.getUsername())
                .eq(StringUtils.isNotBlank(managerUserQuery.getPhone()),UsrUser::getPhone,managerUserQuery.getPhone())
                .between(StringUtils.isNotBlank(managerUserQuery.getCreateTimeStart()) && StringUtils.isNotBlank(managerUserQuery.getCreateTimeStart()),UsrUser::getCreateTime,managerUserQuery.getCreateTimeStart(),managerUserQuery.getCreateTimeEnd());
        Page<UsrUser> usrUserPage = userService.page(new Page<>(managerUserQuery.getPageNum(), managerUserQuery.getPageSize()), queryWrapper);
        List<UsrUser> usrUsers = usrUserPage.getRecords();
        List<ManagerUserVo> managerUserVoList = usrUsers.stream().map(usrUser -> {
            ManagerUserVo managerUserVo = new ManagerUserVo();
            BeanUtils.copyProperties(usrUser, managerUserVo);
            List<UsrRole> roles = roleMapper.selectByUserId(usrUser.getUserId());
            managerUserVo.setRoleKey(roles.get(0).getRoleKey());
            //TODO 查询权限
            return managerUserVo;
        }).collect(Collectors.toList());
        PageResult<ManagerUserVo> pageResult = new PageResult<>();
        pageResult.setList(managerUserVoList);
        pageResult.setTotal(usrUserPage.getTotal());
        pageResult.setPageNum(usrUserPage.getCurrent());
        pageResult.setPageSize(usrUserPage.getSize());
        return R.ok(pageResult);
    }


    @PutMapping("/updateStatus")
    public R updateUserStatus(@RequestBody CommonUserVo commonUserVo){
        UsrUser usrUser = new UsrUser();
        usrUser.setStatus(commonUserVo.getStatus());
        boolean flag = userService.update(usrUser,new LambdaUpdateWrapper<UsrUser>()
                .eq(StringUtils.isNotBlank(commonUserVo.getUserId()),UsrUser::getUserId,commonUserVo.getUserId()));
        return R.ok(flag);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public R deleteUserById(@PathVariable String userId){
        userService.removeById(userId);
        return R.ok();
    }


    @DeleteMapping("/batchDeleteUser")
    public R batchDeleteUser(@RequestBody List<String> ids){
        userService.removeByIds(ids);
        return R.ok();
    }


    @PutMapping("/updateCommonUser")
    public R updateCommonUser(@RequestBody CommonUserVo commonUserVo){
        UsrUser usrUser = new UsrUser();
        BeanUtils.copyProperties(commonUserVo,usrUser);
        LambdaUpdateWrapper<UsrUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UsrUser::getUserId,commonUserVo.getUserId());
        userService.update(usrUser,updateWrapper);
        return R.ok();
    }



    @PostMapping("/saveCommonUser")
    public R saveCommonUser(@RequestBody CommonUserVo commonUserVo){
        UsrUser usrUser = new UsrUser();
        BeanUtils.copyProperties(commonUserVo,usrUser);
        userService.save(usrUser);
        return R.ok();
    }


}
