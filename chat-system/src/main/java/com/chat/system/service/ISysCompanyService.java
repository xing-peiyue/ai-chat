package com.chat.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.chat.system.domain.SysCompany;
import com.chat.system.domain.dto.company.SaveOrUpdateCompanyDTO;
import com.chat.system.domain.dto.company.QuerySysCompanyDTO;
import com.chat.system.domain.dto.company.SysCompanyDTO;

import java.util.List;

/**
 * @author ：xingpeiyue
 * @date ：2024/1/15 11:11
 * @version: 1.0
 */
public interface ISysCompanyService extends IService<SysCompany> {

    /**
     * @description: 公司列表
     * @author: zk
     * @time: 2024/1/24 11:18
     */
    List<SysCompanyDTO> selectListByPage(QuerySysCompanyDTO sysCompanyDTO);

    /**
     * @description: 公司列表
     * @author: xingpeiyue
     * @time: 2024/1/15 11:18
     */
    List<SysCompany> selectList();

    /**
     * @description: 公司保存
     * @author: xingpeiyue
     * @time: 2024/1/15 12:04
     */
    SysCompany saveCompany(SaveOrUpdateCompanyDTO save);

    /**
     * @description: 公司修改
     * @author: xingpeiyue
     * @time: 2024/1/15 12:05
     */
    boolean updateCompany(SaveOrUpdateCompanyDTO save);

    /**
     * @description:查询未分配公司
     * @author: xingpeiyue
     * @time: 2024/1/15 23:10
     */
    List<SysCompany> unSelectCompany(String userId, Long deptId);

    /**
     * @description: 查询默认公司
     * @author: xingpeiyue
     * @time: 2024/1/17 15:07
     */
    SysCompany selectDefaultCompany(Long deptId);

    /**
     * @description: 查找公司
     * @author: xingpeiyue
     * @time: 2024/1/17 16:35
     */
    SysCompany selectCacheCompany(String clientId);
}
