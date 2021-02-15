package com.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.community.model.entity.BmsTip;

public interface IBmsTipService extends IService<BmsTip> {
    BmsTip getRandomTip();
}
