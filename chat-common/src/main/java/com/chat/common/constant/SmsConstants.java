package com.chat.common.constant;

/**
 * 短信常量
 *
 * @author ：xingpeiyue
 * @date ：2023/9/5 9:51
 * @version: 1.0
 */
public class SmsConstants {
    /**
     * 接口请求地址
     */
    public static final String SMS_HTTP_URL = "https://sms.yunpian.com/v2/sms/single_send.json";

    public static final String SMS_REGISTER = "sms:register";

    public static final String SMS_LOGIN = "sms:login";

    //【云领客服】尊敬的用户：您的注册校验码：{code}，工作人员不会索取，请勿泄漏。
    public static final String SMS_REGISTER_MESSAGE = "【云领客服】校验码{code}，您正在申请注册云领客服帐号，需要进行校验。请勿泄漏您的校验码。";
    //【云领客服】尊敬的用户：您的登录校验码：{code}，工作人员不会索取，请勿泄漏。
    public static final String SMS_LOGIN_MESSAGE = "【云领客服】您正在进行短信登录，验证码{code}，切勿将验证码泄露于他人，本条验证码有效期15分钟。";
}
