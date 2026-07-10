package com.chat.common.core.domain.dto.user;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户对象 sys_user
 *
 * @author xpy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class SysUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;
    private String phone;
    private String nickName;
    /**
     * 登录账号
     */
    private String userName;
    private String userType;
    private String email;
    /**
     * 头像
     */
    private String avatar;
    private String online;
    private String access;
    private Date createTime;
    private Date updateTime;
    /**
     * 最后一条信息
     */
    private String lastMsg;
    private String lastTime;
    /**
     * 消息类型
     */
    private String msgType;
    /**
     * 未读消息个数
     */
    private Integer unreadNum;
    private String isRevoke;
    /**
     * 会话id
     */
    private String talkId;

    /**
     * 会话状态
     */
    private String sessionStatus;

    /**
     * 标记
     */
    private String mark;

    private String channelType;
    private String companyName;
    private String channelName;

    private Long channelId;
    private Long companyId;

}
