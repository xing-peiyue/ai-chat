package com.chat.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chat.common.core.domain.dto.user.CustomerUserDTO;
import com.chat.common.core.domain.dto.user.CustomerUserStatisticsDTO;
import com.chat.common.core.domain.dto.user.CustomerVisitorDTO;
import com.chat.common.core.domain.entity.SysUser;
import com.chat.system.domain.dto.user.QueryUserDTO;
import com.chat.system.domain.dto.user.QueryUserSeatDTO;
import com.chat.system.domain.dto.user.SysUserDetailDTO;
import com.chat.system.domain.dto.user.SysUserListDTO;

import java.util.List;

/**
 * 用户表 数据层
 *
 * @author chat
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据条件分页查询用户列表
     *
     * @param model 用户信息
     * @return 用户信息集合信息
     */
    List<SysUserListDTO> selectUserList(QueryUserDTO model);

    /**
     * 根据条件分页查询已配用户角色列表
     *
     * @param model 用户信息
     * @return 用户信息集合信息
     */
    List<SysUserListDTO> selectAllocatedList(QueryUserDTO model);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param model 用户信息
     * @return 用户信息集合信息
     */
    List<SysUserListDTO> selectUnallocatedList(QueryUserDTO model);

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUserDetailDTO selectUserByUserName(String userName);

    /**
     * 通过用户ID查询用户
     *
     * @param userId 用户ID
     * @return 用户对象信息
     */
    SysUserDetailDTO selectUserByUserId(String userId);

    /**
     * @description: 客服对接的用户
     * @author: xingpeiyue
     * @time: 2023/11/20 14:22
     */
    List<CustomerVisitorDTO> customerVisitorList(SysUser model);

    /**
     * @description: 坐席列表查询
     * @author: xingpeiyue
     * @time: 2024/1/16 9:25
     */
    List<CustomerUserDTO> listCustomerSeats(SysUser model);

    /**
     * @description: 客服接入坐席记录
     * @author: xingpeiyue
     * @time: 2024/1/16 18:44
     */
    List<CustomerUserStatisticsDTO> listCustomerSeatsRecord(QueryUserSeatDTO user);
}
