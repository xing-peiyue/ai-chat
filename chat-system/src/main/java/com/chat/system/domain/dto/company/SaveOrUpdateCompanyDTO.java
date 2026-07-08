package com.chat.system.domain.dto.company;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ：xingpeiyue
 * @date ：2024/1/12 19:00
 * @version: 1.0
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
public class SaveOrUpdateCompanyDTO implements Serializable {
    private static final long serialVersionUID = -3324033745991914540L;
    private Long id;
    @NotNull(message = "公司名称不能为空")
    private String name;
    @NotNull(message = "公司简称不能为空")
    private String shortName;
    @NotNull(message = "联系人不能为空")
    private String nickName;
    @NotNull(message = "手机号不能为空")
    private String phone;
    private String email;
    private String address;
    private String createId;
    private Date createTime;
    private String updateId;
    private Date updateTime;
    private String status;
}
