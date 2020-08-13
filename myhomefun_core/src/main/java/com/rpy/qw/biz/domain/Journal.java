package com.rpy.qw.biz.domain;

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
@TableName(value = "biz_journal")
public class Journal implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Integer id;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 用户登录名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 商品id
     */
    @TableField(value = "good_id")
    private Integer goodId;

    /**
     * 消费i金额
     */
    @TableField(value = "pay_money")
    private Double payMoney;

    /**
     * 总金额
     */
    @TableField(value = "account_money")
    private Double accountMoney;

    /**
     * 总积分
     */
    @TableField(value = "account_points")
    private Double accountPoints;

    /**
     * 0 积分 1金钱
     */
    @TableField(value = "payment_money_type")
    private Integer paymentMoneyType;

    /**
     * 类型
     */
    @TableField(value = "type")
    private Integer type;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 逻辑删除
     */
    @TableField(value = "deleted")
    private Integer deleted;

    /**
     * 创建时间
     */
    @TableField(value = "created_time")
    private Date createdTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USERNAME = "username";

    public static final String COL_GOOD_ID = "good_id";

    public static final String COL_PAY_MONEY = "pay_money";

    public static final String COL_ACCOUNT_MONEY = "account_money";

    public static final String COL_ACCOUNT_POINTS = "account_points";

    public static final String COL_PAYMENT_MONEY_TYPE = "payment_money_type";

    public static final String COL_TYPE = "type";

    public static final String COL_REMARK = "remark";

    public static final String COL_DELETED = "deleted";

    public static final String COL_CREATED_TIME = "created_time";
}