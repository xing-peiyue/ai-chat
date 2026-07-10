package com.chat.common.core.domain.model;

import lombok.*;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 用户注册对象
 *
 * @author xpy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class RegisterBody implements Serializable {
    private static final long serialVersionUID = 5576031325028393397L;
    @NotNull(message = "公司名称不能为空")
    private String company;
    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    private String phone;
    /**
     * 验证码
     */
    @NotNull(message = "验证码不能为空")
    private String code;
    private String contact;
    private List<String> channel;
}
