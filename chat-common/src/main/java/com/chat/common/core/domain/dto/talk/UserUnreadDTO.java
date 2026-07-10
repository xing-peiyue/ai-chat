package com.chat.common.core.domain.dto.talk;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author ：xingpeiyue
 * @date ：2023/8/8 17:37
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class UserUnreadDTO implements Serializable {
    private static final long serialVersionUID = 3558478066347961086L;
    private String userId;
}
