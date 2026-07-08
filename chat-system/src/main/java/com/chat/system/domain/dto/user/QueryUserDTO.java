package com.chat.system.domain.dto.user;

import com.chat.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * @program: chat
 * @description:
 * @author: peiyue.xing
 * @create: 2023-12-10 22:54
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
public class QueryUserDTO extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -1414196371644229581L;
    private String userId;
    private Long roleId;
    private String userName;
    private String phone;
    private String beginTime;
    private String endTime;
    private String status;
    private String userType;
    private Long deptId;
}
