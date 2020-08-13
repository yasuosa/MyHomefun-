package com.rpy.qw.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.qw.sys.domain.Menu;

public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 查询最高排序1数
     * @return
     */
    Integer selectTopOrderNum();


    /**
     * 查询当前id的子类数
     * @param id
     * @return
     */
    Integer queryByParentId(Integer id);
}