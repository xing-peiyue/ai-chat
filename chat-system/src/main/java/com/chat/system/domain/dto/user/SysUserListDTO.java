package com.chat.system.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: chat
 * @description: 集合
 * @author: peiyue.xing
 * @create: 2023-12-10 22:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class SysUserListDTO implements Serializable {
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

    /**
     * 联系人
     */
    private String contact;

    /**
     * 公司名称
     */
    private String company;
    /**
     * 用户类型 00 系统用户  01代理商
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
     * 对外客服地址
     */
    private String chatQrUrl;
    /**
     * 接入渠道
     */
    private String channel;

    private String remark;

    /**
     * 来源
     */
    private String source;
    /**
     * 过期时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String deptName;
}
