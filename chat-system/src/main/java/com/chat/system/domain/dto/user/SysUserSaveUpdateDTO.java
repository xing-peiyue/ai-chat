package com.chat.system.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @program: chat
 * @description:
 * @author: peiyue.xing
 * @create: 2023-12-12 23:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class SysUserSaveUpdateDTO implements Serializable {
    private static final long serialVersionUID = -2528328743404992875L;
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

    private String password;

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
     * 用户类型 00 系统用户  01代理商  02 普通用户  03 访客
     */
    private String userType;

    /**
     * 状态
     */
    private String status;

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

    private Long[] roleIds;
    private Long[] postIds;
}
