package com.community.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.mapper.BmsTagMapper;
import com.community.mapper.BmsTopicMapper;
import com.community.model.entity.BmsPost;
import com.community.model.entity.BmsTag;
import com.community.model.entity.BmsTopicTag;
import com.community.model.vo.PostVO;
import com.community.service.IBmsPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/18 11:39 下午
 */
@Service
public class IBmsPostServiceImpl extends ServiceImpl<BmsTopicMapper, BmsPost> implements IBmsPostService {
    @Resource
    private BmsTagMapper bmsTagMapper;

    @Autowired
    private com.community.service.IBmsTopicTagService IBmsTopicTagService;

    @Override
    public Page<PostVO> getList(Page<PostVO> page, String tab) {
        // 查询话题
        Page<PostVO> iPage = this.baseMapper.selectListAndPage(page, tab);
        // 查询话题的标签
        iPage.getRecords().forEach(topic -> {
//            根据帖子id找出topictag表里对应的记录
            List<BmsTopicTag> topicTags = IBmsTopicTagService.selectByTopicId(topic.getId());
            if (!topicTags.isEmpty()) {
//              获取topictag里的tagid集合，当前帖子的所有标签id
//              topictags是整个类的集合，下面这一步操作是把类的集合的tagid提取出来合成一个新的集合
//              这是java8的一种写法，免去再次遍历的麻烦
                List<String> tagIds = topicTags.stream().map(BmsTopicTag::getTagId).collect(Collectors.toList());
//              根据刚才上面的tagid集合，找出一一对应的tag类
                List<BmsTag> tags = bmsTagMapper.selectBatchIds(tagIds);
                //保存到vo里
                topic.setTags(tags);
            }
        });
        return iPage;
    }
}
