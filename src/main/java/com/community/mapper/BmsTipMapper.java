package com.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.model.entity.BmsTip;
import org.springframework.stereotype.Repository;

@Repository
public interface BmsTipMapper extends BaseMapper<BmsTip> {   //mybatis-plus的工具，就不用再在xml里写insert等标签了
    BmsTip getRandomTip();
}
