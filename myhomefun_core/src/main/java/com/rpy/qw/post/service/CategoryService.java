package com.rpy.qw.post.service;

import com.rpy.qw.post.domain.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.utils.PageVo;

import java.util.List;

public interface CategoryService extends IService<Category>{


    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    PageVo<Category> getByPage(PageVo<Category> pageVo);


    /**
     * 保存
     * @param category
     */
    void saveCategory(Category category);

    /**
     * 更新
     * @param category
     */
    void updateCategory(Category category);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);

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
     * 查询所有可用的专栏
     * @return
     */
    List<Category> getList();

}
