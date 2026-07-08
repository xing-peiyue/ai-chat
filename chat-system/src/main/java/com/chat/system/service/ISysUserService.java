package com.chat.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chat.common.core.domain.dto.user.CustomerUserStatisticsDTO;
import com.chat.common.core.domain.dto.user.HomeUserNoticeDTO;
import com.chat.common.core.domain.entity.SysUser;
import com.chat.system.domain.dto.user.*;

import java.util.List;

/**
 * 用户 业务层
 *
 * @author xpy
 */
public interface ISysUserService extends IService<SysUser> {
    /**
     * 根据条件分页查询用户列表
     *
     * @param model 用户信息
     * @return 用户信息集合信息
     */
    List<SysUserListDTO> selectUserList(QueryUserDTO model);

    /**
     * 根据条件分页查询已分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUserListDTO> selectAllocatedList(QueryUserDTO user);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param user 用户信息
     * @return 用户信息集合信息
     */
    List<SysUserListDTO> selectUnallocatedList(QueryUserDTO user);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUserDetailDTO selectUserByUserName(String userName);

    /**
     * @description: 获取账号
     * @author: xingpeiyue
     * @time: 2023/9/6 14:10
     */
    SysUser selectByUserName(String userName);

    SysUser selectByUserId(String userId);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUserDetailDTO selectUserByUserId(String userId);

    /**
     * 根据用户ID查询用户所属角色组
     *
     * @param userName 用户名
     * @return 结果
     */
    String selectUserRoleGroup(String userName);

    /**
     * 根据用户ID查询用户所属岗位组
     *
     * @param userName 用户名
     * @return 结果
     */
    String selectUserPostGroup(String userName);

    /**
     * 校验用户名称是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkUserNameUnique(SysUser user);

    /**
     * 校验手机号码是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkPhoneUnique(SysUser user);

    /**
     * 校验email是否唯一
     *
     * @param user 用户信息
     * @return 结果
     */
    String checkEmailUnique(SysUser user);

    /**
     * 校验用户是否允许操作
     *
     * @param user 用户信息
     */
    void checkUserAllowed(SysUser user);

    /**
     * 校验用户是否有数据权限
     *
     * @param userId 用户id
     */
    void checkUserDataScope(String userId);

    /**
     * 新增用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int insertUser(SysUserSaveUpdateDTO user);

    /**
     * 注册用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean registerUser(SysUser user);

    /**
     * 修改用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean updateUser(SysUserSaveUpdateDTO user);

    /**
     * 用户授权角色
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     */
    void insertUserAuth(String userId, Long[] roleIds);

    /**
     * 修改用户状态
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean updateUserStatus(SysUser user);

    /**
     * 修改用户基本信息
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean updateUserProfile(SysUser user);


    /**
     * 重置用户密码
     *
     * @param user 用户信息
     * @return 结果
     */
    boolean resetPwd(SysUser user);

    /**
     * 重置用户密码
     *
     * @param userId   用户账号
     * @param password 密码
     * @return 结果
     */
    boolean resetUserPwd(String userId, String password);

    /**
     * 通过用户ID删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    boolean deleteUserByUserId(String userId);

    /**
     * 批量删除用户信息
     *
     * @param userIds 需要删除的用户ID
     * @return 结果
     */
    boolean deleteUserByUserIds(String[] userIds);

    /**
     * @description: 缓存查找用户
     * @author: xingpeiyue
     * @time: 2023/8/18 14:07
     */
    SysUser selectCacheSysUser(String userId);

    /**
     * @description: 根据当前用户生成客服二维码
     * @author: xingpeiyue
     * @time: 2023/9/13 17:38
     */
    boolean userChatCodeUrl(SysUser sysUser);

    /**
     * @description: 获取用户表信息
     * @author: xingpeiyue
     * @time: 2023/9/18 21:34
     */
    SysUser userInfo(String userId);

    HomeUserNoticeDTO homeUserNotice(String deptId);

    /**
     * @description: 新增客服  分配角色
     * @author: xingpeiyue
     * @time: 2023/11/17 15:21
     */
    SysUser registerCustomer(String username, String nickName);


    /**
     * @description: 客服统计
     * @author: xingpeiyue
     * @time: 2023/11/20 10:59
     */
    List<CustomerUserStatisticsDTO> customerStatistics();

    /**
     * @description: 查找当前账号下的所有人员
     * @author: xingpeiyue
     * @time: 2024/2/3 19:04
     */
    List<String> selectUserId();


    /**
     * @description: 修改客服坐席密码和昵称
     * @author: xingpeiyue
     * @time: 2024/2/28 12:00
     */
    boolean updateCustomerSeat(UserSeatDTO user);
}
