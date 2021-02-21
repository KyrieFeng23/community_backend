package com.community.model.vo;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @author fyf
 * @since 2021/2/21 11:17 下午
 */
@Data
public class CommentVO {

    private String id;

    private String content;

    private String topicId;

    private String userId;

    private String username;

    private Date createTime;

}
