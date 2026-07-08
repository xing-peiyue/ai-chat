package com.chat.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：xingpeiyue
 * @date ：2024/1/15 11:08
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
@TableName("sys_company")
public class SysCompany implements Serializable {
    /**
     * 默认的系统clientId  最大部门
     */
    public static final String CLIENT_ID = "xt123456";
    private static final long serialVersionUID = -2560726860412892660L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long deptId;
    /**
     * 公司唯一识别码
     */
    private String clientId;
    /**
     * 公司名称
     */
    private String name;
    /**
     * 简称
     */
    private String shortName;
    private String nickName;
    private String phone;
    private String email;
    private String address;
    /**
     * 是否默认
     */
    private String isDefault;
    private String createId;
    private Date createTime;
    private String updateId;
    private Date updateTime;
    private String status;
}
