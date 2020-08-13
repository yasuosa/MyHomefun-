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
@TableName(value = "post_type")
@ApiModel(value = "分类模块")
public class Type implements Serializable {
    /**
     * 分类id
     */
    @ApiModelProperty(value = "分类id")
    @TableId(value = "type_id", type = IdType.AUTO)
    private Integer typeId;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    @TableField(value = "type_name")
    private String typeName;

    /**
     * 贴子数
     */
    @ApiModelProperty(value = "贴子数")
    @TableField(value = "type_post_count")
    private Integer typePostCount;

    /**
     * 是否启用 0否1是
     */
    @ApiModelProperty(value = "是否启用 0否1是")
    @TableField(value = "enable")
    private Integer enable;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "created_time")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_TYPE_ID = "type_id";

    public static final String COL_TYPE_NAME = "type_name";

    public static final String COL_TYPE_POST_COUNT = "type_post_count";

    public static final String COL_ENABLE = "enable";

    public static final String COL_CREATED_TIME = "created_time";
}