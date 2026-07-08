package com.chat.system.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 用户坐席查询
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
public class QueryUserSeatDTO implements Serializable {
    private static final long serialVersionUID = 7600322720616482410L;
    /**
     * 接入公司
     */
    private Long companyId;
    private Long deptId;
    private String userName;
    private String nickName;
    private String status;
    private String name;
}
