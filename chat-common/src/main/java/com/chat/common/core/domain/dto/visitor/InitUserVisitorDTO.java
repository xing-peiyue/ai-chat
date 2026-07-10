package com.chat.common.core.domain.dto.visitor;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ：xingpeiyue
 * @date ：2023/8/9 12:03
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class InitUserVisitorDTO implements Serializable {
    private static final long serialVersionUID = 9070303482614408674L;
    /**
     * 访客id
     */
    private String userId;
    private String clientId;
}
