package com.chat.common.core.domain.dto.visitor;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：xingpeiyue
 * @date ：2023/8/22 9:28
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class UserVisitorDTO implements Serializable {
    private static final long serialVersionUID = 9070303482614408674L;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户类型 00 系统用户  01代理商  02 普通用户  03 访客
     */
    private String userType;


    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
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

    /**
     * 最后登录时间
     */
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
     * 最后一条消息
     */
    private String content;

    private Date lastTime;

    /**
     * 是否撤回消息
     */
    private String isRevoke;

    /**
     * 聊天的会话id
     */
    private String talkId;

    private String sessionId;
    /**
     * 所有公司访客
     */
    private String company;


}
