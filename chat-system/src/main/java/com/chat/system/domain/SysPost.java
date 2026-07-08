package com.chat.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chat.common.core.domain.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 岗位表 sys_post
 *
 * @author chat
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
@TableName("sys_post")
public class SysPost implements Serializable {
    private static final long serialVersionUID = -5767875750006830361L;
    /**
     * 岗位序号
     */
    @TableId(value = "post_id", type = IdType.AUTO)
    private Long postId;
    private String postCode;

    /**
     * 岗位名称
     */
    private String postName;

    private String postSort;
    /**
     * 状态（1正常 0停用）
     */
    private String status;

    private String remark;
    private Date createTime;
    private String createId;
    private Date updateTime;
    private String updateId;

}
