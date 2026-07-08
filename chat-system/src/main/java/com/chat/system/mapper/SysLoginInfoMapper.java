package com.chat.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chat.system.domain.SysLoginInfo;

import java.util.List;

/**
 * 系统访问日志情况信息 数据层
 *
 * @author chat
 */
public interface SysLoginInfoMapper extends BaseMapper<SysLoginInfo> {
    /**
     * 新增系统登录日志
     */
    void insertLoginInfo(SysLoginInfo sysLoginInfo);

    /**
     * 查询系统登录日志集合
     *
     * @return 登录记录集合
     */
    List<SysLoginInfo> selectLoginInfoList(SysLoginInfo sysLoginInfo);

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    int deleteLoginInfoByIds(Long[] infoIds);

    /**
     * 清空系统登录日志
     *
     * @return 结果
     */
    int cleanLoginInfo();
}
