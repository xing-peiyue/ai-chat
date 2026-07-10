package com.chat.common.enums;

import cn.hutool.core.util.StrUtil;

import java.util.stream.Stream;

/**
 * @author ：xingpeiyue
 * @date ：2021/6/4 13:52
 * @description：通道类型
 * @version: 2.0
 */
public enum ChannelValueEnum {
    /*电信*/
    WEB("1", "web"),
    /*移动*/
    WECHAT_PUB("2", "wechat_pub"),
    /*联通*/
    WECHAT_PROGRAM("3", "wechat_program"),
    WECHAT_ENTER("4", "wechat_enterprise"),
    WECHAT_TIKTOK("5", "tiktok");
    private String key;
    private String value;

    ChannelValueEnum(String key, String value) {
        this.key = key;
        this.value = value;

    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValue(String key) {
        return Stream.of(ChannelValueEnum.values()).filter(t -> StrUtil.equals(t.key, key)).findFirst().orElseThrow(() -> new RuntimeException(String.format("状态未找到value值:%d", key))).value;
    }

    public static String getKey(String value) {
        return Stream.of(ChannelValueEnum.values()).filter(t -> t.value.equalsIgnoreCase(value)).findFirst().orElseThrow(() -> new RuntimeException(String.format("状态未找到key值:%s", value))).key;
    }
}
