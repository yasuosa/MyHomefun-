package com.rpy.qw.post.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rpy.qw.post.domain.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: myfunhome
 * @description: 用户可查看的博客模型
 * @author: 任鹏宇
 * @create: 2020-07-25 20:20
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "post_post")
@ApiModel(value = "用户查看帖子模型")
public class ReadPostVo implements Serializable {

    /**
     * 帖子id
     */
    @ApiModelProperty(value = "帖子id")
    private String postId;

    /**
     * 创建人id
     */
    @ApiModelProperty(value = "创建人id")
    private Integer userId;

    /**
     * 创建人登录名
     */
    @ApiModelProperty(value = "创建人登录名")
    private String username;

    /**
     * 标题
     */
    @ApiModelProperty(value = "标题")
    private String postTitle;

    /**
     * 文章专栏  /专升本/考研/编程
     */
    @ApiModelProperty(value = "文章专栏")
    private Integer postCategoryId;

    /**
     * 文章类型  /提问/公告/资源
     */
    @ApiModelProperty(value = "文章类型")
    private Integer postTypeId;

    /**
     * 帖子封面
     */
    @ApiModelProperty(value = "帖子封面")
    private String postCover;

    /**
     * 内容
     */
    @ApiModelProperty(value = "内容")
    private String postContent;

    /**
     * 点赞数
     */
    @ApiModelProperty(value = "点赞数")
    private Integer postGoods;

    /**
     * 阅读数
     */
    @ApiModelProperty(value = "阅读数")
    private Integer postRead;

    /**
     * 收藏数
     */
    @ApiModelProperty(value = "收藏数")
    private Integer postCollection;

    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String postRemark;

    /**
     * 是否可以评论  0不行 1可以
     */
    @ApiModelProperty(value = "是否可以评论  0不行 1可以")
    private Integer allowComment;

    /**
     * 是否可以公开  0不行 1可以
     */
    @ApiModelProperty(value = "是否可以公开")
    private Integer isPublic;

    /**
     * 评论数
     */
    @ApiModelProperty(value = "评论数")
    private Integer postCommentnum;

    /**
     * 文章隐藏权限  登录可看，评论可看，购买
     */
    @ApiModelProperty(value = "文章隐藏权限")
    private String postHideContentPermission;

    /**
     * 是否有隐藏资源 0无 1有
     */
    @ApiModelProperty(value = "是否有隐藏资源 0无 1有")
    private Integer isHasHideContent;

    /**
     * 文章来源
     */
    @ApiModelProperty(value = "文章来源")
    private String postFromSource;


    /**
     * 是否是精华贴   0 不 1是
     */
    @ApiModelProperty(value = "是否是精华贴   0 不 1是")
    private Integer isPerfect;

    /**
     * 是否置顶  0不 1是
     */
    @ApiModelProperty(value = "是否置顶  0不 1是")
    private Integer isTop;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    @ApiModelProperty(value = "专栏名")
    private String categoryName;

    @ApiModelProperty(value = "分类名")
    private String typeName;

    @ApiModelProperty(value = "标签名")
    private List<Tag> tags =new ArrayList<>();


    @ApiModelProperty(value = "作者昵称")
    private String nickname ;

    @ApiModelProperty(value = "作者头像")
    private String userHeader ;

    @ApiModelProperty(value = "隐藏内容")
    private PostHideContentVo hideData;


    @ApiModelProperty(value = "是否点赞")
    private Boolean hasGood;

    @ApiModelProperty(value = "是否收藏")
    private Boolean hasCollection;

}
