package com.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
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

}
