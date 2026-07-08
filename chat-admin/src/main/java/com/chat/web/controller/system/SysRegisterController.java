package com.chat.web.controller.system;

import com.chat.common.core.controller.BaseController;
import com.chat.common.core.domain.AjaxResult;
import com.chat.common.core.domain.entity.SysUser;
import com.chat.common.core.domain.model.RegisterBody;
import com.chat.common.exception.ServiceException;
import com.chat.framework.web.service.SysRegisterService;
import com.chat.message.service.IChannelWebsiteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.Resource;

/**
 * 注册验证
 *
 * @author chat
 */
@Slf4j
@RestController
public class SysRegisterController extends BaseController {
    @Resource
    private SysRegisterService registerService;
    @Resource
    private IChannelWebsiteService channelWebsiteService;

    /**
     * @description: 注册成为代理商
     * @author: xingpeiyue
     * @time: 2023/12/15 9:50
     */
    @PostMapping("/register")
    public AjaxResult register(@RequestBody @Validated RegisterBody body) {
        try {
            SysUser register = registerService.register(body);
            channelWebsiteService.initChannelWebsite(register.getDeptId());
            return success();
        } catch (ServiceException e) {
            return error(e.getMessage());
        } catch (Exception e) {
            log.error("注册异常", e);
            return error("注册失败");
        }
    }
}
