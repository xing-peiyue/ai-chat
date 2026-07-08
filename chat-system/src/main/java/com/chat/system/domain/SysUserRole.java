package com.chat.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户和角色关联 sys_user_role
 *
 * @author xpy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
@TableName("sys_user_role")
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -9003706438468994014L;
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    private String userId;
    /**
     * 角色ID
     */
    private Long roleId;

}
