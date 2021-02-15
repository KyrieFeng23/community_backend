package com.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.model.entity.BmsBillboard;
import org.springframework.stereotype.Repository;

@Repository
public interface BmsBillboardMapper extends BaseMapper<BmsBillboard> {   //mybatis-plus的工具，就不用再在xml里写insert等标签了

}
