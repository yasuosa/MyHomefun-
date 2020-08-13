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
@TableName(value = "post_tag")
@ApiModel(value = "标签模型")
public class Tag implements Serializable {
    /**
     * id
     */
    @ApiModelProperty(value = "标签id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 专栏id

     */
    @ApiModelProperty(value = "专栏id")
    @TableField(value = "c_id")
    private Integer cId;


    /**
     * 标签姓名
     */
    @ApiModelProperty(value = "标签名")
    @TableField(value = "name")
    private String name;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 专栏名称
     */
    @ApiModelProperty(value = "专栏名称")
    @TableField(exist = false)
    private String cName;


    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_C_ID = "c_id";

    public static final String COL_C_NAME = "c_name";

    public static final String COL_NAME = "name";

    public static final String COL_CREATED_TIME = "created_time";
}