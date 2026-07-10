package com.chat.common.core.domain.dto.message;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ：xingpeiyue
 * @date ：2023/8/8 14:53
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class UserMessageTextDTO implements Serializable {
    private static final long serialVersionUID = -5437032606817229850L;
    /**
     * 消息发送人
     */
    private String userId;
    /**
     * 消息接收人
     */
    private String receiverId;
    private String talkId;
    private String text;
}
