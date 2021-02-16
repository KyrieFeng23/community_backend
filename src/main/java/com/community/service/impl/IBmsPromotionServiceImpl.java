package com.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.mapper.BmsPromotionMapper;
import com.community.model.entity.BmsPromotion;
import com.community.service.IBmsPromotionService;
import org.springframework.stereotype.Service;

@Service
public class IBmsPromotionServiceImpl extends ServiceImpl<BmsPromotionMapper, BmsPromotion>
implements IBmsPromotionService
{

}
