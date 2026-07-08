package com.chat.system.service.impl;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.chat.common.constant.HttpStatus;
import com.chat.common.constant.SmsConstants;
import com.chat.common.core.domain.AjaxResult;
import com.chat.common.core.redis.RedisCache;
import com.chat.common.core.text.Convert;
import com.chat.system.domain.SysSmsRecord;
import com.chat.system.service.ISysSmsRecordService;
import com.chat.system.service.ISysSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author ：xingpeiyue
 * @date ：2023/9/4 16:24
 * @version: 1.0
 */
@Slf4j
@Service
public class SysSmsServiceImpl implements ISysSmsService {
    public static final String BASE_NUMBER = "012356789";
    @Value("${sms.appKey}")
    private String appKey;
    @Resource
    private RedisCache redisCache;
    @Resource
    private ISysSmsRecordService sysSmsRecordService;

    /**
     * 格式：{"msg":"发送成功","code":0,"fee":0.05,"count":1,"mobile":"13692137675","sid":76815478050,"unit":"RMB"}
     * 错误： {"msg":"请求参数格式错误","code":2,"http_status_code":400,"detail":"验证码类模板#code#值长度必须1-16个字，且只能是字母和数字"}
     *
     * @description: 短息发送
     * @author: xingpeiyue
     * @time: 2023/9/4 16:44
     */
    private AjaxResult send(String text, String phone) {
        try {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("apikey", appKey);
            map.put("mobile", phone);
            map.put("text", text);
            log.info("短信请求参数：{}", map);
            String body = HttpRequest.post(SmsConstants.SMS_HTTP_URL)
                    .header(Header.ACCEPT, ContentType.JSON.getValue())
                    .header(Header.CONTENT_TYPE, ContentType.FORM_URLENCODED.getValue())
                    .form(map)
                    .execute().body();
            JSONObject object = JSONUtil.parseObj(body);
            log.info("验证码请求返回参数:{}", object);
            Integer code = object.getInt("code");
            String msg = object.getStr("msg");
            if (code == 0) {
                SysSmsRecord smsVo = JSONUtil.toBean(object, SysSmsRecord.class);
                smsVo.setText(text).setPhone(phone);
                return AjaxResult.success(smsVo);
            }
            return AjaxResult.error(HttpStatus.ERROR, msg);
        } catch (Exception e) {
            log.error("短信请求数据异常：", e);
            return AjaxResult.error(HttpStatus.ERROR, "短信请求异常");
        }
    }

    /**
     * @description:
     * @author: xingpeiyue
     * @time: 2023/9/4 19:36
     * @return:
     */
    @Override
    public boolean sendRegisterSms(String type, String phone) {
        String smsText = this.genRegisterSmsText(phone);
        AjaxResult result = this.send(smsText, phone);
        return this.saveSmsRecord(phone, type, smsText, result);
    }

    /**
     * @description: 登录短信
     * @author: xingpeiyue
     * @time: 2023/9/6 14:38
     */
    @Override
    public boolean sendLoginSms(String type, String phone) {
        String smsText = this.genLoginSmsText(phone);
        AjaxResult result = this.send(smsText, phone);
        return this.saveSmsRecord(phone, type, smsText, result);
    }

    private String genRegisterSmsText(String phone) {
        String num = RandomUtil.randomString(BASE_NUMBER, 4);
        String codeKey = SmsConstants.SMS_REGISTER + ":" + phone;
        //5分钟
        redisCache.setCacheObject(codeKey, num, 5 * 60, TimeUnit.MINUTES);
        Map<Object, Object> map = MapUtil.builder().put("code", num).build();
        return StrUtil.format(SmsConstants.SMS_REGISTER_MESSAGE, map);
    }

    private String genLoginSmsText(String phone) {
        String num = RandomUtil.randomString(BASE_NUMBER, 4);
        String codeKey = SmsConstants.SMS_LOGIN + ":" + phone;
        //15分钟
        redisCache.setCacheObject(codeKey, num, 15 * 60, TimeUnit.MINUTES);
        Map<Object, Object> map = MapUtil.builder().put("code", num).build();
        return StrUtil.format(SmsConstants.SMS_LOGIN_MESSAGE, map);
    }

    private boolean saveSmsRecord(String phone, String type, String smsText, AjaxResult result) {
        if (result.isSuccess()) {
            SysSmsRecord sysSmsRecord = (SysSmsRecord) result.get(AjaxResult.DATA_TAG);
            sysSmsRecord.setType(type).setStatus("1").setCreateTime(new Date()).setUpdateTime(new Date());
            sysSmsRecordService.save(sysSmsRecord);
            return true;
        }
        SysSmsRecord build = SysSmsRecord.builder().phone(phone).text(smsText).status("0").type(type)
                .remark(Convert.toStr(result.get(AjaxResult.MSG_TAG), "短信下发异常"))
                .createTime(new Date()).updateTime(new Date())
                .build();
        sysSmsRecordService.save(build);
        return false;
    }
}
