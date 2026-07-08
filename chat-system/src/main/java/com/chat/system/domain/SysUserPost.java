package com.chat.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 用户和岗位关联 sys_user_post
 *
 * @author chat
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
@TableName("sys_user_post")
public class SysUserPost implements Serializable {
    private static final long serialVersionUID = 2088283021634677695L;
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 岗位ID
     */
    private Long postId;

}
