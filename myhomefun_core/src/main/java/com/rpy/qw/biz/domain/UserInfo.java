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
@TableName(value = "biz_user_info")
@ApiModel(value = "用户资金模型")
public class UserInfo implements Serializable {

    public UserInfo(String username, Double accumulatePoints) {
        this.username = username;
        this.accumulatePoints = accumulatePoints;
    }

    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @ApiModelProperty(value = "id")
    private Integer id;

    /**
     * userId
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 用户余额
     */
    @TableField(value = "money")
    @ApiModelProperty(value = "用户余额")
    private Double money;

    /**
     * 积分
     */
    @ApiModelProperty(value = "用户余额积分")
    @TableField(value = "accumulate_points")
    private Double accumulatePoints;

    /**
     * 支付宝收款码的地址
     */
    @ApiModelProperty(value = "支付宝收款码的地址")
    @TableField(value = "zfb_receiving_money_code")
    private String zfbReceivingMoneyCode;


    /**
     * 支付宝账号
     */
    @ApiModelProperty(value = "支付宝账号")
    @TableField(value = "zfb_account")
    private String zfbAccount;
    /**
     * 微信收款码的地址
     */
    @ApiModelProperty(value = "微信收款码的地址")
    @TableField(value = "wx_receiving_money_code")
    private String wxReceivingMoneyCode;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    @TableField(value = "version")
    private Integer version;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "created_time")
    private Date createdTime;


    /**
     * 1已删除 0未删除
     */
    @ApiModelProperty(value = "1已删除 0未删除")
    @TableField(value = "deleted")
    private Integer deleted;

    public UserInfo(Integer userId, String username) {
        this.userId = userId;
        this.username = username;
    }

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USERNAME = "username";

    public static final String COL_MONEY = "money";

    public static final String COL_ACCUMULATE_POINTS = "accumulate_points";

    public static final String COL_ZFB_RECEIVING_MONEY = "zfb_receiving_money";

    public static final String COL_WX_RECEIVING_MONEY = "wx_receiving_money";

    public static final String COL_VERSION = "version";

    public static final String COL_CREATED_TIME = "created_time";
}