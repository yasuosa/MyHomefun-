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
@TableName(value = "post_auditing")
public class Auditing implements Serializable {
    public static final String COL_USERID = "userId";
    /**
     * 审核单子id
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;

    /**
     * 帖子id
     */
    @TableField(value = "post_id")
    private String postId;

    /**
     * 审核状态 0 审核中 -1 驳回 1通过
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 用户登录名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 操作人
     */
    @TableField(value = "operate_by")
    private String operateBy;

    /**
     * 0 未删除 1已删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 操作反馈
     */
    @TableField(value = "feedback_content")
    private String feedbackContent;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 修改时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_POST_ID = "post_id";

    public static final String COL_STATUS = "status";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USERNAME = "username";

    public static final String COL_OPERATE_BY = "operate_by";

    public static final String COL_DELETED = "deleted";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}