package com.chat.common.enums;

import cn.hutool.core.util.StrUtil;

import java.util.stream.Stream;

/**
 * @author ：xingpeiyue
 * @date ：2021/6/4 13:52
 * @description：通道类型
 * @version: 2.0
 */
public enum ChannelTypeEnum {
    /*电信*/
    WEB("1", "网页"),
    /*移动*/
    WECHAT_PUB("2", "微信公众号"),
    /*联通*/
    WECHAT_PROGRAM("3", "微信小程序"),
    WECHAT_ENTER("4", "企业微信"),
    WECHAT_TIKTOK("5", "企业抖音"),
    WECHAT_YOUZAN("6", "有赞");
    private String key;
    private String value;

    ChannelTypeEnum(String key, String value) {
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
        return Stream.of(ChannelTypeEnum.values()).filter(t -> StrUtil.equals(t.key, key)).findFirst().orElseThrow(() -> new RuntimeException(String.format("状态未找到value值:%d", key))).value;
    }

    public static String getKey(String value) {
        return Stream.of(ChannelTypeEnum.values()).filter(t -> t.value.equalsIgnoreCase(value)).findFirst().orElseThrow(() -> new RuntimeException(String.format("状态未找到key值:%s", value))).key;
    }
}
