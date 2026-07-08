package com.chat.web.controller.system;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.chat.common.core.controller.BaseController;
import com.chat.common.core.domain.AjaxResult;
import com.chat.common.core.domain.entity.SysUser;
import com.chat.common.core.page.TableDataInfo;
import com.chat.message.domain.channel.ChannelInfo;
import com.chat.message.service.IChannelInfoService;
import com.chat.message.service.IChannelService;
import com.chat.message.service.IChannelWebsiteService;
import com.chat.system.domain.SysCompany;
import com.chat.system.domain.SysUserCompany;
import com.chat.system.domain.dto.company.SaveOrUpdateCompanyDTO;
import com.chat.system.domain.dto.company.QuerySysCompanyDTO;
import com.chat.system.domain.dto.company.SysCompanyDTO;
import com.chat.system.service.ISysCompanyService;
import com.chat.system.service.ISysUserCompanyService;
import com.chat.system.service.ISysUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 公司信息
 *
 * @author chat
 */
@RestController
@RequestMapping("/system/company")
public class SysCompanyController extends BaseController {
    @Resource
    private ISysCompanyService companyService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IChannelWebsiteService channelWebsiteService;
    @Resource
    private IChannelService channelService;
    @Resource
    private ISysUserCompanyService sysUserCompanyService;
    @Resource
    private IChannelInfoService channelInfoService;

    /**
     * @description: 获取公司列表
     * @author: xingpeiyue
     * @time: 2024/1/15 11:15
     */
    @GetMapping("/list")
    public TableDataInfo list(QuerySysCompanyDTO sysCompanyDTO) {
        startPage();
        List<SysCompanyDTO> companyList = companyService.selectListByPage(sysCompanyDTO);
        companyList.forEach(t -> {
            long count = sysUserCompanyService.count(Wrappers.<SysUserCompany>lambdaQuery().eq(SysUserCompany::getCompanyId, t.getId()));
            t.setSeatCount(count);
            List<ChannelInfo> infos = channelInfoService.list(Wrappers.<ChannelInfo>lambdaQuery().eq(ChannelInfo::getCompanyId, t.getId()));
            t.setChannelCount(infos.size());
            Set<Long> types = infos.stream().map(ChannelInfo::getChannelType).collect(Collectors.toSet());
            t.setChannelTypes(types);
        });
        return getDataTable(companyList);
    }

    /**
     * @description: 查询公司
     * @author: xingpeiyue
     * @time: 2024/1/15 14:21
     */
    @GetMapping("/listAll")
    public AjaxResult companyList() {
        List<SysCompany> companyList = companyService.selectList();
        return AjaxResult.success(companyList);
    }

    /**
     * @description: 获取公司信息
     * @author: xingpeiyue
     * @time: 2024/1/15 11:53
     */
    @GetMapping("/getCompany")
    public AjaxResult getCompany(@RequestParam("id") Long id) {
        SysCompany company = companyService.getById(id);
        return AjaxResult.success(company);
    }

    /**
     * @description: 公司保存
     * @author: xingpeiyue
     * @time: 2024/1/15 11:55
     */
    @PostMapping("/saveCompany")
    public AjaxResult saveCompany(@RequestBody @Validated SaveOrUpdateCompanyDTO save) {
        SysCompany company = companyService.saveCompany(save);
        //生成通道
        if (ObjectUtil.isNotNull(company.getId())) {
            channelWebsiteService.initChannelWebsiteCompanyId(company.getId());
        }
        return AjaxResult.success();
    }

    /**
     * @description: 修改
     * @author: xingpeiyue
     * @time: 2024/1/15 12:07
     */
    @PostMapping("/updateCompany")
    public AjaxResult updateCompany(@RequestBody @Validated SaveOrUpdateCompanyDTO update) {
        companyService.updateCompany(update);
        return AjaxResult.success();
    }


    /**
     * @description: 删除公司
     * @author: xingpeiyue
     * @time: 2024/1/15 13:39
     */
    @GetMapping("/delCompany")
    public AjaxResult delCompany(@RequestParam("id") Long id) {
        boolean allocatesCompany = channelService.channelAllocatesCompany(id);
        if (allocatesCompany) {
            return AjaxResult.error("公司已分配通道，请先删除通道");
        }
        //是否分配客服
        long count = sysUserCompanyService.count(Wrappers.<SysUserCompany>lambdaQuery().eq(SysUserCompany::getCompanyId, id));
        if (count > 0) {
            return AjaxResult.error("公司已分配席位，请先删除客服席位");
        }
        SysCompany build = SysCompany.builder().status("0").id(id).build();
        companyService.updateById(build);
        return AjaxResult.success();
    }

    /**
     * @description: 查询用户未选中的公司
     * @author: xingpeiyue
     * @time: 2024/1/15 23:02
     */
    @GetMapping("/unSelectCompany")
    public AjaxResult unSelectCompany(@RequestParam("userId") String userId) {
        SysUser sysUser = sysUserService.userInfo(userId);
        if (ObjectUtil.isNull(sysUser)) {
            return AjaxResult.success(new ArrayList<>());
        }
        List<SysCompany> companyList = companyService.unSelectCompany(userId, sysUser.getDeptId());
        return AjaxResult.success(companyList);
    }

}
