package com.chat.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chat.system.domain.SysUserCompany;

import java.util.List;

/**
 * @author ：xingpeiyue
 * @date ：2024/1/15 20:58
 * @version: 1.0
 */
public interface ISysUserCompanyService extends IService<SysUserCompany> {
    /**
     * @description: 查找在线接入公司客服
     * @author: xingpeiyue
     * @time: 2024/1/17 18:08
     */
    List<SysUserCompany> selectLineByCompanyId(Long companyId);

    /**
     * @description: 查找用户接入的公司
     * @author: xingpeiyue
     * @time: 2024/1/18 14:48
     */
    List<Long> selectCompanyByUserId(String userId);

    /**
     * @description: 查找客服对外昵称
     * @author: xingpeiyue
     * @time: 2024/1/18 16:16
     */
    SysUserCompany selectCompanyByUserAndCompanyId(String userId, Long companyId);

    /**
     * @description: 坐席到期数
     * @author: xingpeiyue
     * @time: 2024/3/1 20:38
     */
    long selectSeatExpireCount();
}
