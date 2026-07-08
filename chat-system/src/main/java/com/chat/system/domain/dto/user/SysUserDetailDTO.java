package com.chat.system.domain.dto.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chat.common.core.domain.entity.SysDept;
import com.chat.common.core.domain.entity.SysRole;
import com.chat.common.core.domain.entity.SysUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ：xingpeiyue
 * @date ：2023/12/12 17:31
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
public class SysUserDetailDTO implements Serializable {
    private static final long serialVersionUID = -3139731226231925966L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    private String password;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 公司名称
     */
    private String company;
    /**
     * 用户类型 00 系统用户  01代理商  02 普通用户  03 访客
     */
    private String userType;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phone;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 帐号状态（1正常 0停用）
     */
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
    private String loginIp;

    private String loginLocation;
    private String browser;
    private String os;
    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginDate;
    /**
     * 是否在线  0 否  1 是
     */
    private String online;

    /**
     * 接入状态  1 接入  2 等待
     */
    private String access;

    /**
     * 对外客服地址
     */
    private String chatQrUrl;
    /**
     * 接入渠道
     */
    private String channel;

    private String remark;

    private String deptName;

    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
    private String createId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private SysDept dept;
    private List<SysRole> roles;
    private Long[] roleIds;
    private Long[] postIds;
    private Long roleId;
}
