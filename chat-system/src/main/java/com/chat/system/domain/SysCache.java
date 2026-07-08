package com.chat.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.chat.common.utils.StringUtils;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * 缓存信息
 *
 * @author chat
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
@ToString
public class SysCache implements Serializable {
    private static final long serialVersionUID = -6753573916669337186L;
    /**
     * 缓存名称
     */
    private String cacheName = "";

    /**
     * 缓存键名
     */
    private String cacheKey = "";

    /**
     * 缓存内容
     */
    private Object cacheValue = "";
    /**
     * 备注
     */
    private String remark = "";

    public SysCache(String cacheName, String remark) {
        this.cacheName = cacheName;
        this.remark = remark;
    }

    public SysCache(String cacheName, String cacheKey, Object cacheValue) {
        this.cacheName = StringUtils.replace(cacheName, ":", "");
        this.cacheKey = StringUtils.replace(cacheKey, cacheName, "");
        this.cacheValue = cacheValue;
    }

}
