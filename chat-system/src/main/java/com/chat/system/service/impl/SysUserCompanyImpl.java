package com.chat.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chat.common.constant.CacheConstants;
import com.chat.common.core.redis.RedisCache;
import com.chat.common.utils.SecurityUtils;
import com.chat.system.domain.SysCompany;
import com.chat.system.domain.SysUserCompany;
import com.chat.system.mapper.SysUserCompanyMapper;
import com.chat.system.service.ISysCompanyService;
import com.chat.system.service.ISysUserCompanyService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：xingpeiyue
 * @date ：2024/1/15 20:58
 * @version: 1.0
 */
@Service
public class SysUserCompanyImpl extends ServiceImpl<SysUserCompanyMapper, SysUserCompany> implements ISysUserCompanyService {
    @Resource
    private RedisCache redisCache;
    @Resource
    private ISysCompanyService companyService;

    @Override
    public List<SysUserCompany> selectLineByCompanyId(Long companyId) {
        return this.baseMapper.selectLineByCompanyId(companyId);
    }

    @Override
    public List<Long> selectCompanyByUserId(String userId) {
        List<SysUserCompany> companies = this.list(Wrappers.<SysUserCompany>lambdaQuery().eq(SysUserCompany::getUserId, userId));
        if (CollUtil.isNotEmpty(companies)) {
            return companies.stream().map(SysUserCompany::getCompanyId).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public SysUserCompany selectCompanyByUserAndCompanyId(String userId, Long companyId) {
        SysUserCompany sysUserCompany = redisCache.getCacheObject(CacheConstants.SYS_USER_COMPANY + userId + ":" + companyId);
        if (ObjectUtil.isNull(sysUserCompany)) {
            SysUserCompany userCompany = this.getOne(Wrappers.<SysUserCompany>lambdaQuery()
                    .eq(SysUserCompany::getUserId, userId).eq(SysUserCompany::getCompanyId, companyId)
                    .last("limit 1"));
            if (ObjectUtil.isNotNull(userCompany)) {
                redisCache.setCacheObject(CacheConstants.SYS_USER_COMPANY + userId + ":" + companyId, userCompany);
                return userCompany;
            }
        }
        return ObjectUtil.isNotNull(sysUserCompany) ? sysUserCompany : SysUserCompany.builder().build();
    }

    @Override
    public long selectSeatExpireCount() {
        List<Long> companyIds = new ArrayList<>();
        if (!SecurityUtils.isAdminType()) {
            List<SysCompany> list = companyService.list(Wrappers.<SysCompany>lambdaQuery().eq(SysCompany::getDeptId, SecurityUtils.getDeptId()));
            List<Long> ids = list.stream().map(SysCompany::getId).collect(Collectors.toList());
            companyIds.addAll(ids);
        }
        return this.count(Wrappers.<SysUserCompany>lambdaQuery().in(CollUtil.isNotEmpty(companyIds), SysUserCompany::getCompanyId, companyIds));
    }
}
