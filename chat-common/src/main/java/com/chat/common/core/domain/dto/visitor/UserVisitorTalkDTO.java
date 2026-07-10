package com.chat.common.core.domain.dto.visitor;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 后台点击接入会话
 *
 * @author ：xingpeiyue
 * @date ：2023/8/24 17:22
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class UserVisitorTalkDTO implements Serializable {
    private static final long serialVersionUID = -3432785293588222767L;
    private String visitorId;
    private List<String> visitorIds;
}
