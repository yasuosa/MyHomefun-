package com.rpy.qw.enums;

import lombok.Getter;

/**
 * @program: myfunhome
 * @description: 状态码
 * @author: 任鹏宇
 * @create: 2020-06-18 22:47
 **/

@Getter
public enum StateEnum {

    /**
     *  0 管理员
     *  1 普通用户
     */
    ADMIN(0,"管理员"),
    USER(1,"普通用户"),

    /**
     * 逻辑删除
     *   0  未删除
     *   1  已删除
     */
    DELETED_FALSE(0,"未删除"),
    DELETED_TRUE(1,"已删除"),

    /**
     * asc
     * desc
     */
    ASC(-1,"asc"),
    DESC(-1,"desc"),

    /**
     * 审批 -1 拒绝 0 审批中  1通过
     */
    ALLOW_AUDITING(1,"通过"),
    AUDITING(0,"审批中"),
    DENY_AUDITING(-1,"拒绝"),


    ENABLE(1,"启用"),
    DISABLE(0,"禁用"),


    /**
     * 请求访问状态
     */
    REQUEST_SUCCESS(1,"请求成功"),
    REQUEST_ERROR(0,"请求异常"),

    /**
     * 文章允许和拒绝
     */
    ALLOW(1,"允许"),
    DENY(-1,"拒绝"),


    /**
     * 菜单类比
     */
    TYPECODE_TOPMENU(0,"top_menu"),
    TYPECODE_LEFTMENU(0,"left_menu"),
    TYPECODE_PERMISSSION(0,"permission"),


    /**
     * 帖子是否公开
     */
    ALLOW_PUBLIC(1,"公开"),
    DENY_PUBLIC(0,"私密"),

    /**
     * 评论是否可以
     */
    ALLOW_COMMENT(1,"可以"),
    DENY_COMMENT(0,"不可以"),


    /**
     * 流水张类型
     */
    JOURNAL_TYPE_SHIN(1,"签到"),
    JOURNAL_TYPE_BUY(2,"购买资源"),
    JOURNAL_TYPE_EARN(3,"资源被购买"),
    JOURNAL_TYPE_COMMENT(4,"评论"),
    JOURNAL_TYPE_PUBLISH(6,"发帖"),

    /**
     * 支付方式
     */
    PAYMENT_TYPE_POINTS(0,"积分支付"),
    PAYMENT_TYPE_MONEY(1,"余额支付"),


    /**
     * banner 类型
     */
    BANNER_TYPE_GUANGGAO(0,"广告"),
    BANNER_TYPE_GONGGAO(1,"公告"),
    BANNER_TYPE_POST(2,"帖子")

    ;

    private Integer code;
    private String msg;

    StateEnum(Integer code, String msg){
        this.code=code;
        this.msg=msg;
    }
}
