package com.chat.system.service;

/**
 * 短信请求接口
 *
 * @author ：xingpeiyue
 * @date ：2023/9/4 16:23
 * @version: 1.0
 */
public interface ISysSmsService {
    /**
     * @description: 注册验证码
     * @author: xingpeiyue
     * @time: 2023/9/4 16:42
     */
    boolean sendRegisterSms(String type, String phone);

    /**
     * @description: 登录验证
     * @author: xingpeiyue
     * @time: 2023/9/6 14:35
     */
    boolean sendLoginSms(String type, String phone);
}
