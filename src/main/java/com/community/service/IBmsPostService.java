package com.community.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.community.model.dto.CreateTopicDTO;
import com.community.model.entity.BmsPost;
import com.community.model.entity.UmsUser;
import com.community.model.vo.PostVO;

import java.util.List;
import java.util.Map;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/18 11:37 下午
 */
public interface IBmsPostService extends IService<BmsPost> {

    /**
     * 获取首页话题列表
     *
     * @param page
     * @param tab
     * @return
     */
    Page<PostVO> getList(Page<PostVO> page, String tab);

    /**
     * 发布
     *
     * @param dto
     * @param principal
     * @return
     */
    BmsPost create(CreateTopicDTO dto, UmsUser principal);

    /**
     * 编辑帖子
     *
     * @param post
     * @param tags
     * @return
     */
    BmsPost updateTopic(BmsPost post,List<String> tags);

    /**
     * 查看话题详情
     *
     * @param id
     * @return
     */
    Map<String, Object> viewTopic(String id);


    /**
     * 获取随机推荐10篇
     *
     * @param id
     * @return
     */
    List<BmsPost> getRecommend(String id);

    /**
     * 关键字检索
     *
     * @param keyword
     * @param page
     * @return
     */
    Page<PostVO> searchByKey(String keyword, Page<PostVO> page);

    /**
     * 根据帖子id和更新方式更新评论数
     * @param topic_id
     * @param isAdd
     */
    void updateCommentCount(String topic_id,boolean isAdd);

}
