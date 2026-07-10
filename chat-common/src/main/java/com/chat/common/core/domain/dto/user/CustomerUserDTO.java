package com.chat.common.core.domain.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ：xingpeiyue
 * @date ：2023/11/17 16:52
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class CustomerUserDTO implements Serializable {
    private static final long serialVersionUID = 3370528072574232538L;
    private Long id;
    private String userId;
    private String userName;
    private String nickName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 公司名称
     */
    private String name;
    private String companyId;
    /**
     * 公司简称
     */
    private String shortName;
    private String status;
    /**
     * 默认密码
     */
    private String defaultPwd;
    /**
     * 已接客服数
     */
    private Long visitors;
    private String url;
    private String createId;
    /**
     * 归属公司
     */
    private String companyUser;
}
