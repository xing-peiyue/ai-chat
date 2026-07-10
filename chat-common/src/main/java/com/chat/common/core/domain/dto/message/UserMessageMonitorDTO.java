package com.chat.common.core.domain.dto.message;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ：xingpeiyue
 * @date ：2024/1/26 16:35
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class UserMessageMonitorDTO implements Serializable {
    private static final long serialVersionUID = 3006081285468880172L;
    private String userId;
    private String receiverId;
    /**
     * 消息类型 1 文本  2 图片 3.语音
     */
    private String eventType;
    /**
     * 发送者类型 1 客服 2 访客 3 机器人
     */
    private String userType;
    private String text;
    private String imgUrl;
}
