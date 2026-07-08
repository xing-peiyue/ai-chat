package com.chat.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：xingpeiyue
 * @date ：2024/1/15 20:55
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
@TableName("sys_user_company")
public class SysUserCompany implements Serializable {
    private static final long serialVersionUID = 833530971565394433L;
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String userId;
    private Long companyId;
    /**
     * 过期时间
     */
    private Date expireTime;
    private String nickName;
    private String createId;
    /**
     * 开始时间
     */
    private Date createTime;
}
