package com.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.community.model.entity.BmsPost;
import com.community.model.entity.BmsTag;
import com.community.model.entity.BmsTopicTag;
import com.community.model.entity.UmsUser;

import java.util.List;
import java.util.Set;

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

    /**
     * 创建中间关系
     *
     * @param id
     * @param tags
     * @return
     */
    void createTopicTag(String id, List<BmsTag> tags);

    /**
     * 获取标签换脸话题ID集合
     *
     * @param id
     * @return
     */
    Set<String> selectTopicIdsByTagId(String id);

    /**
     * 更新帖子和标签的关联表
     * @param topicId
     * @param tags
     */
    void updateTopicTag(String topicId, List<String> tags);
}
