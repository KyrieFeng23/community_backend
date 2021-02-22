package com.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.community.model.entity.BmsPost;
import com.community.model.entity.BmsTag;

import java.util.List;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/19 9:13 下午
 */

public interface IBmsTagService extends IService<BmsTag> {
    /**
     * 插入标签
     *
     * @param tags
     * @return
     */
    List<BmsTag> insertTags(List<String> tags);

    /**
     * 获取标签关联话题
     *
     * @param topicPage
     * @param id
     * @return
     */
    Page<BmsPost> selectTopicsByTagId(Page<BmsPost> topicPage, String id);
}
