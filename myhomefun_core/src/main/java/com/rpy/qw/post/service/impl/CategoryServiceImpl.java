package com.rpy.qw.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.post.service.CategoryService;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.post.domain.Category;
import com.rpy.qw.post.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public PageVo<Category> getByPage(PageVo<Category> pageVo) {
        Map<String, Object> params = pageVo.getParams();
        String name = (String) params.get("name");
        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name),"name",name);

        IPage<Category> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();

        // 排序
        if(StringUtils.isNotBlank(sortColumn)){
            if(StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())){
                queryWrapper.orderByAsc(sortColumn);
            }else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }

        categoryMapper.selectPage(page, queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(page.getRecords());
        return pageVo;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveCategory(Category category) {
        String name = category.getName();
        if(StringUtils.isBlank(name)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"专栏名称不为空!");
        }
        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        Category old = categoryMapper.selectOne(queryWrapper);
        if(null !=old){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"专栏名称已存在!");
        }
        categoryMapper.insert(category);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(Category category) {
        Integer id = category.getId();
        String name = category.getName();
        if(null == id){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"专栏id不为空!");
        }
        if(StringUtils.isBlank(name)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"专栏名称不为空!");
        }
        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        queryWrapper.notIn("id",id);
        Category old = categoryMapper.selectOne(queryWrapper);
        if(null !=old){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"专栏名称已存在!");
        }
        categoryMapper.updateById(category);
    }

    @Override
    public void deleteById(Integer id) {
        //TODO 是否有帖子
        categoryMapper.deleteById(id);
    }

    @Override
    public void enable(Integer id) {
        Category category=new Category();
        category.setId(id);
        category.setEnable(StateEnum.ENABLE.getCode());
        categoryMapper.updateById(category);
    }

    @Override
    public void disable(Integer id) {
        Category category=new Category();
        category.setId(id);
        category.setEnable(StateEnum.DISABLE.getCode());
        categoryMapper.updateById(category);
    }

    @Override
    public List<Category> getList() {
        QueryWrapper<Category> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("enable",StateEnum.ENABLE.getCode());
        return categoryMapper.selectList(queryWrapper);
    }
}
