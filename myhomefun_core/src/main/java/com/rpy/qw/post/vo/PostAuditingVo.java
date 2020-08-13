package com.rpy.qw.post.vo;

import com.rpy.qw.post.domain.PostHideContent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @program: myfunhome
 * @description: 帖子审批表的VO
 * @author: 任鹏宇
 * @create: 2020-07-15 17:24
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostAuditingVo implements Serializable {

    /**
     * 帖子id
     */
    private String postId;

    /**
     * 审核编号
     */
    private String auditingId;

    /**
     * 创建人id
     */
    private Integer userId;

    /**
     * 创建人登录名
     */
    private String username;

    /**
     * 创建人昵称
     */
    private String nickname;



    /**
     * 标题
     */
    private String postTitle;

    /**
     * 文章专栏  /专升本/考研/编程
     */
    private Integer postCategoryId;

    /**
     * 专栏名称
     */
    private String postCategoryName;

    /**
     * 文章类型  /提问/公告/资源
     */
    private Integer postTypeId;

    /**
     * 类型名称
     */
    private String postTypeName;

    /**
     * 帖子封面
     */
    private String postCover;

    /**
     * 内容
     */
    private String postContent;


    /**
     * 简介
     */
    private String postRemark;


    /**
     * 文章来源
     */
    private String postFromSource;


    private Date createdTime;

    /**
     * -1不可用 审核  1可用
     */
    private Integer allow;

    /**
     * 文章隐藏权限  登录可看，评论可看，购买
     */
    private String postHideContentPermission;

    /**
     * 是否有隐藏资源 0无 1有
     */
    private Integer isHasHideContent;


    /**
     * 标签名
     */
    private List<String> tagNames;


    /**
     * 隐藏资源
     */
    private PostHideContent hideContent;


}
