package com.chat.system.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chat.common.constant.UserConstants;
import com.chat.common.exception.ServiceException;
import com.chat.system.domain.SysPost;
import com.chat.system.domain.SysUserPost;
import com.chat.system.mapper.SysPostMapper;
import com.chat.system.mapper.SysUserPostMapper;
import com.chat.system.service.ISysPostService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 岗位信息 服务层处理
 *
 * @author chat
 */
@Service
public class SysPostServiceImpl extends ServiceImpl<SysPostMapper, SysPost> implements ISysPostService {
    @Resource
    private SysUserPostMapper userPostMapper;

    /**
     * 查询岗位信息集合
     *
     * @param post 岗位信息
     * @return 岗位信息集合
     */
    @Override
    public List<SysPost> selectPostList(SysPost post) {
        LambdaQueryWrapper<SysPost> query = Wrappers.<SysPost>lambdaQuery()
                .like(StrUtil.isNotBlank(post.getPostCode()), SysPost::getPostCode, post.getPostCode())
                .eq(StrUtil.isNotBlank(post.getStatus()), SysPost::getStatus, post.getStatus())
                .like(StrUtil.isNotBlank(post.getPostName()), SysPost::getPostName, post.getPostName());
        return this.list(query);
    }

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    @Override
    public List<SysPost> selectPostAll() {
        return this.list();
    }

    /**
     * 通过岗位ID查询岗位信息
     *
     * @param postId 岗位ID
     * @return 角色对象信息
     */
    @Override
    public SysPost selectPostById(Long postId) {
        return this.getById(postId);
    }

    /**
     * 根据用户ID获取岗位选择框列表
     *
     * @param userId 用户ID
     * @return 选中岗位ID列表
     */
    @Override
    public List<Long> selectPostListByUserId(String userId) {
        return this.baseMapper.selectPostListByUserId(userId);
    }

    /**
     * 校验岗位名称是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostNameUnique(SysPost post) {
        Long postId = ObjectUtil.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost sysPost = this.getOne(Wrappers.<SysPost>lambdaQuery().eq(SysPost::getPostName, post.getPostName()).last("limit 1"));
        if (ObjectUtil.isNotNull(sysPost) && sysPost.getPostId().longValue() != postId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 校验岗位编码是否唯一
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public String checkPostCodeUnique(SysPost post) {
        Long postId = ObjectUtil.isNull(post.getPostId()) ? -1L : post.getPostId();
        SysPost sysPost = this.getOne(Wrappers.<SysPost>lambdaQuery().eq(SysPost::getPostCode, post.getPostCode()).last("limit 1"));
        if (ObjectUtil.isNotNull(sysPost) && sysPost.getPostId().longValue() != postId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 通过岗位ID查询岗位使用数量
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public long countUserPostById(Long postId) {
        return userPostMapper.selectCount(Wrappers.<SysUserPost>lambdaQuery().eq(SysUserPost::getPostId, postId));
    }

    /**
     * 删除岗位信息
     *
     * @param postId 岗位ID
     * @return 结果
     */
    @Override
    public boolean deletePostById(Long postId) {
        return this.removeById(postId);
    }

    /**
     * 批量删除岗位信息
     *
     * @param postIds 需要删除的岗位ID
     * @return 结果
     */
    @Override
    public boolean deletePostByIds(Long[] postIds) {
        SysUserPost sysUserPost = userPostMapper.selectOne(Wrappers.<SysUserPost>lambdaQuery()
                .in(SysUserPost::getPostId, postIds)
                .last("limit 1"));
        if (ObjectUtil.isNotNull(sysUserPost)) {
            SysPost post = selectPostById(sysUserPost.getPostId());
            throw new ServiceException(String.format("%1$s已分配,不能删除", post.getPostName()));
        }
        return this.removeBatchByIds(CollUtil.newArrayList(postIds));
    }

    /**
     * 新增保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean insertPost(SysPost post) {
        post.setCreateTime(new Date());
        return this.save(post);
    }

    /**
     * 修改保存岗位信息
     *
     * @param post 岗位信息
     * @return 结果
     */
    @Override
    public boolean updatePost(SysPost post) {
        post.setUpdateTime(new Date());
        return this.updateById(post);
    }
}
