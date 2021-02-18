package com.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.community.model.entity.BmsTopicTag;

import java.util.List;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/18 11:38 下午
 */
public interface IBmsTopicTagService extends IService<BmsTopicTag> {

    /**
     * 获取Topic Tag 关联记录
     *
     * @param topicId TopicId
     * @return
     */
    List<BmsTopicTag> selectByTopicId(String topicId);

}
