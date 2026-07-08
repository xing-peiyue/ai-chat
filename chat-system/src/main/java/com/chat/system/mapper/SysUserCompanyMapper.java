package com.chat.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chat.system.domain.SysUserCompany;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：xingpeiyue
 * @date ：2024/1/15 20:57
 * @version: 1.0
 */
public interface SysUserCompanyMapper extends BaseMapper<SysUserCompany> {
    /**
     * @description: 查找在线公司客服
     * @author: xingpeiyue
     * @time: 2024/1/17 18:14
     */
    List<SysUserCompany> selectLineByCompanyId(@Param("companyId") Long companyId);
}
