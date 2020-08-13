package com.rpy.qw.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.post.service.TagService;
import com.rpy.qw.post.vo.TagVo;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.post.domain.Category;
import com.rpy.qw.post.mapper.CategoryMapper;
import com.rpy.qw.post.domain.Tag;
import com.rpy.qw.post.mapper.TagMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public PageVo<Tag> getByPage(PageVo<Tag> pageVo) {

        Map<String, Object> params = pageVo.getParams();
        String name = (String) params.get("name");
        Integer cId = (Integer) params.get("cid");
        QueryWrapper<Tag> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name),"name",name);
        queryWrapper.eq(null!=cId,"c_id",cId);
        IPage<Tag> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
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

        tagMapper.selectPage(page, queryWrapper);

        List<Tag> records = page.getRecords();
        // 数量太小 不链表查了
        records.forEach( e -> {
            Integer cId1 = e.getCId();
            QueryWrapper<Category> q=new QueryWrapper<>();
            q.eq("id",cId1);
            Category category = categoryMapper.selectOne(q);
            e.setCName(category.getName());

        });
        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(records);
        return pageVo;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTag(Tag tag) {
        // 查询该专栏下 有无相同的标签名字
        String name = tag.getName();
        Integer cId = tag.getCId();
        if(StringUtils.isBlank(name)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前标签名不为空");
        }
        if(null ==cId){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请填写标签所在专栏");
        }
        QueryWrapper<Tag> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        queryWrapper.eq("c_id",cId);
        Tag old = tagMapper.selectOne(queryWrapper);

        if(null !=old){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前专栏下已存在此标签");
        }
        tagMapper.insert(tag);

    }

    @Override
    public void deleteById(Integer id) {
        //TODO 删除post和 tag 的中间表
        tagMapper.deleteById(id);
    }

    @Override
    public void updateTag(Tag tag) {
        // 查询该专栏下 有无相同的标签名字
        String name = tag.getName();
        Integer cId = tag.getCId();
        Integer id = tag.getId();
        if(StringUtils.isBlank(name)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前标签名不为空");
        }
        if(null ==cId){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请填写标签所在专栏");
        }
        if(null ==id){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请填写标签的Id");
        }
        QueryWrapper<Tag> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        queryWrapper.eq("c_id",cId);
        queryWrapper.notIn("id",id);
        Tag old = tagMapper.selectOne(queryWrapper);
        if(null !=old){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前专栏下已存在此标签");
        }
        tagMapper.updateById(tag);
    }

    @Override
    public void batchDel(List<Integer> ids) {
        if(null == ids || ids.size()<=1) {
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请选择删除标签(至少2条)");
        }
        ids.forEach(id ->{
            deleteById(id);
        });
    }

    @Override
    public List<TagVo> getListByCategroyId(Integer caregoryId) {
        if(null == caregoryId){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"参数不合法");
        }
        QueryWrapper<Tag> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("c_id",caregoryId);
        List<Tag> tags = tagMapper.selectList(queryWrapper);
        List<TagVo> data=new ArrayList<>();
        tags.forEach(i ->{
            TagVo tagVo=new TagVo();
            BeanUtils.copyProperties(i,tagVo);
            tagVo.setCount(tagMapper.selectConutByPostAndTag(i.getId()));
            data.add(tagVo);
        });
        return  data.stream().sorted(new Comparator<TagVo>() {
            @Override
            public int compare(TagVo o1, TagVo o2) {
                return o2.getCount()-o1.getCount();
            }
        }).collect(Collectors.toList());
    }

    @Override
    public List<TagVo> getList() {
        List<Tag> tags = tagMapper.selectList(null);
        List<TagVo> data=new ArrayList<>();
        tags.forEach(i ->{
            TagVo tagVo=new TagVo();
            BeanUtils.copyProperties(i,tagVo);
            tagVo.setCount(tagMapper.selectConutByPostAndTag(i.getId()));
            data.add(tagVo);
        });
        return  data.stream().sorted(new Comparator<TagVo>() {
            @Override
            public int compare(TagVo o1, TagVo o2) {
                return o2.getCount()-o1.getCount();
            }
        }).collect(Collectors.toList());
    }
}
