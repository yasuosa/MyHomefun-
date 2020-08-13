package com.rpy.qw.post.service;

import com.rpy.qw.post.domain.Type;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.utils.PageVo;

import java.util.List;

public interface TypeService extends IService<Type>{


    /***
     * 分页查询 （不需要查 分类内容存在多少文章）
     * @param pageVo
     * @return
     */
    PageVo<Type> getByPage(PageVo<Type> pageVo);


    /**
     * 保存分类
     * @param type
     */
    void saveType(Type type);

    /**
     * 删除分类
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 更新
     * @param type
     */
    void updateType(Type type);


    /**
     * 启用
     * @param id
     */
    void enable(Integer id);

    /**
     * 禁用
     * @param id
     */
    void disable(Integer id);


    /**
     * 查询所有可用的分类
     * @return
     */
    List<Type> getList();


}
