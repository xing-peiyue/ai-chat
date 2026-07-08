package com.chat.web.controller.system;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chat.common.annotation.Log;
import com.chat.common.constant.UserConstants;
import com.chat.common.core.controller.BaseController;
import com.chat.common.core.domain.AjaxResult;
import com.chat.common.core.domain.entity.SysDept;
import com.chat.common.core.domain.entity.SysRole;
import com.chat.common.core.domain.entity.SysUser;
import com.chat.common.core.page.TableDataInfo;
import com.chat.common.enums.BusinessType;
import com.chat.common.enums.UserTypeEnum;
import com.chat.common.utils.SecurityUtils;
import com.chat.common.utils.StringUtils;
import com.chat.system.domain.dto.user.QueryUserDTO;
import com.chat.system.domain.dto.user.SysUserDetailDTO;
import com.chat.system.domain.dto.user.SysUserListDTO;
import com.chat.system.domain.dto.user.SysUserSaveUpdateDTO;
import com.chat.system.service.ISysDeptService;
import com.chat.system.service.ISysPostService;
import com.chat.system.service.ISysRoleService;
import com.chat.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author chat
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {
    @Resource
    private ISysUserService userService;

    @Resource
    private ISysRoleService roleService;

    @Resource
    private ISysDeptService deptService;

    @Resource
    private ISysPostService postService;

    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(QueryUserDTO params) {
        startPage();
        List<SysUserListDTO> list = userService.selectUserList(params);
        return getDataTable(list);
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = {"/", "/{userId}"})
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) String userId) {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        //获取所有角色
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        //获取所有岗位
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId)) {
            SysUserDetailDTO sysUser = userService.selectUserByUserId(userId);
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUserSaveUpdateDTO dto) {
        SysUser user = BeanUtil.copyProperties(dto, SysUser.class);
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StringUtils.isNotEmpty(user.getPhone())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }

        return toAjax(userService.insertUser(dto));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUserSaveUpdateDTO dto) {
        SysUser user = BeanUtil.copyProperties(dto, SysUser.class);
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，登录账号已存在");
        } else if (StrUtil.isNotEmpty(user.getPhone())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        } else if (StrUtil.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user))) {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        return toAjax(userService.updateUser(dto));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable String[] userIds) {
        if (ArrayUtils.contains(userIds, getUserId())) {
            return error("当前用户不能删除");
        }
        return toAjax(userService.deleteUserByUserIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:resetPwd')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUserSaveUpdateDTO dto) {
        SysUser sysUser = BeanUtil.copyProperties(dto, SysUser.class);
        userService.checkUserAllowed(sysUser);
        userService.checkUserDataScope(sysUser.getUserId());
        sysUser.setPassword(SecurityUtils.encryptPassword(dto.getPassword()));
        sysUser.setUpdateId(getUserId());
        sysUser.setUpdateTime(new Date());
        return toAjax(userService.resetPwd(sysUser));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUserSaveUpdateDTO dto) {
        SysUser sysUser = BeanUtil.copyProperties(dto, SysUser.class);
        userService.checkUserAllowed(sysUser);
        userService.checkUserDataScope(sysUser.getUserId());
        sysUser.setUpdateId(getUserId());
        return toAjax(userService.updateUserStatus(sysUser));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") String userId) {
        AjaxResult ajax = AjaxResult.success();
        SysUserDetailDTO user = userService.selectUserByUserId(userId);
        List<SysRole> userRoles = user.getRoles();
        //获取所有角色
        List<SysRole> roles = roleService.selectRoleAll();
        List<Long> roleIds = Optional.ofNullable(userRoles).orElseGet(ArrayList::new).stream().map(SysRole::getRoleId).collect(Collectors.toList());
        roles.stream().filter(r -> roleIds.contains(r.getRoleId())).forEach(r -> r.setFlag(true));
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(String userId, Long[] roleIds) {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        return success();
    }

    /**
     * 获取部门树列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/deptTree")
    public AjaxResult deptTree(SysDept dept) {
        return AjaxResult.success(deptService.selectDeptTreeList(dept));
    }

    /**
     * @description: 查询用户详情
     * @author: xingpeiyue
     * @time: 2023/9/18 21:25
     */
    @GetMapping("/userInfo")
    public AjaxResult userInfo(@RequestParam("userId") String userId) {
        SysUser sysUser = userService.userInfo(userId);
        return AjaxResult.success(sysUser);
    }


    /**
     * @description: 获取部门客服
     * @author: xingpeiyue
     * @time: 2024/1/15 22:43
     */
    @GetMapping("/deptCustomer")
    public AjaxResult deptCustomer() {
        List<SysUser> customers = userService.list(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getDeptId, SecurityUtils.getDeptId())
                .eq(SysUser::getUserType, UserTypeEnum.CUSTOMER.getKey()));
        return AjaxResult.success(customers);
    }

}
