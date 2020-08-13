package com.rpy.qw.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.qw.biz.domain.Goods;

public interface GoodsMapper extends BaseMapper<Goods> {


    /**
     * 根据postid 删除商品
     * @param postId
     */
    void deleteByPostId(String postId);


    /**
     * 根据postid 查询商品
     * @param postId
     * @return
     */
    Goods selectGoodsByPostId(String postId);


    /**
     * 根基postid 彻底删除商品
     * @param postId
     */
    void deleteTrueGoodsByPostId(String postId);
}