package com.rpy.qw.post.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "post_comment")
public class Comment implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 对象id 
     */
    @TableField(value = "pid")
    private Integer pid;

    /**
     * 帖子id
     */
    @TableField(value = "post_id")
    private String postId;

    /**
     * 用户id
     */
    @TableField(value = "userId")
    private Integer userId;

    /**
     * 用户登录名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 点赞数
     */
    @TableField(value = "goods")
    private Integer goods;

    /**
     * 回复内容
     */
    @TableField(value = "comment")
    private String comment;

    /**
     * 0 未删除 1已删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_PID = "pid";

    public static final String COL_POST_ID = "post_id";

    public static final String COL_USERID = "userId";

    public static final String COL_USERNAME = "username";

    public static final String COL_GOODS = "goods";

    public static final String COL_COMMENT = "comment";

    public static final String COL_DELETED = "deleted";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}