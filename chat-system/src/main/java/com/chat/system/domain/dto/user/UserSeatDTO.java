package com.chat.system.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 用户坐席
 *
 * @author ：xingpeiyue
 * @date ：2024/1/16 18:40
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
public class UserSeatDTO implements Serializable {
    private static final long serialVersionUID = 7600322720616482410L;
    /**
     * 坐席id
     */
    private Long id;
    private String nickName;
    private String password;

}
