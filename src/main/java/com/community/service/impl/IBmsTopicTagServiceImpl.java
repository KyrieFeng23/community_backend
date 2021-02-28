package com.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.mapper.BmsTopicTagMapper;
import com.community.model.entity.BmsPost;
import com.community.model.entity.BmsTag;
import com.community.model.entity.BmsTopicTag;
import com.community.model.entity.UmsUser;
import com.community.service.IBmsTagService;
import com.community.service.IBmsTopicTagService;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/18 11:42 下午
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class IBmsTopicTagServiceImpl extends ServiceImpl<BmsTopicTagMapper, BmsTopicTag> implements IBmsTopicTagService {

    @Autowired
    @Lazy
    private IBmsTagService iBmsTagService;

    @Override
    public List<BmsTopicTag> selectByTopicId(String topicId) {
        QueryWrapper<BmsTopicTag> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BmsTopicTag::getTopicId, topicId);
        return this.baseMapper.selectList(wrapper);
    }

    @Override
    public void createTopicTag(String id, List<BmsTag> tags) {
        // 先删除topicId对应的所有记录
        // 因为有可能你最开始创建帖子的时候，有五个标签，之后修改帖子，删除了两个标签，那么这两个标签再一个个去判断比较麻烦
        // 直接删除再新建更方便

        this.baseMapper.delete(new LambdaQueryWrapper<BmsTopicTag>().eq(BmsTopicTag::getTopicId, id));

        // 循环保存对应关联
        tags.forEach(tag -> {
            BmsTopicTag topicTag = new BmsTopicTag();
            topicTag.setTopicId(id);
            topicTag.setTagId(tag.getId());
            this.baseMapper.insert(topicTag);
        });
    }

    @Override
    public Set<String> selectTopicIdsByTagId(String id) {
        return this.baseMapper.getTopicIdsByTagId(id);
    }

    @Override
    public void updateTopicTag(String topicId, List<String> tags) {
        if (!ObjectUtils.isEmpty(tags)) {

            List<BmsTag> tagList = new ArrayList<>();
            for (String tag : tags){
                BmsTag bmsTag = iBmsTagService.getOne(new LambdaQueryWrapper<BmsTag>().eq(BmsTag::getName, tag));
                tagList.add(bmsTag);
            }
            this.createTopicTag(topicId,tagList);
        }
    }


}
