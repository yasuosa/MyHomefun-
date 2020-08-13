package com.rpy.qw.biz.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "biz_sign")
public class Sign implements Serializable {
    public static final String COL_TOTAL = "total";
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
     * 当月
     */
    @TableField(value = "date_month")
    @JsonFormat(pattern = "yyyy-MM",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM",iso = DateTimeFormat.ISO.DATE)
    private Date dateMonth;

    /**
     * 签到情况
     */
    @TableField(value = "mask")
    private Integer mask;

    /**
     * 本月连续签到
     */
    @TableField(value = "continue_sign_month")
    private Integer continueSignMonth;

    /**
     * 更新时间
     */
    @TableField(value = "updated_time")
    private Date updatedTime;

    /**
     * 从前的签到数
     */
    @JsonIgnore
    @TableField(value = "before_continue_sign_month")
    private Integer beforeContinueSignMonth;

    @TableField(exist = false)
    private boolean isSignToday;

    @TableField(exist = false)
    private Integer continueTotal;


    @TableField(exist = false)
    private Integer total;


    public Integer getContinueTotal() {
        return this.continueSignMonth+this.beforeContinueSignMonth;
    }

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_DATE_MONTH = "date_month";

    public static final String COL_MASK = "mask";

    public static final String COL_CONTINUE_SIGN_MONTH = "continue_sign_month";

    public static final String COL_UPDATED_TIME = "updated_time";

    public static final String COL_BEFORE_CONTINUE_SIGN_MONTH = "before_continue_sign_month";

    
}