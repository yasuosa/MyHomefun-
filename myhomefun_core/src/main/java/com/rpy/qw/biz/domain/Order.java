package com.rpy.qw.biz.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "biz_order")
public class Order implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 文章id
     */
    @TableField(value = "post_id")
    private String postId;

    /**
     * 用户
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer  userId;

    /**
     * 用户
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名")
    private String username;


    /**
     * 商品id
     */
    @TableField(value = "goods_id")
    private Integer goodsId;

    /**
     * 商品标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 商品价格
     */
    @TableField(value = "price")
    private Double price;

    /**
     * 购买数量
     */
    @TableField(value = "number")
    private Integer number;

    /**
     * 支付方式
     */
    @TableField(value = "payment")
    private String payment;

    /**
     * 收货地址
     */
    @TableField(value = "address")
    private String address;

    /**
     * 1 已签收 0 未签收
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 逻辑删除 0未删除 1已删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_POST_ID = "post_id";

    public static final String COL_GOODS_ID = "goods_id";

    public static final String COL_TITLE = "title";

    public static final String COL_PRICE = "price";

    public static final String COL_NUMBER = "number";

    public static final String COL_PAYMENT = "payment";

    public static final String COL_ADDRESS = "address";

    public static final String COL_STATUS = "status";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_DELETED = "deleted";
}