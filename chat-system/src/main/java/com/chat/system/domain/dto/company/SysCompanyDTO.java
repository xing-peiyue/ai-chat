package com.chat.system.domain.dto.company;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author ：zk
 * @date ：2024/1/24 11:08
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
public class SysCompanyDTO implements Serializable {
    /**
     * 默认的系统clientId  最大部门
     */
    public static final String CLIENT_ID = "xt123456";
    private static final long serialVersionUID = -2560743212412892660L;
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

    /**
     * 渠道个数
     */
    private long channelCount;
    /**
     * 已接渠道类型
     */
    private Set<Long> channelTypes;

    /**
     * 坐席个数
     */
    private long seatCount;
}
