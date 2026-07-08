package com.chat.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 当前在线会话
 *
 * @author chat
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class SysUserOnline implements Serializable {
    private static final long serialVersionUID = 4502394315568471641L;
    /**
     * 会话编号
     */
    private String tokenId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地址
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录时间
     */
    private Long loginTime;

}
