package com.chat.web.controller.system;

import cn.hutool.core.util.StrUtil;
import com.chat.common.annotation.Log;
import com.chat.common.constant.UserConstants;
import com.chat.common.core.controller.BaseController;
import com.chat.common.core.domain.AjaxResult;
import com.chat.common.core.domain.entity.SysUser;
import com.chat.common.core.domain.model.LoginUser;
import com.chat.common.enums.BusinessType;
import com.chat.common.utils.SecurityUtils;
import com.chat.common.utils.StringUtils;
import com.chat.framework.web.service.TokenService;
import com.chat.system.service.ISysDeptService;
import com.chat.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.Resource;

/**
 * 个人信息 业务处理
 *
 * @author chat
 */
@RestController
@RequestMapping("/system/user/profile")
public class SysProfileController extends BaseController {
    @Resource
    private ISysUserService userService;
    @Resource
    private TokenService tokenService;
    @Resource
    private ISysDeptService deptService;

    /**
     * 个人信息
     */
    @GetMapping
    public AjaxResult profile() {
        LoginUser loginUser = getLoginUser();
        SysUser user = loginUser.getUser();
        AjaxResult ajax = AjaxResult.success(user);
        ajax.put("roleGroup", userService.selectUserRoleGroup(loginUser.getUsername()));
        ajax.put("postGroup", userService.selectUserPostGroup(loginUser.getUsername()));
        ajax.put("dept", deptService.getById(user.getDeptId()));
        return ajax;
    }

    /**
     * 修改用户
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateProfile(@RequestBody SysUser user) {
        SysUser dto = new SysUser();
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = loginUser.getUser();

        dto.setNickName(StrUtil.blankToDefault(user.getNickName(), sysUser.getNickName()));
        dto.setEmail(StrUtil.blankToDefault(user.getEmail(), sysUser.getEmail()));
        dto.setSex(StrUtil.blankToDefault(user.getSex(), sysUser.getSex()));
        dto.setPhone(StrUtil.blankToDefault(user.getPhone(), sysUser.getPhone()));
        dto.setCompany(StrUtil.blankToDefault(user.getCompany(), sysUser.getCompany()));
        dto.setId(sysUser.getId());

        if (StrUtil.isNotEmpty(dto.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(dto))) {
            return AjaxResult.error("修改用户'" + dto.getUserName() + "'失败，邮箱账号已存在");
        }

        if (userService.updateById(dto)) {
            // 更新缓存用户信息
            sysUser.setNickName(dto.getNickName());
            sysUser.setEmail(dto.getEmail());
            sysUser.setSex(dto.getSex());
            sysUser.setCompany(dto.getCompany());
            sysUser.setPhone(dto.getPhone());
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改个人信息异常，请联系管理员");
    }

    /**
     * 重置密码
     */
    @Log(title = "个人信息", businessType = BusinessType.UPDATE)
    @PutMapping("/updatePwd")
    public AjaxResult updatePwd(String newPassword) {
        LoginUser loginUser = getLoginUser();
        String userId = loginUser.getUserId();
        if (userService.resetUserPwd(userId, SecurityUtils.encryptPassword(newPassword))) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码异常，请联系管理员");
    }

    /**
     * 头像上传
     */
    @Log(title = "用户头像", businessType = BusinessType.UPDATE)
    @PostMapping("/avatar")
    public AjaxResult avatar(@RequestParam("imgUrl") String imgUrl) {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = loginUser.getUser();

        SysUser build = SysUser.builder().id(sysUser.getId()).avatar(imgUrl).build();
        if (userService.updateById(build)) {
            sysUser.setAvatar(imgUrl);
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
}
