package com.chat.common.core.domain.dto.talk;

import com.chat.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：zk
 * @date ：2023/11/9 19:20
 * @version: 2.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
public class TalkRecordsPageDTO implements Serializable {

    private static final long serialVersionUID = 116432132234366912L;

    /**
     * 会话ID
     */
    private String talkId;

    /**
     * 发送人
     */
    private String userName;

    /**
     * 接收人
     */
    private String receiver;

    /**
     * 用户类型
     */
    private String userType;

    private String receiverType;

    private String receiverId;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
