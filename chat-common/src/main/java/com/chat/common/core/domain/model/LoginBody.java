package com.chat.common.core.domain.model;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 用户登录对象
 *
 * @author xpy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@ToString
public class LoginBody {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;
    private String phone;
    private String phoneCode;
    /**
     * type = 1 手机号登录  2 账号密码登录
     */
    private String type;

    /**
     * 1 手机端使用  2 pc端使用  防止统一账号  多平台登录
     */
    private String pt;

}
