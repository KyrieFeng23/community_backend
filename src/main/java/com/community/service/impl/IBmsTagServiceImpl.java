package com.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.mapper.BmsTagMapper;
import com.community.model.entity.BmsPost;
import com.community.model.entity.BmsTag;
import com.community.service.IBmsTagService;
import com.community.service.IBmsTopicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/19 9:18 下午
 */
@Service
public class IBmsTagServiceImpl extends ServiceImpl<BmsTagMapper, BmsTag> implements IBmsTagService {

    @Autowired
    private com.community.service.IBmsTopicTagService IBmsTopicTagService;

    @Autowired
    private com.community.service.IBmsPostService IBmsPostService;


    @Override
    public List<BmsTag> insertTags(List<String> tagNames) {
        //定义标签类列表
        List<BmsTag> tagList = new ArrayList<>();
        //遍历参数 标签名字列表
        for (String tagName : tagNames) {
            //查询是否已经有这个标签
            BmsTag tag = this.baseMapper.selectOne(new LambdaQueryWrapper<BmsTag>().eq(BmsTag::getName, tagName));
            if (tag == null) {
                //没有则创建
                tag = BmsTag.builder().name(tagName).build();
                this.baseMapper.insert(tag);
            } else {
                //有则把标签下的帖子数量加一
                tag.setTopicCount(tag.getTopicCount() + 1);
                this.baseMapper.updateById(tag);
            }
            //处理后的tag类添加到最开始创建的list中，最后返回
            tagList.add(tag);
        }
        return tagList;
    }


    @Override
    public Page<BmsPost> selectTopicsByTagId(Page<BmsPost> topicPage, String id) {

        // 获取关联的话题ID
        Set<String> ids = IBmsTopicTagService.selectTopicIdsByTagId(id);
        LambdaQueryWrapper<BmsPost> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(BmsPost::getId, ids);

        return IBmsPostService.page(topicPage, wrapper);
    }

    @Override
    public int updateTags(String tag_id) {
        BmsTag tag = this.getById(tag_id);
        tag.setTopicCount(tag.getTopicCount() - 1);
        this.baseMapper.updateById(tag);
        if (tag.getTopicCount()==0){
            this.removeById(tag_id);
        }
        return 0;
    }
}
