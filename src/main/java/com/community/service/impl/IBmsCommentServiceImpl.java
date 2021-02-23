package com.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.mapper.BmsCommentMapper;
import com.community.model.dto.CommentDTO;
import com.community.model.entity.BmsComment;
import com.community.model.entity.UmsUser;
import com.community.model.vo.CommentVO;
import com.community.service.IBmsCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/21 11:18 下午
 */
@Slf4j
@Service
public class IBmsCommentServiceImpl extends ServiceImpl<BmsCommentMapper, BmsComment> implements IBmsCommentService {
    @Override
    public List<CommentVO> getCommentsByTopicID(String topicid) {
        List<CommentVO> lstBmsComment = new ArrayList<CommentVO>();
        try {
            lstBmsComment = this.baseMapper.getCommentsByTopicID(topicid);
        } catch (Exception e) {
            log.info("lstBmsComment失败");
        }
        return lstBmsComment;
    }

    @Override
    public BmsComment create(CommentDTO dto, UmsUser user) {
        BmsComment comment = BmsComment.builder()
                .userId(user.getId())
                .content(dto.getContent())
                .topicId(dto.getTopic_id())
                .createTime(new Date())
                .build();
        this.baseMapper.insert(comment);
        return comment;
    }

    @Override
    public int deleteComments(String id) {
        QueryWrapper<BmsComment> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BmsComment::getTopicId, id);
        this.baseMapper.delete(wrapper);
        return 0;
    }


}
