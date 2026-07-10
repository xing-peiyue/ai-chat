package com.chat.common.constant;

/**
 * 缓存的key 常量
 *
 * @author xpy
 */
public class CacheConstants {
    /**
     * 前缀
     */
    public static final String CHAT_PREFIX = "chat:";
    /**
     * 部门 缓存
     */
    public static final String SYS_DEPT_KEY = "chat:sys_dept:";

    /**
     * 访客 缓存
     */
    public static final String CHAT_VISITOR_KEY = "chat:chat_visitor:";

    /**
     * 最后一条发送的信息
     */
    public static final String CHAT_TALK_KEY = "chat:chat_talk_message:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "chat:sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "chat:sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 用户id自增
     */
    public static final String USER_ID_INCREMENT = "chat:user_id_inc";

    /**
     * 客服用户关系建立缓存
     */
    public static final String VISITOR_USER_TALK = "chat:user_visitor_talk:";
    /**
     * 用户数据缓存
     */
    public static final String SYS_USER_KEY = "chat:sys_user:";

    /**
     * 用户模板数据缓存
     */
    public static final String CHAT_TEMPLATE_PROMPT_KEY = "chat:template_prompt:";

    /**
     * 存储公司
     */
    public static final String SYS_COMPANY = "chat:sys_company:";


    /**
     * 客服关联公司
     */
    public static final String SYS_USER_COMPANY = "chat:sys_user_company:";
    /**
     * 用户未读数据存储
     */
    public static final String CHAT_USER_UNREAD = "chat:chat_user_unread:";

    /**
     * 用户发送提示语言的缓存
     */
    public static final String CHAT_VISITOR_TEMPLATE_PROMPT = "chat:visitor_prompt:";

    /**
     * 微信开放平台
     */
    public static final String CHAT_CHANNEL_WX_OPEN = "chat:channel:weChat";

    /**
     * 企业微信平台
     */
    public static final String CHAT_CHANNEL_WX_CP = "chat:channel:wxcp";

    /**
     * 抖音开放平台
     */
    public static final String CHAT_CHANNEL_TIKTOK_OPEN = "chat:channel:tiktok";

    public static final String CHAT_CHANNEL_WX = "chat:channel_wx:";

    public static final String CHAT_CHANNEL_WEBSITE = "chat:channel_website:";

    public static final String CHAT_CHANNEL_TIKTOK = "chat:channel_tiktok:";

    public static final String CHAT_CHANNEL_WXCP = "chat:channel_wxcp:";

    /**
     * 用户监控
     */
    public static final String CHAT_USER_MONITORING = "chat:user_monitoring:";


    public static final String CHAT_USER_KEYWORD = "chat:user_keyword:";


}
