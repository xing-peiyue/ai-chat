package com.chat.system.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.chat.common.core.domain.entity.SysDept;
import com.chat.common.core.domain.entity.SysUser;
import com.chat.common.core.domain.model.LoginUser;
import com.chat.system.domain.SysUserOnline;
import com.chat.system.service.ISysDeptService;
import com.chat.system.service.ISysUserOnlineService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * 在线用户 服务层处理
 *
 * @author chat
 */
@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService {
    @Resource
    private ISysDeptService deptService;

    /**
     * 通过登录地址查询信息
     *
     * @param ipaddr 登录地址
     * @param user   用户信息
     * @return 在线用户信息
     */
    @Override
    public SysUserOnline selectOnlineByIpaddr(String ipaddr, LoginUser user) {
        if (StrUtil.equals(ipaddr, user.getIpaddr())) {
            return loginUserToUserOnline(user);
        }
        return null;
    }

    /**
     * 通过用户名称查询信息
     *
     * @param userName 用户名称
     * @param user     用户信息
     * @return 在线用户信息
     */
    @Override
    public SysUserOnline selectOnlineByUserName(String userName, LoginUser user) {
        if (StrUtil.equals(userName, user.getUsername())) {
            return loginUserToUserOnline(user);
        }
        return null;
    }

    /**
     * 通过登录地址/用户名称查询信息
     *
     * @param ipaddr   登录地址
     * @param userName 用户名称
     * @param user     用户信息
     * @return 在线用户信息
     */
    @Override
    public SysUserOnline selectOnlineByInfo(String ipaddr, String userName, LoginUser user) {
        if (StrUtil.equals(ipaddr, user.getIpaddr()) && StrUtil.equals(userName, user.getUsername())) {
            return loginUserToUserOnline(user);
        }
        return null;
    }

    /**
     * 设置在线用户信息
     *
     * @param user 用户信息
     * @return 在线用户
     */
    @Override
    public SysUserOnline loginUserToUserOnline(LoginUser user) {
        if (ObjectUtil.isNull(user) || ObjectUtil.isNull(user.getUser())) {
            return null;
        }
        SysUserOnline sysUserOnline = new SysUserOnline();
        sysUserOnline.setTokenId(user.getToken());
        sysUserOnline.setUserName(user.getUsername());
        sysUserOnline.setIpaddr(user.getIpaddr());
        sysUserOnline.setLoginLocation(user.getLoginLocation());
        sysUserOnline.setBrowser(user.getBrowser());
        sysUserOnline.setOs(user.getOs());
        sysUserOnline.setLoginTime(user.getLoginTime());
        SysUser sysUser = user.getUser();
        Long deptId = sysUser.getDeptId();
        SysDept sysDept = deptService.getById(deptId);
        sysUserOnline.setDeptName(sysDept.getDeptName());
        return sysUserOnline;
    }
}
