package com.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.community.common.api.ApiResult;
import com.community.model.dto.CommentDTO;
import com.community.model.entity.*;
import com.community.model.vo.CommentVO;
import com.community.service.IBmsCommentService;
import com.community.service.IBmsPostService;
import com.community.service.IUmsUserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.community.jwt.JwtUtil.USER_NAME;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/21 11:14 下午
 */
@RestController
@RequestMapping("/comment")
public class BmsCommentController extends BaseController {

    @Resource
    private IBmsCommentService bmsCommentService;
    @Resource
    private IUmsUserService umsUserService;
    @Resource
    private IBmsPostService bmsPostService;

    @GetMapping("/get_comments")
    public ApiResult<List<CommentVO>> getCommentsByTopicID(@RequestParam(value = "topicid", defaultValue = "1") String topicid) {
        List<CommentVO> lstBmsComment = bmsCommentService.getCommentsByTopicID(topicid);
        return ApiResult.success(lstBmsComment);
    }

    @PostMapping("/add_comment")
    public ApiResult<BmsComment> add_comment(@RequestHeader(value = USER_NAME) String userName,
                                             @RequestBody CommentDTO dto) {
        UmsUser user = umsUserService.getUserByUsername(userName);
        BmsComment comment = bmsCommentService.create(dto, user);
        bmsPostService.updateCommentCount(dto.getTopic_id(),true);
        return ApiResult.success(comment);
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete(
            @RequestHeader(value = USER_NAME) String userName,
                                    @PathVariable("id") String id) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        BmsComment bmsComment = bmsCommentService.getById(id);
        String topicId=bmsComment.getTopicId();
        Assert.notNull(bmsComment, "来晚一步，话题已不存在");
        Assert.isTrue(bmsComment.getUserId().equals(umsUser.getId()), "你为什么可以删除别人的评论？？？");
        //删除评论表中数据
        bmsCommentService.removeById(id);
        //更新post表中comment字段的数据
        bmsPostService.updateCommentCount(topicId,false);
        return ApiResult.success(null,"删除成功");
    }
}
