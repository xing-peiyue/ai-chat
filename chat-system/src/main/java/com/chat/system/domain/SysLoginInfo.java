package com.chat.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chat.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * 系统访问记录表
 * 登录信息表
 *
 * @author xpy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@SuperBuilder
@ToString
@TableName("sys_login_info")
public class SysLoginInfo extends BaseEntity {
    private static final long serialVersionUID = 7443947916374547762L;
    /**
     * ID
     */
    @TableId(value = "info_id", type = IdType.AUTO)
    private Long infoId;

    /**
     * 用户账号
     */
    private String userName;

    /**
     * 登录状态 0成功 1失败
     */
    private String status;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 访问时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loginTime;

}
