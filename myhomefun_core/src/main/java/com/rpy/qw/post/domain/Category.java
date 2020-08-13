package com.rpy.qw.post.domain;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "post_category")
@ApiModel(value = "专栏模型")
public class Category implements Serializable {
    /**
     * 专栏id
     */
    @ApiModelProperty(value = "专栏id")
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 专栏名称
     */
    @ApiModelProperty(value = "专栏名称")
    @TableField(value = "name")
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

    /**
     * 前端的url
     */
    @ApiModelProperty(value = "前端的url")
    @TableField(value = "path")
    private String path;

    /**
     * 启用禁用 0 1
     */
    @ApiModelProperty(value = "启用禁用 0 1")
    @TableField(value = "enable")
    private Integer enable;

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

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_REMARK = "remark";

    public static final String COL_PATH = "path";

    public static final String COL_ENABLE = "enable";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}