package com.community.model.dto;

import com.community.model.entity.BmsPost;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/28 4:57 下午
 */
@Data
public class UpdateTopicDTO implements Serializable {
    private static final long serialVersionUID = -5957433707110390852L;


    /**
     * 帖子
     */
    private BmsPost topic;

    /**
     * 标签
     */
    private List<String> tags;

}
