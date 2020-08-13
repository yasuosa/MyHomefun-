package com.rpy.qw.biz.domain;

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
@TableName(value = "biz_goods")
@ApiModel(value = "商品类模型")
public class Goods implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "商品id")
    private Integer id;

    /**
     * 商品名称
     */
    @TableField(value = "name")
    @ApiModelProperty(value = "商品名称")
    private String name;


    /**
     * 帖子
     */
    @TableField(value = "post_id")
    @ApiModelProperty(value = "帖子id")
    private String  postId;




    /**
     * 商品类型 0积分 1钱
     */
    @ApiModelProperty(value = "商品类型 0积分 1钱")
    @TableField(value = "type")
    private Integer type;



    /**
     * 商品内容
     */
    @ApiModelProperty(value = "商品内容")
    @TableField(value = "content")
    private String content;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @TableField(value = "remark")
    private String remark;

    /**
     * 商品价格
     */
    @ApiModelProperty(value = "商品价格")
    @TableField(value = "price")
    private Double price;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 创建人联系
     */
    @ApiModelProperty(value = "创建人联系方式")
    @TableField(value = "user_contact")
    private String userContact;

    /**
     * 商品封面
     */
    @ApiModelProperty(value = "商品封面")
    @TableField(value = "cover")
    private String cover;

    /**
     * 1已删除 0未删除
     */
    @ApiModelProperty(value = "1已删除 0未删除")
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 创建人账号
     */
    @ApiModelProperty(value = "创建人账号")
    @TableField(value = "username")
    private String username;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_NAME = "name";

    public static final String COL_CONTENT = "content";

    public static final String COL_REMARK = "remark";

    public static final String COL_PRICE = "price";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USERNAME = "username";
}