package com.community.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data   //自动生成setter and getter
@Builder
@Accessors(chain = true)    //与上一条builder一起是生成的对象支持链式操作
//链式操作：例：
// stringBuilder.append("Mr").append(".").append("Yuan").insert(0,"Hello!"); //在0位置插入Hello
@TableName("bms_billboard")   //指明数据库里对应的表
@NoArgsConstructor      //生成无参的构造方法
@AllArgsConstructor     //生成全参数的构造方法
public class BmsBillboard implements Serializable {   //使用serializable接口方便序列化

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 公告牌
     */
    @TableField("content")
    private String content;

    /**
     * 公告时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 1：展示中，0：过期
     */
    @Builder.Default
    @TableField("`show`")
    private boolean show = false;

}