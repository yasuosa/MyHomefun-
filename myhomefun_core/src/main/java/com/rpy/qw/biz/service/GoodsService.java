package com.rpy.qw.biz.service;

import com.rpy.qw.biz.domain.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.utils.PageVo;

public interface GoodsService extends IService<Goods>{


    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    PageVo<Goods> getByPage(PageVo<Goods> pageVo);


    /**
     * 购买商品
     * @param goodsId
     */
    void payPoints(Integer goodsId);

}
