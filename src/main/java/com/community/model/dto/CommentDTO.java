package com.community.model.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/22 3:04 下午
 */
@Data
public class CommentDTO implements Serializable {
    private static final long serialVersionUID = -5957433707110390852L;


    private String topic_id;

    /**
     * 内容
     */
    private String content;



}
