package com.community.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.community.model.entity.BmsTopicTag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/18 11:33 下午
 */
@Repository
public interface BmsTopicTagMapper extends BaseMapper<BmsTopicTag> {
    /**
     * 根据标签获取话题ID集合
     *
     * @param id
     * @return
     */
    Set<String> getTopicIdsByTagId(@Param("id") String id);
}