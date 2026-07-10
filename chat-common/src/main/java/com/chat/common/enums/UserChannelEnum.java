package com.chat.common.enums;

import java.util.stream.Stream;

/**
 * @description: 用户注册渠道
 * @author: xingpeiyue
 * @time: 2023/11/17 11:17
 */
public enum UserChannelEnum {
    /**
     * 用户渠道接入
     */
    WEB(1, "网站"),
    WECHAT(2, "公众号"),
    APPLET(3, "小程序"),
    ENTER_WECHAT(4, "企业微信"),
    ENTER_TIKTOK(5, "企业抖音"),
    APP(6, "APP接入"),
    OTHER(7, "其他");
    private int key;

    private String value;

    UserChannelEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public static String getValue(int key) {
        return Stream.of(UserChannelEnum.values()).filter(t -> t.key == key).findFirst().orElse(OTHER).value;
    }

    public static int getKey(String value) {
        return Stream.of(UserChannelEnum.values()).filter(t -> t.value.equalsIgnoreCase(value)).findFirst().orElse(OTHER).key;
    }
}
