package com.chat.common.enums;

import cn.hutool.core.util.StrUtil;

import java.util.stream.Stream;

/**
 * @author ：xingpeiyue
 * @date ：2021/6/4 13:52
 * @description：套餐类型
 * @version: 2.0
 */
public enum ProductTypeEnum {
    /*电信*/
    MONTH("1", "月套餐"),
    /*移动*/
    YEAR("2", "年套餐"),
    /*联通*/
    TOTAL("3", "累计套餐");
    private String key;
    private String value;

    ProductTypeEnum(String key, String value) {
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
        return Stream.of(ProductTypeEnum.values()).filter(t -> StrUtil.equals(t.key, key)).findFirst().orElseThrow(() -> new RuntimeException(String.format("状态未找到value值:%d", key))).value;
    }

    public static String getKey(String value) {
        return Stream.of(ProductTypeEnum.values()).filter(t -> t.value.equalsIgnoreCase(value)).findFirst().orElseThrow(() -> new RuntimeException(String.format("状态未找到key值:%s", value))).key;
    }
}
