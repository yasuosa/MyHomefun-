package com.rpy.qw.post.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: myfunhome
 * @description: 帖子的隐藏资源
 * @author: 任鹏宇
 * @create: 2020-07-14 15:25
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "post_hide_content")
public class PostHideContent implements Serializable {

    @TableId(value = "id")
    private Integer id;


    /**
     * 类型 1普通文本  2 支付文本
     */
    @TableField(value = "type")
    private Integer type;

    @TableField(value = "post_id")
    private String postId;

    @TableField(value = "resource")
    private String resource;


    @TableField(value = "deleted")
    private Integer deleted;
    /**
     * 价格
     */
    @TableField(value = "price")
    private Double price;

    @TableField(value = "created_time")
    private Date createdTime;
}
