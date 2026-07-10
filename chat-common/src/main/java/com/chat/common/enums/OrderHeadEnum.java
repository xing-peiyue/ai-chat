package com.chat.common.enums;


import java.util.stream.Stream;

/**
 * 订单头枚举
 *
 * @author: zk
 * @time: 2023/11/10
 */
public enum OrderHeadEnum {
    ADMIN("1", "BLP"),
    AGENT("2", "WXP"),
    ORDINARY("3", "ALP"),
    CUSTOMER("4", "XTP"),
    OTHER("10", "OTP");

    private String key;

    private String value;

    OrderHeadEnum(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }

    public static String getValue(String key) {
        return Stream.of(OrderHeadEnum.values()).filter(t -> t.key.equals(key)).findFirst().orElse(OTHER).value;
    }

    public static String getKey(String value) {
        return Stream.of(OrderHeadEnum.values()).filter(t -> t.value.equalsIgnoreCase(value)).findFirst().orElse(OTHER).key;
    }
}
