package com.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.mapper.BmsFollowMapper;
import com.community.model.entity.BmsFollow;
import com.community.service.IBmsFollowService;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/21 5:32 下午
 */
@Service
public class IBmsFollowServiceImpl extends ServiceImpl<BmsFollowMapper, BmsFollow> implements IBmsFollowService {
}