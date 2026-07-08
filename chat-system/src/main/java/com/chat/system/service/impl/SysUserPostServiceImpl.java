package com.chat.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chat.system.domain.SysUserPost;
import com.chat.system.mapper.SysUserPostMapper;
import com.chat.system.service.ISysUserPostService;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;

/**
 * @program: chat
 * @description:
 * @author: peiyue.xing
 * @create: 2023-12-12 23:57
 */
@Service
public class SysUserPostServiceImpl extends ServiceImpl<SysUserPostMapper, SysUserPost> implements ISysUserPostService {
}
