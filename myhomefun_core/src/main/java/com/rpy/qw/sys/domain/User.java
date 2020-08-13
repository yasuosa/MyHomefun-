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
@TableName(value = "sys_user")
@ApiModel(value = "用户模型")
public class User implements Serializable {
    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "用户登录名")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value = "用户密码")
    private String password;

    /**
     * 真实姓名
     */
    @TableField(value = "realname")
    @ApiModelProperty(value = "真实名称")
    private String realname;

    /**
     * 性别 1男2女 -1未知
     */
    @TableField(value = "sex")
    @ApiModelProperty(value = "用户性别")
    private Integer sex;

    /**
     * 头像
     */
    @TableField(value = "header")
    @ApiModelProperty(value = "头像地址")
    private String headerUrl;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @TableField(value = "user_email")
    @ApiModelProperty(value = "邮箱地址")
    private String userEmail;

    /**
     * 签名
     */
    @TableField(value = "sign")
    @ApiModelProperty(value = "签名")
    private String sign;


    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value = "用户联系方式")
    private String phone;

    /**
     * 地址
     */
    @TableField(value = "address")
    @ApiModelProperty(value = "用户地址")
    private String address;


    /**
     * 盐
     */
    @TableField(value = "salt")
    @ApiModelProperty(value = "加密盐")
    private String salt;



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
     * 是否删除 0否 1是
     */
    @TableField(value = "deleted")
    @ApiModelProperty(value = "是否删除")
    private Integer deleted;


    private static final long serialVersionUID = 1L;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_REALNAME = "realname";

    public static final String COL_SEX = "sex";

    public static final String COL_HEADER = "header";

    public static final String COL_NICKNAME = "nickname";

    public static final String COL_USER_EMAIL = "user_email";

    public static final String COL_PHONE = "phone";

    public static final String COL_ADDRESS = "address";

    public static final String COL_CREATED_TIME = "created_time";

    public static final String COL_UPDATE_TIME = "update_time";

    public static final String COL_VERSION = "version";

    public static final String COL_DELETED = "deleted";
}