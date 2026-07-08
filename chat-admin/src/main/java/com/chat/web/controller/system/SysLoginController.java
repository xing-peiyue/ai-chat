package com.chat.web.controller.system;

import java.util.List;
import java.util.Set;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.chat.common.core.domain.entity.SysDept;
import com.chat.system.domain.dto.user.SysUserDetailDTO;
import com.chat.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.chat.common.constant.Constants;
import com.chat.common.core.domain.AjaxResult;
import com.chat.common.core.domain.entity.SysMenu;
import com.chat.common.core.domain.entity.SysUser;
import com.chat.common.core.domain.model.LoginBody;
import com.chat.common.utils.SecurityUtils;
import com.chat.framework.web.service.SysLoginService;
import com.chat.framework.web.service.SysPermissionService;
import com.chat.system.service.ISysMenuService;

import jakarta.annotation.Resource;

/**
 * 登录验证
 *
 * @author chat
 */
@RestController
public class SysLoginController {
    @Resource
    private SysLoginService loginService;
    @Resource
    private ISysMenuService menuService;
    @Resource
    private SysPermissionService permissionService;


    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        if (StrUtil.equals(loginBody.getType(), "1")) {
            String token = loginService.mobileLogin(loginBody.getPhone(), loginBody.getPhoneCode(), loginBody.getPt());
            ajax.put(Constants.TOKEN, token);
        } else {
            String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                    loginBody.getUuid(), loginBody.getPt());
            ajax.put(Constants.TOKEN, token);
        }
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        String userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }
}
