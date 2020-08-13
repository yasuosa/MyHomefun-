package com.rpy.qw.sys.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 友情链接表
    */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_link")
@ApiModel(value = "友链模型")
public class Link implements Serializable {
    /**
     * 链接id
     */
    @TableId(value = "link_id", type = IdType.AUTO)
    @ApiModelProperty(value = "友链ID")
    private Integer linkId;

    /**
     * 名称
     */
    @TableField(value = "link_name")
    @ApiModelProperty(value = "友链名称")
    private String linkName;

    /**
     * 介绍
     */
    @TableField(value = "link_remark")
    @ApiModelProperty(value = "友链介绍")
    private String linkRemark;



    /**
     * 链接地址
     */
    @TableField(value = "link_url")
    @ApiModelProperty(value = "友链URL")
    private String linkUrl;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    /**
     * 乐观锁
     */
    @TableField(value = "version")
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 是否删除，0否1是
     */
    @TableField(value = "deleted")
    @ApiModelProperty(value = "是否删除")
    private Integer deleted;

    private static final long serialVersionUID = 1L;

    public static final String COL_LINK_ID = "link_id";

    public static final String COL_LINK_NAME = "link_name";

    public static final String COL_LINK_URL = "link_url";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_VERSION = "version";

    public static final String COL_DELETED = "deleted";
}