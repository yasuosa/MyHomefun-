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
@TableName(value = "post_banner")
public class Banner implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    @TableField(value = "banner_title")
    private String bannerTitle;

    @TableField(value = "banner_href")
    private String bannerHref;

    @TableField(value = "banner_cover")
    private String bannerCover;
    /**
     * 0广告 1公告 2帖子
     */
    @TableField(value = "banner_type")
    private Integer bannerType;

    @TableField(value = "created_time")
    private Date createdTime;


    @TableField(value = "banner_remark")
    private String bannerRemark;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_BANNER_TITLE = "banner_title";

    public static final String COL_BANNER_HREF = "banner_href";

    public static final String COL_BANNER_TYPE = "banner_type";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_BANNER_AUTHOR = "banner_author";

    public static final String COL_BANNER_REMARK = "banner_remark";
}