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

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "sys_role")
@ApiModel(value = "角色模型")
public class Role implements Serializable {
    /**
     * 角色id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "角色Id")
    private Integer id;

    /**
     * 角色名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "角色名")
    private String name;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 可用1 不可用0
     */
    @TableField(value = "available")
    @ApiModelProperty(value = "可用1 不可用0")
    private Integer available;

    /**
     * 1已删除 0未删除
     */
    @TableField(value = "deleted")
    @ApiModelProperty(value = "1已删除 0未删除")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    @ApiModelProperty(value = "更新时间")
    private Date updatedTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_REMARK = "remark";

    public static final String COL_AVAILABLE = "available";

    public static final String COL_DELETED = "deleted";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATED_TIME = "updated_time";
}