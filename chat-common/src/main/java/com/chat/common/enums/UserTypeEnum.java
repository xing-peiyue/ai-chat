package com.chat.common.enums;


import java.util.stream.Stream;

/**
 * 用户类型枚举
 *
 * @author: zk
 * @time: 2023/11/10
 */
public enum UserTypeEnum {
    ADMIN("00", "系统用户"),
    AGENT("01", "代理商"),
    ORDINARY("02", "普通用户"),
    CUSTOMER("03", "客服"),
    ROBOT("04", "机器人"),
    OTHER("10", "其他");

    private String key;

    private String value;

    UserTypeEnum(String key, String value) {
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
        return Stream.of(UserTypeEnum.values()).filter(t -> t.key.equals(key)).findFirst().orElse(OTHER).value;
    }

    public static String getKey(String value) {
        return Stream.of(UserTypeEnum.values()).filter(t -> t.value.equalsIgnoreCase(value)).findFirst().orElse(OTHER).key;
    }
}
