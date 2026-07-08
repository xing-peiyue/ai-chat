package com.chat.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chat.common.annotation.DataScope;
import com.chat.common.config.ChatConfig;
import com.chat.common.config.UploadProjectProperties;
import com.chat.common.constant.CacheConstants;
import com.chat.common.constant.UserConstants;
import com.chat.common.core.domain.AjaxResult;
import com.chat.common.core.domain.dto.user.CustomerUserStatisticsDTO;
import com.chat.common.core.domain.dto.user.HomeUserNoticeDTO;
import com.chat.common.core.domain.entity.SysDept;
import com.chat.common.core.domain.entity.SysRole;
import com.chat.common.core.domain.entity.SysUser;
import com.chat.common.core.domain.model.LoginUser;
import com.chat.common.core.redis.RedisCache;
import com.chat.common.enums.UserTypeEnum;
import com.chat.common.exception.ServiceException;
import com.chat.common.utils.IdWorker;
import com.chat.common.utils.SecurityUtils;
import com.chat.common.utils.StringUtils;
import com.chat.common.utils.qrcode.QrCodeUtils;
import com.chat.common.utils.spring.SpringUtils;
import com.chat.system.domain.*;
import com.chat.system.domain.dto.user.*;
import com.chat.system.mapper.*;
import com.chat.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import jakarta.annotation.Resource;
import javax.management.relation.Role;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 用户 业务层处理
 *
 * @author chat
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    private SysPostMapper postMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysUserPostMapper userPostMapper;

    @Resource
    private ISysUserPostService userPostService;

    @Resource
    private ISysUserRoleService userRoleService;

    @Resource
    private ISysDeptService deptService;
    @Resource
    private ISysRoleService roleService;
    @Resource
    private ISysCompanyService companyService;
    @Resource
    private ISysUserCompanyService userCompanyService;
    @Resource
    private IdWorker idWorker;
    @Resource
    private RedisCache redisCache;

    /**
     * 根据条件分页查询用户列表
     *
     * @param model 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUserListDTO> selectUserList(QueryUserDTO model) {
        return baseMapper.selectUserList(model);
    }

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param model 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUserListDTO> selectAllocatedList(QueryUserDTO model) {
        return baseMapper.selectAllocatedList(model);
    }

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param model 用户信息
     * @return 用户信息集合信息
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<SysUserListDTO> selectUnallocatedList(QueryUserDTO model) {
        return baseMapper.selectUnallocatedList(model);
    }

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    @Override
    public SysUserDetailDTO selectUserByUserName(String userName) {
        return baseMapper.selectUserByUserName(userName);
    }

    @Override
    public SysUser selectByUserName(String userName) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, userName).eq(SysUser::getDelFlag, "0").last("limit 1"));
        return sysUser;
    }

    @Override
    public SysUser selectByUserId(String userId) {
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserId, userId).eq(SysUser::getDelFlag, "0").last("limit 1"));
        return sysUser;
    }

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    @Override
    public SysUserDetailDTO selectUserByUserId(String userId) {
        SysUserDetailDTO dto = new SysUserDetailDTO();
        SysUser sysUser = this.selectByUserId(userId);
        if (ObjectUtil.isNotNull(sysUser)) {
            BeanUtil.copyProperties(sysUser, dto);
            SysDept dept = deptService.getById(sysUser.getDeptId());
            dto.setDept(dept);
            List<SysRole> sysRoles = roleService.selectRolesByUserId(userId);
            dto.setRoles(sysRoles);
        }
        return dto;
    }

    /**
     * 查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserRoleGroup(String userName) {
        List<SysRole> list = roleMapper.selectRolesByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysRole::getRoleName).collect(Collectors.joining(","));
    }

    /**
     * 查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    @Override
    public String selectUserPostGroup(String userName) {
        List<SysPost> list = postMapper.selectPostsByUserName(userName);
        if (CollectionUtils.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        return list.stream().map(SysPost::getPostName).collect(Collectors.joining(","));
    }

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public String checkUserNameUnique(SysUser user) {
        String userId = StrUtil.blankToDefault(user.getUserId(), "-1");
        SysUser info = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserName, user.getUserName())
                .eq(SysUser::getDelFlag, "0")
                .last("limit 1"));
        if (ObjectUtil.isNotNull(info) && !StrUtil.equals(info.getUserId(), userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkPhoneUnique(SysUser user) {
        String userId = StrUtil.blankToDefault(user.getUserId(), "-1");
        SysUser info = this.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getPhone, user.getPhone())
                .eq(SysUser::getDelFlag, "0")
                .last("limit 1"));
        if (ObjectUtil.isNotNull(info) && !StrUtil.equals(info.getUserId(), userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return
     */
    @Override
    public String checkEmailUnique(SysUser user) {
        String userId = StrUtil.blankToDefault(user.getUserId(), "-1");
        SysUser info = this.getOne(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getEmail, user.getEmail())
                .eq(SysUser::getDelFlag, "0")
                .last("limit 1"));
        if (ObjectUtil.isNotNull(info) && !StrUtil.equals(info.getUserId(), userId)) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    @Override
    public void checkUserAllowed(SysUser user) {
        if (ObjectUtil.isNotNull(user.getUserId()) && user.isAdmin()) {
            throw new ServiceException("不允许操作超级管理员用户");
        }
    }

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    @Override
    public void checkUserDataScope(String userId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            QueryUserDTO build = QueryUserDTO.builder().userId(userId).build();
            List<SysUserListDTO> users = SpringUtils.getAopProxy(this).selectUserList(build);
            if (StringUtils.isEmpty(users)) {
                throw new ServiceException("没有权限访问用户数据！");
            }
        }
    }

    /**
     * 新增保存用户信息
     *
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(SysUserSaveUpdateDTO dto) {
        SysUser user = BeanUtil.copyProperties(dto, SysUser.class);
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        // 新增用户信息
        user.setUserId(idWorker.genId()).setCreateTime(new Date()).setCreateId(SecurityUtils.getUserId());
        this.save(user);
        // 新增用户岗位关联
        insertUserPost(user.getUserId(), dto.getPostIds());
        // 新增用户与角色管理
        insertUserRole(user.getUserId(), dto.getRoleIds());
        return 1;
    }

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean registerUser(SysUser user) {
        user.setUserId(idWorker.genId()).setCreateTime(new Date());
        //当前代理商生成一个部门
        SysDept build = SysDept.builder().parentId(SysDept.DEPT_ID).deptName(user.getCompany()).build();
        deptService.insertDept(build);
        SysUserRole userRole = SysUserRole.builder().userId(user.getUserId()).roleId(SysRole.AGENT).build();
        userRoleMapper.insert(userRole);
        user.setDeptId(build.getDeptId());
        //生成公司
        SysCompany company = SysCompany.builder().deptId(build.getDeptId()).isDefault("1").name(user.getCompany()).shortName(user.getCompany())
                .email(user.getEmail()).nickName(user.getNickName()).phone(user.getPhone()).clientId(idWorker.genId())
                .createTime(new Date())
                .build();
        companyService.save(company);
        this.save(user);
        return true;
    }

    /**
     * 修改保存用户信息
     *
     * @param dto 用户信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(SysUserSaveUpdateDTO dto) {
        String userId = dto.getUserId();
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(userId, dto.getRoleIds());
        // 删除用户与岗位关联
        userPostMapper.deleteUserPostByUserId(userId);
        // 新增用户与岗位管理
        insertUserPost(dto.getUserId(), dto.getPostIds());
        SysUser user = BeanUtil.copyProperties(dto, SysUser.class);
        user.setUpdateTime(new Date()).setCreateId(SecurityUtils.getUserId());
        return this.update(user, Wrappers.<SysUser>lambdaUpdate().eq(SysUser::getUserId, userId));
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertUserAuth(String userId, Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        insertUserRole(userId, roleIds);
    }

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean updateUserStatus(SysUser user) {
        return this.update(user, Wrappers.<SysUser>lambdaUpdate().eq(SysUser::getUserId, user.getUserId()));
    }

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean updateUserProfile(SysUser user) {
        return this.update(user, Wrappers.<SysUser>lambdaUpdate().eq(SysUser::getUserId, user.getUserId()));
    }


    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    @Override
    public boolean resetPwd(SysUser user) {
        return this.update(user, Wrappers.<SysUser>lambdaUpdate().eq(SysUser::getUserId, user.getUserId()));
    }

    /**
     * 重置用户密码
     *
     * @param userId   用户账号
     * @param password 密码
     * @return 结果
     */
    @Override
    public boolean resetUserPwd(String userId, String password) {
        return this.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getPassword, password)
                .set(SysUser::getDefaultPwd, "0")
                .eq(SysUser::getUserId, userId));
    }

    /**
     * 新增用户岗位信息
     *
     * @param postIds 用户对象
     */
    public void insertUserPost(String userId, Long[] postIds) {
        if (ObjectUtil.isNotNull(postIds)) {
            List<SysUserPost> sysUserPosts = CollUtil.newArrayList(postIds).stream()
                    .map(s -> {
                        SysUserPost userPost = new SysUserPost();
                        userPost.setUserId(userId);
                        userPost.setPostId(s);
                        return userPost;
                    })
                    .collect(Collectors.toList());
            userPostService.saveBatch(sysUserPosts);
        }
    }

    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    public void insertUserRole(String userId, Long[] roleIds) {
        if (StringUtils.isNotEmpty(roleIds)) {
            // 新增用户与角色管理
            List<SysUserRole> userRoles = CollUtil.newArrayList(roleIds).stream()
                    .map(t -> {
                        SysUserRole ur = new SysUserRole();
                        ur.setUserId(userId);
                        ur.setRoleId(t);
                        return ur;
                    })
                    .collect(Collectors.toList());
            userRoleService.saveBatch(userRoles);
        }
    }

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUserByUserId(String userId) {
        // 删除用户与角色关联
        userRoleMapper.deleteUserRoleByUserId(userId);
        // 删除用户与岗位表
        userPostMapper.deleteUserPostByUserId(userId);
        return this.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getDelFlag, "2")
                .eq(SysUser::getUserId, userId));
    }

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUserByUserIds(String[] userIds) {
        for (String userId : userIds) {
            checkUserAllowed(SysUser.builder().userId(userId).build());
            checkUserDataScope(userId);
        }
        // 删除用户与角色关联
        userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().in(SysUserRole::getUserId, userIds));
        // 删除用户与岗位关联
        userPostService.remove(Wrappers.<SysUserPost>lambdaQuery().in(SysUserPost::getUserId, userIds));
        return this.update(Wrappers.<SysUser>lambdaUpdate()
                .set(SysUser::getDelFlag, "2")
                .in(SysUser::getUserId, userIds));
    }


    @Override
    public SysUser selectCacheSysUser(String userId) {
        SysUser sysUser = redisCache.getCacheObject(CacheConstants.SYS_USER_KEY + userId);
        if (ObjectUtil.isNotNull(sysUser)) {
            return sysUser;
        }
        SysUser user = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserId, userId));
        redisCache.setCacheObject(CacheConstants.SYS_USER_KEY, user, 60 * 2, TimeUnit.MINUTES);
        return user;
    }

    @Override
    public boolean userChatCodeUrl(SysUser sysUser) {
        QrConfig config = new QrConfig(300, 300);
        // 设置边距，既二维码和背景之间的边距
        config.setMargin(3);
        try {
//            String base64 = QrCodeUtil.generateAsBase64(ChatConfig.getVisitorUrl() + "?deptId=" + sysUser.getDeptId(), config, ImgUtil.IMAGE_TYPE_PNG);
            String base64 = QrCodeUtils.generateQRCodeImage(ChatConfig.getVisitorUrl() + "?deptId=" + sysUser.getDeptId(), sysUser.getCompany(), 300, 300);
            HashMap<String, Object> paramMap = new HashMap<>();
            paramMap.put("file", base64);
            String result = HttpUtil.post(UploadProjectProperties.getDomain() + "/file/upload/base64/chatQr", paramMap);
            JSONObject object = JSONUtil.parseObj(result);
            JSONObject data = object.getJSONObject("data");
            String fullUrl = data.getStr("fullUrl");
            SysUser build = SysUser.builder().chatQrUrl(fullUrl).id(sysUser.getId()).build();
            return this.updateById(build);
        } catch (Exception e) {
            log.error("创建用户生成客服聊天二维码失败", e);
            return false;
        }

    }

    @Override
    public SysUser userInfo(String userId) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        Long deptId = user.getDeptId();
        return this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserId, userId).eq(SysUser::getDeptId, deptId).last("limit 1"));
    }

    @Override
    public HomeUserNoticeDTO homeUserNotice(String deptId) {
        HomeUserNoticeDTO dto = new HomeUserNoticeDTO();
        LambdaQueryWrapper<SysUser> wrapper = Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getOnline, "1")
                .eq(SysUser::getDeptId, deptId);
        if (StrUtil.equals(deptId, SysDept.DEPT_ID + "")) {
            wrapper.eq(SysUser::getUserType, UserTypeEnum.ADMIN.getKey());
        } else {
            wrapper.in(SysUser::getUserType, UserTypeEnum.AGENT.getKey(), UserTypeEnum.CUSTOMER.getKey());
        }
        long count = this.count(wrapper);
        dto.setAllOffline(count > 0 ? "0" : "1");
        if (StrUtil.equals(deptId, SysDept.DEPT_ID + "")) {
            dto.setAvatar(UploadProjectProperties.getDomain() + "/upload/profile/xt.png");
            dto.setCompany("云领客服系统");
            dto.setUserName("云领客服系统");
            dto.setAutoOpenDialogTime(1000);
            dto.setDescription("简单好用的客服系统");
            return dto;
        }
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getDeptId, deptId).eq(SysUser::getUserType, "01"));
        if (ObjectUtil.isNull(sysUser)) {
            return dto;
        }
        dto.setAvatar(UploadProjectProperties.getDomain() + sysUser.getAvatar());
        dto.setCompany(sysUser.getCompany());
        dto.setUserName(sysUser.getNickName());
        dto.setAutoOpenDialogTime(1000);
        dto.setDescription("简单好用的客服系统");
        return dto;
    }


    @Override
    public List<CustomerUserStatisticsDTO> customerStatistics() {
        String userId = SecurityUtils.getUserId();
        List<SysUser> list = this.list(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getCreateId, userId)
                .eq(SysUser::getUserType, UserTypeEnum.CUSTOMER.getKey())
                .orderByAsc(SysUser::getStatus));
        List<CustomerUserStatisticsDTO> customers = Optional.ofNullable(list).orElseGet(ArrayList::new).stream()
                .map(t -> {
                    CustomerUserStatisticsDTO dto = new CustomerUserStatisticsDTO();
                    dto.setUserId(t.getUserId()).setUserName(t.getUserName()).setNickName(t.getNickName());
                    dto.setComplaints(0L).setSatisfaction("5.0分");
//                    long count = userVisitorTalkService.count(Wrappers.<UserVisitorTalk>lambdaQuery().eq(UserVisitorTalk::getUserId, t.getUserId()));
                    dto.setVisitors(0L);
                    return dto;
                }).collect(Collectors.toList());
        return customers;
    }

    @Override
    public List<String> selectUserId() {
        List<SysUser> users = this.list(Wrappers.<SysUser>lambdaQuery()
                .eq(SysUser::getDeptId, SecurityUtils.getDeptId()));
        if (CollUtil.isNotEmpty(users)) {
            return users.stream().map(SysUser::getUserId).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SysUser registerCustomer(String username, String nickName) {
        LoginUser loginUser = SecurityUtils.getLoginUser();
        SysUser user = loginUser.getUser();
        SysUser sysUser = new SysUser();
        Date now = new Date();
        sysUser.setUserId(idWorker.genId()).setDeptId(user.getDeptId()).setUserName(username).setNickName(nickName)
                .setUserType(UserTypeEnum.CUSTOMER.getKey()).setAvatar("/upload/profile/user.jpg")
                .setCreateTime(now).setCreateId(user.getUserId());
        sysUser.setPassword(SecurityUtils.encryptPassword("123456"));
        SysUserRole userRole = SysUserRole.builder().userId(sysUser.getUserId()).roleId(SysRole.CUSTOMER).build();
        userRoleMapper.insert(userRole);
        this.save(sysUser);
        //默认分配
        DateTime dateTime = DateUtil.offsetDay(now, 15);
        SysCompany company = companyService.selectDefaultCompany(user.getDeptId());
        SysUserCompany build = SysUserCompany.builder().companyId(company.getId()).expireTime(dateTime).nickName(nickName)
                .userId(sysUser.getUserId()).createTime(new Date()).createId(user.getUserId())
                .build();
        userCompanyService.save(build);
        return sysUser;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateCustomerSeat(UserSeatDTO user) {
        SysUserCompany userCompany = userCompanyService.getById(user.getId());
        if (ObjectUtil.isNull(userCompany)) {
            throw new ServiceException("客服坐席不存在");
        }

        String userId = userCompany.getUserId();
        SysUser sysUser = this.getOne(Wrappers.<SysUser>lambdaQuery().eq(SysUser::getUserId, userId));
        if (ObjectUtil.isNull(sysUser)) {
            throw new ServiceException("用户不存在");
        }
        if (StrUtil.isNotBlank(user.getNickName())) {
            userCompany.setNickName(user.getNickName());
            userCompanyService.updateById(userCompany);
            redisCache.setCacheObject(CacheConstants.SYS_USER_COMPANY + userId + ":" + userCompany.getCompanyId(), userCompany);
        }
        if (StrUtil.isNotBlank(user.getPassword())) {
            String password = SecurityUtils.encryptPassword(user.getPassword());
            SysUser build = SysUser.builder().id(sysUser.getId()).password(password).build();
            this.updateById(build);
        }
        return true;
    }
}
