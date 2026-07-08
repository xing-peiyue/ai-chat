package com.chat.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chat.common.annotation.DataScope;
import com.chat.common.constant.UserConstants;
import com.chat.common.core.domain.entity.SysRole;
import com.chat.common.core.domain.entity.SysUser;
import com.chat.common.exception.ServiceException;
import com.chat.common.utils.SecurityUtils;
import com.chat.common.utils.StringUtils;
import com.chat.common.utils.spring.SpringUtils;
import com.chat.system.domain.SysRoleDept;
import com.chat.system.domain.SysRoleMenu;
import com.chat.system.domain.SysUserRole;
import com.chat.system.domain.dto.role.SysRoleSaveUpdateDTO;
import com.chat.system.mapper.SysRoleDeptMapper;
import com.chat.system.mapper.SysRoleMapper;
import com.chat.system.mapper.SysRoleMenuMapper;
import com.chat.system.mapper.SysUserRoleMapper;
import com.chat.system.service.ISysRoleDeptService;
import com.chat.system.service.ISysRoleMenuService;
import com.chat.system.service.ISysRoleService;
import com.chat.system.service.ISysUserRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 角色 业务层处理
 *
 * @author chat
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {
    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysRoleMenuMapper roleMenuMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysRoleDeptMapper roleDeptMapper;
    @Resource
    private ISysRoleMenuService roleMenuService;
    @Resource
    private ISysUserRoleService userRoleService;
    @Resource
    private ISysRoleDeptService roleDeptService;

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysRole> selectRoleList(SysRole role) {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(String userId) {
        List<SysRole> userRoles = roleMapper.selectRolePermissionByUserId(userId);
        return userRoles;
    }

    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(String userId) {
        List<SysRole> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRole perm : perms) {
            if (StringUtils.isNotNull(perm)) {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRoleAll() {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRole());
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Long> selectRoleListByUserId(Long userId) {
        return roleMapper.selectRoleListByUserId(userId);
    }

    /**
     * 通过角色ID查询角色
     *
     * @param roleId 角色ID
     * @return 角色对象信息
     */
    @Override
    public SysRole selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    /**
     * 校验角色名称是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleNameUnique(SysRole role) {
        Long roleId = ObjectUtil.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole sysRole = this.getOne(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleName, role.getRoleName())
                .last("limit 1"));
        if (ObjectUtil.isNotNull(sysRole) && sysRole.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色权限是否唯一
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public String checkRoleKeyUnique(SysRole role) {
        Long roleId = StringUtils.isNull(role.getRoleId()) ? -1L : role.getRoleId();
        SysRole sysRole = this.getOne(Wrappers.<SysRole>lambdaQuery()
                .eq(SysRole::getRoleKey, role.getRoleKey())
                .last("limit 1"));
        if (ObjectUtil.isNotNull(sysRole) && sysRole.getRoleId().longValue() != roleId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验角色是否允许操作
     *
     * @param role 角色信息
     */
    @Override
    public void checkRoleAllowed(SysRole role) {
        if (ObjectUtil.isNotNull(role.getRoleId()) && role.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员角色");
        }
    }

    /**
     * 校验角色是否有数据权限
     *
     * @param roleId 角色id
     */
    @Override
    public void checkRoleDataScope(Long roleId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysRole role = new SysRole();
            role.setRoleId(roleId);
            List<SysRole> roles = SpringUtils.getAopProxy(this).selectRoleList(role);
            if (StringUtils.isEmpty(roles)) {
                throw new ServiceException("没有权限访问角色数据！");
            }
        }
    }

    /**
     * 通过角色ID查询角色使用数量
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    public long countUserRoleByRoleId(Long roleId) {
        return userRoleService.count(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getRoleId, roleId));
    }

    /**
     * 新增保存角色信息
     *
     * @param saveUpdateRole 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertRole(SysRoleSaveUpdateDTO saveUpdateRole) {
        SysRole sysRole = BeanUtil.copyProperties(saveUpdateRole, SysRole.class);
        // 新增角色信息
        sysRole.setCreateTime(new Date());
        sysRole.setCreateId(SecurityUtils.getUserId());
        this.save(sysRole);
        return insertRoleMenu(sysRole.getRoleId(), saveUpdateRole.getMenuIds());
    }

    /**
     * 修改保存角色信息
     *
     * @param saveUpdateRole 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(SysRoleSaveUpdateDTO saveUpdateRole) {
        SysRole sysRole = BeanUtil.copyProperties(saveUpdateRole, SysRole.class);
        // 修改角色信息
        this.updateById(sysRole);
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(sysRole.getRoleId());
        return insertRoleMenu(sysRole.getRoleId(), saveUpdateRole.getMenuIds());
    }

    /**
     * 修改角色状态
     *
     * @param role 角色信息
     * @return 结果
     */
    @Override
    public boolean updateRoleStatus(SysRole role) {
        return this.updateById(role);
    }

    /**
     * 修改数据权限信息
     *
     * @param dto 角色信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean authDataScope(SysRoleSaveUpdateDTO dto) {
        SysRole sysRole = BeanUtil.copyProperties(dto, SysRole.class);
        // 修改角色信息
        this.updateById(sysRole);
        // 删除角色与部门关联
        roleDeptService.remove(Wrappers.<SysRoleDept>lambdaQuery().eq(SysRoleDept::getRoleId, dto.getRoleId()));
        // 新增角色和部门信息（数据权限）
        return insertRoleDept(dto.getRoleId(), dto.getDeptIds());
    }

    /**
     * 新增角色菜单信息
     */
    public boolean insertRoleMenu(Long roleId, Long[] menuIds) {
        if (ArrayUtil.isNotEmpty(menuIds)) {
            List<SysRoleMenu> roleMenus = CollUtil.newArrayList(menuIds).stream()
                    .map(m -> {
                        SysRoleMenu rm = new SysRoleMenu();
                        rm.setRoleId(roleId);
                        rm.setMenuId(m);
                        return rm;
                    })
                    .collect(Collectors.toList());
            return roleMenuService.saveBatch(roleMenus);
        }
        return true;
    }

    /**
     * 新增角色部门信息(数据权限)
     */
    public boolean insertRoleDept(Long roleId, Long[] deptIds) {
        if (ArrayUtil.isNotEmpty(deptIds)) {
            List<SysRoleDept> roleDepts = CollUtil.newArrayList(deptIds).stream()
                    .map(d -> {
                        SysRoleDept rd = new SysRoleDept();
                        rd.setRoleId(roleId);
                        rd.setDeptId(d);
                        return rd;
                    })
                    .collect(Collectors.toList());
            return roleDeptService.saveBatch(roleDepts);
        }
        return true;
    }

    /**
     * 通过角色ID删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteRoleById(Long roleId) {
        // 删除角色与菜单关联
        roleMenuMapper.deleteRoleMenuByRoleId(roleId);
        // 删除角色与部门关联
        roleDeptMapper.deleteRoleDeptByRoleId(roleId);
        return roleMapper.deleteRoleById(roleId);
    }

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteRoleByIds(Long[] roleIds) {
        for (Long roleId : roleIds) {
            checkRoleAllowed(new SysRole(roleId));
            checkRoleDataScope(roleId);
            SysRole role = this.getById(roleId);
            if (countUserRoleByRoleId(roleId) > 0) {
                throw new ServiceException(String.format("%1$s已分配,不能删除", role.getRoleName()));
            }
        }
        List<Long> roleList = CollUtil.newArrayList(roleIds);
        // 删除角色与菜单关联
        roleMenuService.remove(Wrappers.<SysRoleMenu>lambdaQuery().in(SysRoleMenu::getRoleId, roleList));
        // 删除角色与部门关联
        roleDeptService.remove(Wrappers.<SysRoleDept>lambdaQuery().in(SysRoleDept::getRoleId, roleList));
        return this.update(Wrappers.<SysRole>lambdaUpdate().set(SysRole::getDelFlag, "2").in(SysRole::getRoleId, roleList));
    }

    /**
     * 取消授权用户角色
     *
     * @param userRole 用户和角色关联信息
     * @return 结果
     */
    @Override
    public boolean deleteAuthUser(SysUserRole userRole) {
        return userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRoleId, userRole.getRoleId())
                .eq(SysUserRole::getUserId, userRole.getUserId()));
    }

    /**
     * 批量取消授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要取消授权的用户数据ID
     * @return 结果
     */
    @Override
    public boolean deleteAuthUsers(Long roleId, Long[] userIds) {
        return userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery()
                .eq(SysUserRole::getRoleId, roleId)
                .in(SysUserRole::getUserId, CollUtil.newArrayList(userIds)));
    }

    /**
     * 批量选择授权用户角色
     *
     * @param roleId  角色ID
     * @param userIds 需要授权的用户数据ID
     * @return 结果
     */
    @Override
    public int insertAuthUsers(Long roleId, String[] userIds) {
        // 新增用户与角色管理
        List<SysUserRole> list = new ArrayList<SysUserRole>();
        for (String userId : userIds) {
            SysUserRole ur = new SysUserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        return userRoleMapper.batchUserRole(list);
    }
}
