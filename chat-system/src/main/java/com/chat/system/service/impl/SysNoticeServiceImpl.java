package com.chat.system.service.impl;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.chat.system.domain.SysNotice;
import com.chat.system.mapper.SysNoticeMapper;
import com.chat.system.service.ISysNoticeService;

import jakarta.annotation.Resource;

/**
 * 公告 服务层实现
 *
 * @author chat
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements ISysNoticeService {
    @Resource
    private SysNoticeMapper noticeMapper;

    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId) {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice) {
        return noticeMapper.selectNoticeList(notice);
    }

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public boolean insertNotice(SysNotice notice) {
        notice.setCreateTime(new Date());
        return this.save(notice);
    }

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public boolean updateNotice(SysNotice notice) {
        notice.setUpdateTime(new Date());
        return this.updateById(notice);
    }

    /**
     * 删除公告对象
     *
     * @param noticeId 公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeById(Long noticeId) {
        return noticeMapper.deleteNoticeById(noticeId);
    }

    /**
     * 批量删除公告信息
     *
     * @param noticeIds 需要删除的公告ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(Long[] noticeIds) {
        return noticeMapper.deleteNoticeByIds(noticeIds);
    }
}
