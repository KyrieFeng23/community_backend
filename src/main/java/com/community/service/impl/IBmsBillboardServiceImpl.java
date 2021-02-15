package com.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.mapper.BmsBillboardMapper;
import com.community.model.entity.BmsBillboard;
import com.community.service.IBmsBillboardService;
import org.springframework.stereotype.Service;

@Service
public class IBmsBillboardServiceImpl extends ServiceImpl<BmsBillboardMapper,BmsBillboard>
implements IBmsBillboardService
{

}
