package com.chat.common.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信第三方开放平台配置
 * @author ：zk
 * @date ：2023/11/27 10:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "wxopen.config")
public class WxOpenConfig {

    // 第三方平台 Appid
    private String componentAppId;

    // 第三方平台 AppSecret
    private String componentAppSecret;

    // 第三方平台 授权成功回调url
    private String redirectUrl;

    //在第三方平台填写的token
    private String token;

    //在第三方平台填写的加解密key
    private String encodingAesKey;

}
