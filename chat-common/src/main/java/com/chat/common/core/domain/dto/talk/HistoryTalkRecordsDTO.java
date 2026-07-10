package com.chat.common.core.domain.dto.talk;

import com.chat.common.core.domain.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

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
public class HistoryTalkRecordsDTO extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 116714321234366912L;

    /**
     * 访客名称
     */
    private String visitorName;

    /**
     * 访客标识
     */
    private String visitorUserId;

    /**
     * 渠道 1:h5 2:微信小程序 3:微信公众号
     */
    private String channel;

    /**
     * 客服标识
     */
    private String userId;

}
