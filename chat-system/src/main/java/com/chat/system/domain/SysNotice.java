package com.chat.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chat.common.core.domain.BaseEntity;
import com.chat.common.xss.Xss;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * 通知公告表 sys_notice
 *
 * @author chat
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@ToString
@TableName("sys_notice")
public class SysNotice extends BaseEntity {

    private static final long serialVersionUID = 1980083088001850855L;
    /**
     * 公告ID
     */
    @TableId(value = "notice_id", type = IdType.AUTO)
    private Long noticeId;

    /**
     * 公告标题
     */
    @Xss(message = "公告标题不能包含脚本字符")
    @NotBlank(message = "公告标题不能为空")
    @Size(min = 0, max = 50, message = "公告标题不能超过50个字符")
    private String noticeTitle;

    /**
     * 公告类型（1通知 2公告）
     */
    private String noticeType;

    /**
     * 公告内容
     */
    private String noticeContent;

    /**
     * 公告状态（0正常 1关闭）
     */
    private String status;
    
    private String remark;

}
