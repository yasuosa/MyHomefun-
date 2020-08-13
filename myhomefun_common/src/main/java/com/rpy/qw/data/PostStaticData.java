package com.rpy.qw.data;

/**
 * @program: myfunhome
 * @description: 业务上的静态变量
 * @author: 任鹏宇
 * @create: 2020-07-13 22:38
 **/
public interface PostStaticData {

     /**
      * 默认点赞数
      */
     Integer POST_DEFAULT_GOODS=0;

     /**
      * 默认阅读数
      */
     Integer POST_DEFAULT_READS=0;

     /**
      * 默认评论数
      */
     Integer POST_DEFAULT_COMMENTS=0;

     /**
      * 默认收藏数
      */
     Integer POST_DEFAULT_COLLECTIONS=0;

     /**
      * 存在隐藏资源
      */
     Integer POST_HIDE_CONTENT_PERMISSION_EXIST=1;

     /**
      * 隐藏资源为商品
      */
     Integer POST_HIDE_CONTENT_TYPE_GOODS=1;


     /**
      * 隐藏资源为默认普通
      */
     Integer POST_HIDE_CONTENT_TYPE_DEFAULT =0 ;



     /**
      * 隐藏资源的为需要需要购买
      */
     String POST_HIDE_CONTENT_PERMISSION_HAS_GOODS="hide_content:buy";


     /**
      * 隐藏资源为登录可见
      */
     String POST_HIDE_CONTENT_PERMISSION_HAS_LOGIN="hide_content:login";

     /**
      * 隐藏资源评论可以见
      */
     String POST_HIDE_CONTENT_PERMISSION_HAS_COMMENT="hide_content:comment";


     /**
      * 审核中
      */
     Integer POST_ALLOW_TYPE_AUDITING = 0;

     /**
      * 审核失败
      */
     Integer POST_ALLOW_TYPE_AUDITING_DENY = -1;

     /**
      * 审核成功
      */
     Integer POST_ALLOW_TYPE_AUDITING_ALLOW = 1;


     /**
      * 帖子置顶 1 / 0
      */
     Integer POST_IS_TOP = 1;
     Integer POST_IS_NOT_TOP = 0;

     /**
      * 帖子精华 1/0
      */
     Integer POST_IS_PERFECT = 1;
     Integer POST_IS_NOT_PERFECT = 0;


     /**
      * 用户对帖子的行为
      */
     Integer GOOD_TYPE = 0;
     Integer COLLECT_TYPE = 1;

     /**
      * 商品的支付类型
      */
     Integer GOOD_COST_TYPE_POINT = 0;
     Integer GOOD_COST_TYPE_MONEY = 1;
}
