package com.chat.common.utils;

import cn.hutool.core.date.DateUtil;
import com.chat.common.core.redis.RedisCache;
import com.chat.common.utils.spring.SpringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import jakarta.annotation.Resource;
import java.util.Date;

/**
 * @author ：xingpeiyue
 * @date ：2022/5/24 16:45
 * @description： redis生成订单
 * @version: 2.0
 */
@Component
public class OrderRedisUtil {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    //订单缓存key
    private static final String ORDER_KEY = "cms:order_key";

    public String createOrderNum(String head) {
        return head + generateId();
    }

    public String generateId() {
        RedisAtomicLong counter = new RedisAtomicLong(ORDER_KEY, redisTemplate.getConnectionFactory());
        Long incr = counter.getAndIncrement();
        if (!redisTemplate.hasKey(ORDER_KEY)) {
            counter.expireAt(getExpireAtData());
            /**这里取第二次 incr 就是从1开始了,默认从0开始*/
            incr = counter.getAndIncrement();
        }
        String format = DateUtil.format(new Date(), "yyyyMMddHHmm");
        return format + getUniqueId(incr);
    }

    /**
     * 每天23:59:59点过期
     */
    private static Date getExpireAtData() {
        Date date = new Date();
        DateUtil.endOfDay(date);
        return date;
    }

    private static String getUniqueId(long redisId) {
        StringBuffer sb = new StringBuffer();
        int maxLen = 6;
        int len = String.valueOf(redisId).length();
        if (len < maxLen) {
            for (int i = 0; i < maxLen - len; i++) {
                sb.append("0");
            }
        }
        sb.append(redisId);
        return sb.toString();
    }
}
