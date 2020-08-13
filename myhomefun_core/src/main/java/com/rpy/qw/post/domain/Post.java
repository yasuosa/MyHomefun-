package com.rpy.qw.post.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "post_post")
@ApiModel(value = "帖子模型")
public class Post implements Serializable {
    /**
     * 帖子id
     */
    @ApiModelProperty(value = "帖子id")
    @TableId(value = "post_id", type = IdType.INPUT)
    private String postId;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 创建人登录名
     */
    @ApiModelProperty(value = "创建人登录名")
    @TableField(value = "username")
    private String username;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    @TableField(value = "post_title")
    private String postTitle;

    /**
     * 文章专栏  /专升本/考研/编程
     */
    @ApiModelProperty(value = "文章专栏")
    @TableField(value = "post_category_id")
    private Integer postCategoryId;

    /**
     * 文章类型  /提问/公告/资源
     */
    @ApiModelProperty(value = "文章类型")
    @TableField(value = "post_type_id")
    private Integer postTypeId;

    /**
     * 帖子封面
     */
    @ApiModelProperty(value = "帖子封面")
    @TableField(value = "post_cover")
    private String postCover;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    @TableField(value = "post_content")
    private String postContent;

    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    @TableField(value = "post_goods")
    private Integer postGoods;

    /**
     * 阅读数
     */
    @ApiModelProperty(value = "阅读数")
    @TableField(value = "post_read")
    private Integer postRead;

    /**
     * 收藏数
     */
    @ApiModelProperty(value = "收藏数")
    @TableField(value = "post_collection")
    private Integer postCollection;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    @TableField(value = "post_remark")
    private String postRemark;

    /**
     * 评论数
     */
    @ApiModelProperty(value = "评论数")
    @TableField(value = "post_commentNum")
    private Integer postCommentnum;

    /**
     * 文章来源
     */
    @ApiModelProperty(value = "文章来源")
    @TableField(value = "post_from_source")
    private String postFromSource;

    /**
     * 排序码
     */
    @ApiModelProperty(value = "排序码")
    @TableField(value = "ordernum")
    private Integer ordernum;

    /**
     *   0私密  1公开
     */
    @ApiModelProperty(value = "-1不可用  0审核中  1可用")
    @TableField(value = "allow")
    private Integer allow;

    /**
     * 是否可以评论  0不行 1可以
     */
    @ApiModelProperty(value = "是否可以评论  0不行 1可以")
    @TableField(value = "allow_comment")
    private Integer allowComment;

    /**
     * 是否可以公开  0不行 1可以
     */
    @ApiModelProperty(value = "是否可以公开")
    @TableField(value = "is_public")
    private Integer isPublic;



    /**
     * 文章隐藏权限  登录可看，评论可看，购买
     */
    @ApiModelProperty(value = "文章隐藏权限")
    @TableField(value = "post_hide_content_permission")
    private String postHideContentPermission;

    /**
     * 是否有隐藏资源 0无 1有
     */
    @ApiModelProperty(value = "是否有隐藏资源 0无 1有")
    @TableField(value = "is_has_hide_content")
    private Integer isHasHideContent;

    /**
     * 是否是精华贴   0 不 1是
     */
    @ApiModelProperty(value = "是否是精华贴   0 不 1是")
    @TableField(value = "is_perfect")
    private Integer isPerfect;

    /**
     * 是否置顶  0不 1是
     */
    @ApiModelProperty(value = "是否置顶  0不 1是")
    @TableField(value = "is_top")
    private Integer isTop;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    @TableField(value = "version")
    private Integer version;

    /**
     * 1已删除 0未删除
     */
    @ApiModelProperty(value = "逻辑删除  1已删除 0未删除")
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    @TableField(value = "updated_time")
    private Date updatedTime;

    @ApiModelProperty(value = "专栏名")
    @TableField(exist = false)
    private String categoryName;

    @ApiModelProperty(value = "分类名")
    @TableField(exist = false)
    private String typeName;

    @ApiModelProperty(value = "标签名")
    @TableField(exist = false)
    private List<String> tagNames =new ArrayList<>();

    @ApiModelProperty(value = "隐藏资源价格")
    @TableField(exist = false)
    private Double price ;

    @ApiModelProperty(value = "隐藏资源")
    @TableField(exist = false)
    private String hideContent ;


    @ApiModelProperty(value = "作者昵称")
    @TableField(exist = false)
    private String nickname ;

    @ApiModelProperty(value = "作者头像")
    @TableField(exist = false)
    private String userHeader ;


    private static final long serialVersionUID = 1L;

    public static final String COL_POST_ID = "post_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USERNAME = "username";

    public static final String COL_POST_TITLE = "post_title";

    public static final String COL_POST_CATEGORY_ID = "post_category_id";

    public static final String COL_POST_TYPE_ID = "post_type_id";

    public static final String COL_POST_COVER = "post_cover";

    public static final String COL_POST_CONTENT = "post_content";

    public static final String COL_POST_GOODS = "post_goods";

    public static final String COL_POST_READ = "post_read";

    public static final String COL_POST_COLLECTION = "post_collection";

    public static final String COL_POST_REMARK = "post_remark";

    public static final String COL_POST_COMMENTNUM = "post_commentNum";

    public static final String COL_POST_FROM_SOURCE = "post_from_source";

    public static final String COL_ORDERNUM = "ordernum";

    public static final String COL_ALLOW = "allow";

    public static final String COL_POST_HIDE_CONTENT_PERMISSION = "post_hide_content_permission";

    public static final String COL_IS_HAS_HIDE_CONTENT = "is_has_hide_content";

    public static final String COL_IS_PERFECT = "is_perfect";

    public static final String COL_IS_TOP = "is_top";

    public static final String COL_VERSION = "version";

    public static final String COL_DELETED = "deleted";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}