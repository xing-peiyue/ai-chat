package com.chat.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.List;

/**
 * sessionId  id::talkId
 *
 * @author ：xingpeiyue
 * @date ：2023/8/22 15:58
 * @version: 1.0
 */
public class ChatTalkUtils {
    public static final String TALK_SPLIT = "::";
    //机器人标识
    public static final Long ROBOT = 1000L;

    private ChatTalkUtils() {
    }

    /**
     * @description: 获取对话talkId
     * @author: xingpeiyue
     * @time: 2023/9/18 10:42
     */
    public static String getTalkId(String sessionId) {
        if (StrUtil.contains(sessionId, TALK_SPLIT)) {
            List<String> list = StrUtil.split(sessionId, TALK_SPLIT);
            return list.get(1);
        }
        return null;
    }

    /**
     * @description: 获取关联的ID
     * @author: xingpeiyue
     * @time: 2023/9/18 10:42
     */
    public static String getUserVisitorTalkId(String sessionId) {
        if (StrUtil.contains(sessionId, TALK_SPLIT)) {
            List<String> list = StrUtil.split(sessionId, TALK_SPLIT);
            return list.get(0);
        }
        return null;
    }
}
