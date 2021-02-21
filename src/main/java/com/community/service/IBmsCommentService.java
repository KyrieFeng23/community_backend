package com.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.community.model.entity.BmsComment;
import com.community.model.vo.CommentVO;

import java.util.List;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/21 11:17 下午
 */
public interface IBmsCommentService extends IService<BmsComment> {
    /**
     *
     *
     * @param topicid
     * @return {@link BmsComment}
     */
    List<CommentVO> getCommentsByTopicID(String topicid);

}
