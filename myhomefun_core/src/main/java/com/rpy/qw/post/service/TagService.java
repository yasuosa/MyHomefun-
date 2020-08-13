package com.rpy.qw.post.service;

import com.rpy.qw.post.domain.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.post.vo.TagVo;
import com.rpy.qw.utils.PageVo;

import java.util.List;

public interface TagService extends IService<Tag>{


    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    PageVo<Tag> getByPage(PageVo<Tag> pageVo);

    /**
     * 保存
     * @param tag
     */
    void saveTag(Tag tag);


    /**
     * 删除
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 更新
     * @param tag
     */
    void updateTag(Tag tag);


    /**
     * 批量删除
     * @param ids
     */
    void batchDel(List<Integer> ids);

    List<TagVo> getListByCategroyId(Integer categoryId);

    List<TagVo> getList();

}
