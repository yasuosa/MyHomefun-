package com.rpy.qw.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.post.service.TypeService;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.post.domain.Type;
import com.rpy.qw.post.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {

    @Autowired
    private TypeMapper typeMapper;


    @Override
    public PageVo<Type> getByPage(PageVo<Type> pageVo) {
        Map<String, Object> params = pageVo.getParams();
        String name = (String) params.get("typeName");
        QueryWrapper<Type> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name),"type_name",name);

        IPage<Type> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
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

        typeMapper.selectPage(page, queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(page.getRecords());
        return pageVo;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveType(Type type) {
        String name = type.getTypeName();
        if(StringUtils.isBlank(name)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"分类名称不为空!");
        }
        QueryWrapper<Type> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type_name",name);
        Type old = typeMapper.selectOne(queryWrapper);
        if(null !=old){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"分类名称已存在!");
        }
        typeMapper.insert(type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateType(Type type) {
        Integer id = type.getTypeId();
        String name = type.getTypeName();
        if(null == id){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"分类id不为空!");
        }
        if(StringUtils.isBlank(name)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"分类名称不为空!");
        }
        QueryWrapper<Type> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type_name",name);
        queryWrapper.notIn("type_id",id);
        Type old = typeMapper.selectOne(queryWrapper);
        if(null !=old){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"分类名称已存在!");
        }
        typeMapper.updateById(type);
    }

    @Override
    public void deleteById(Integer id) {
        //TODO 是否有帖子
        typeMapper.deleteById(id);
    }

    @Override
    public void enable(Integer id) {
        Type type=new Type();
        type.setTypeId(id);
        type.setEnable(StateEnum.ENABLE.getCode());
        typeMapper.updateById(type);
    }

    @Override
    public void disable(Integer id) {
        Type type=new Type();
        type.setTypeId(id);
        type.setEnable(StateEnum.DISABLE.getCode());
        typeMapper.updateById(type);
    }

    @Override
    public List<Type> getList() {
        QueryWrapper<Type> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("enable",StateEnum.ENABLE.getCode());
        return typeMapper.selectList(queryWrapper);
    }
}
