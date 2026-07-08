package com.chat.web.controller.system;

import com.chat.common.config.ChatConfig;
import com.chat.common.utils.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 首页
 *
 * @author chat
 */
@RestController
public class SysIndexController {
    /**
     * 系统基础配置
     */
    @Resource
    private ChatConfig chatConfig;

    /**
     * 访问首页，提示语
     */
    @GetMapping("/")
    public String index() {
        return StringUtils.format("欢迎使用{}后台管理框架，当前版本：v{}，请通过前端地址访问。", chatConfig.getName(), chatConfig.getVersion());
    }
}
