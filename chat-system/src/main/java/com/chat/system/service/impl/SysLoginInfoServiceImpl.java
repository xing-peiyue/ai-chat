package com.chat.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chat.system.domain.SysLoginInfo;
import com.chat.system.mapper.SysLoginInfoMapper;
import com.chat.system.service.ISysLoginInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author chat
 */
@Service
public class SysLoginInfoServiceImpl extends ServiceImpl<SysLoginInfoMapper, SysLoginInfo> implements ISysLoginInfoService {

    /**
     * 新增系统登录日志
     */
    @Override
    public void insertLoginInfo(SysLoginInfo sysLoginInfo) {
        baseMapper.insertLoginInfo(sysLoginInfo);
    }

    /**
     * 查询系统登录日志集合
     *
     * @return 登录记录集合
     */
    @Override
    public List<SysLoginInfo> selectLoginInfoList(SysLoginInfo sysLoginInfo) {
        return baseMapper.selectLoginInfoList(sysLoginInfo);
    }

    /**
     * 批量删除系统登录日志
     *
     * @param infoIds 需要删除的登录日志ID
     * @return 结果
     */
    @Override
    public int deleteLoginInfoByIds(Long[] infoIds) {
        return baseMapper.deleteLoginInfoByIds(infoIds);
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLoginInfo() {
        baseMapper.cleanLoginInfo();
    }
}
