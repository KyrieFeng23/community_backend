package com.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.community.common.api.ApiResult;
import com.community.model.dto.CreateTopicDTO;
import com.community.model.entity.*;
import com.community.model.vo.PostVO;
import com.community.service.*;
import com.vdurmont.emoji.EmojiParser;
import jdk.jfr.Threshold;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.community.jwt.JwtUtil.USER_NAME;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/18 11:30 下午
 */
@RestController
@RequestMapping("/post")
public class BmsPostController extends BaseController {

    @Resource
    private IBmsPostService iBmsPostService;
    @Resource
    private IUmsUserService umsUserService;
    @Resource
    private IBmsCommentService iBmsCommentService;
    @Resource
    private IBmsTopicTagService iBmsTopicTagService;
    @Resource
    private IBmsTagService iBmsTagService;

    @GetMapping("/list")
    public ApiResult<Page<PostVO>> list(@RequestParam(value = "tab", defaultValue = "latest") String tab,
                                        @RequestParam(value = "pageNo", defaultValue = "1")  Integer pageNo,
                                        @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        Page<PostVO> list = iBmsPostService.getList(new Page<>(pageNo, pageSize), tab);
        //返回一个page对象，是mybatis-plus自带的一个对象，根据获得的参数生成，mapper自动会有分页的功能。
        return ApiResult.success(list);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ApiResult<BmsPost> create(@RequestHeader(value = USER_NAME) String userName
            , @RequestBody CreateTopicDTO dto) {
        UmsUser user = umsUserService.getUserByUsername(userName);
        BmsPost topic = iBmsPostService.create(dto, user);
        return ApiResult.success(topic);
    }

    @GetMapping()
    public ApiResult<Map<String, Object>> view(@RequestParam("id") String id) {
        //因为有帖子、用户、标签三个表的信息，所以用map
        Map<String, Object> map = iBmsPostService.viewTopic(id);
        return ApiResult.success(map);
    }

    @GetMapping("/recommend")
    public ApiResult<List<BmsPost>> getRecommend(@RequestParam("topicId") String id) {
        List<BmsPost> topics = iBmsPostService.getRecommend(id);
        return ApiResult.success(topics);
    }

    @PostMapping("/update")
    public ApiResult<BmsPost> update(@RequestHeader(value = USER_NAME) String userName, @Valid @RequestBody BmsPost post) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        Assert.isTrue(umsUser.getId().equals(post.getUserId()), "非本人无权修改");
        post.setModifyTime(new Date());
        post.setContent(EmojiParser.parseToAliases(post.getContent()));
        iBmsPostService.updateById(post);
        return ApiResult.success(post);
    }

    @Transactional(rollbackFor = Exception.class)
    @DeleteMapping("/delete/{id}")
    public ApiResult<String> delete(@RequestHeader(value = USER_NAME) String userName, @PathVariable("id") String id) {
        UmsUser umsUser = umsUserService.getUserByUsername(userName);
        BmsPost byId = iBmsPostService.getById(id);
        Assert.notNull(byId, "来晚一步，话题已不存在");
        Assert.isTrue(byId.getUserId().equals(umsUser.getId()), "你为什么可以删除别人的话题？？？");
        iBmsPostService.removeById(id);
        //删除帖子下的评论，根据topic-id查询记录并删除
        iBmsCommentService.deleteComments(id);
        //删除对应标签记录。
        QueryWrapper<BmsTopicTag> bmsTopicTagQueryWrapper=new QueryWrapper<>();
        bmsTopicTagQueryWrapper.lambda().eq(BmsTopicTag::getTopicId,id);
        //根据帖子id找到对应tag的列表
        List<BmsTopicTag> bmsTopicTagList=iBmsTopicTagService.selectByTopicId(id);
        if (!bmsTopicTagList.isEmpty()) {
            //删除对应的tag记录
            iBmsTopicTagService.remove(bmsTopicTagQueryWrapper);
//              获取topictag里的tagid集合，当前帖子的所有标签id
//              topictags是整个类的集合，下面这一步操作是把类的集合的tagid提取出来合成一个新的集合
//              这是java8的一种写法，免去再次遍历的麻烦
            List<String> tagIds = bmsTopicTagList.stream().map(BmsTopicTag::getTagId).collect(Collectors.toList());
            tagIds.forEach(tagid->{
                iBmsTagService.updateTags(tagid);
            });
        }
        return ApiResult.success(null,"删除成功");
    }

}
