package com.chat.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chat.common.constant.CacheConstants;
import com.chat.common.core.redis.RedisCache;
import com.chat.common.utils.IdWorker;
import com.chat.common.utils.SecurityUtils;
import com.chat.system.domain.SysCompany;
import com.chat.system.domain.SysUserCompany;
import com.chat.system.domain.dto.company.SaveOrUpdateCompanyDTO;
import com.chat.system.domain.dto.company.QuerySysCompanyDTO;
import com.chat.system.domain.dto.company.SysCompanyDTO;
import com.chat.system.mapper.SysCompanyMapper;
import com.chat.system.service.ISysCompanyService;
import com.chat.system.service.ISysUserCompanyService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：xingpeiyue
 * @date ：2024/1/15 11:12
 * @version: 1.0
 */
@Service
public class SysCompanyServiceImpl extends ServiceImpl<SysCompanyMapper, SysCompany> implements ISysCompanyService {
    @Lazy
    @Resource
    private ISysUserCompanyService userCompanyService;
    @Resource
    private IdWorker idWorker;

    @Resource
    private RedisCache redisCache;


    @Override
    public SysCompany getById(Serializable id) {
        SysCompany company = redisCache.getCacheObject(CacheConstants.SYS_COMPANY + id);
        if (ObjectUtil.isNotNull(company)) {
            return company;
        }
        SysCompany cp = super.getById(id);
        if (ObjectUtil.isNotNull(cp)) {
            redisCache.setCacheObject(CacheConstants.SYS_COMPANY + id, cp);
        }
        return cp;
    }

    @Override
    public boolean updateById(SysCompany entity) {
        redisCache.deleteObject(CacheConstants.SYS_COMPANY + entity.getId());
        return super.updateById(entity);
    }

    @Override
    public List<SysCompanyDTO> selectListByPage(QuerySysCompanyDTO sysCompanyDTO) {
        LambdaQueryWrapper<SysCompany> wrapper = Wrappers.lambdaQuery();
        if (!SecurityUtils.isAdminType()) {
            wrapper.eq(SysCompany::getDeptId, SecurityUtils.getDeptId());
        }
        wrapper.like(StrUtil.isNotBlank(sysCompanyDTO.getName()), SysCompany::getName, sysCompanyDTO.getName());
        wrapper.like(StrUtil.isNotBlank(sysCompanyDTO.getNickName()), SysCompany::getNickName, sysCompanyDTO.getNickName());
        wrapper.like(StrUtil.isNotBlank(sysCompanyDTO.getPhone()), SysCompany::getPhone, sysCompanyDTO.getPhone());
        wrapper.like(StrUtil.isNotBlank(sysCompanyDTO.getStatus()), SysCompany::getStatus, sysCompanyDTO.getStatus());
        List<SysCompany> list = this.list(wrapper);
        return list.stream().map(t -> {
            SysCompanyDTO build = SysCompanyDTO.builder().build();
            BeanUtil.copyProperties(t, build);
            return build;
        }).collect(Collectors.toList());
    }

    @Override
    public List<SysCompany> selectList() {
        LambdaQueryWrapper<SysCompany> wrapper = Wrappers.lambdaQuery();
        if (!SecurityUtils.isAdminType()) {
            wrapper.eq(SysCompany::getDeptId, SecurityUtils.getDeptId());
        }
        return this.list(wrapper);
    }

    @Override
    public SysCompany saveCompany(SaveOrUpdateCompanyDTO save) {
        SysCompany company = BeanUtil.copyProperties(save, SysCompany.class);
        company.setDeptId(SecurityUtils.getDeptId());
        company.setClientId(idWorker.genId());
        company.setCreateId(SecurityUtils.getUserId());
        company.setCreateTime(new Date());
        this.save(company);
        return company;
    }

    @Override
    public boolean updateCompany(SaveOrUpdateCompanyDTO update) {
        SysCompany company = this.getById(update.getId());
        BeanUtil.copyProperties(update, company);
        company.setUpdateId(SecurityUtils.getUserId()).setUpdateTime(new Date());
        return this.updateById(company);
    }

    @Override
    public List<SysCompany> unSelectCompany(String userId, Long deptId) {
        //部门所有公司
        List<SysCompany> list = this.list(Wrappers.<SysCompany>lambdaQuery().eq(SysCompany::getDeptId, deptId));
        if (CollUtil.isEmpty(list)) {
            return list;
        }
        //查询已经选中的
        List<SysUserCompany> companies = userCompanyService.list(Wrappers.<SysUserCompany>lambdaQuery().eq(SysUserCompany::getUserId, userId));
        if (ObjectUtil.isNotNull(companies)) {
            List<Long> ids = companies.stream().map(SysUserCompany::getCompanyId).collect(Collectors.toList());
            return list.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }
        return list;
    }

    @Override
    public SysCompany selectDefaultCompany(Long deptId) {
        return this.getOne(Wrappers.<SysCompany>lambdaQuery().eq(SysCompany::getDeptId, deptId).eq(SysCompany::getIsDefault, "1"));
    }

    @Override
    public SysCompany selectCacheCompany(String clientId) {
        return this.getOne(Wrappers.<SysCompany>lambdaQuery().eq(SysCompany::getClientId, clientId));
    }
}
