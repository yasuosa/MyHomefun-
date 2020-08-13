package com.rpy.qw.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.sys.domain.Role;
import com.rpy.qw.sys.mapper.RoleMapper;
import com.rpy.qw.sys.service.RoleService;
import com.rpy.qw.sys.vo.RoleAndMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
public class
RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageVo<Role> getByPage(PageVo<Role> pageVo) {
        String name = (String) pageVo.getParams().get("name");
        String remark = (String) pageVo.getParams().get("remark");
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                .like(StringUtils.isNotBlank(remark), "remark", remark)
                .eq("deleted", StateEnum.DELETED_FALSE.getCode());
        // 排序
        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();
        if (StringUtils.isNotBlank(sortColumn)) {
            if (StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())) {
                queryWrapper.orderByAsc(sortColumn);
            } else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }

        IPage<Role> page = new Page<>(pageVo.getCurrentPage(), pageVo.getPageSize());

        roleMapper.selectPage(page, queryWrapper);
        List<Role> records = page.getRecords();


        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(records);

        return pageVo;
    }

    @Override
    public void deleteById(Integer id) {

        //TODO
        Role role = new Role();
        role.setId(id);
        role.setDeleted(StateEnum.DELETED_TRUE.getCode());
        roleMapper.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRole(Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        String name = role.getName();
        if(StringUtils.isNotBlank(name)) {
            queryWrapper.eq("name", name);
            queryWrapper.notIn(null != role.getId(), "id", role.getId());
            Role oldRole = roleMapper.selectOne(queryWrapper);
            if (null != oldRole) {
                throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "当前角色已存在");
            }
            roleMapper.insert(role);
        }else {
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请填写当前角色名");
        }
    }

    @Override
    public List<Role> getByList() {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available",StateEnum.ENABLE.getCode());
        queryWrapper.eq("deleted",StateEnum.DELETED_FALSE.getCode());
        List<Role> roles = roleMapper.selectList(queryWrapper);
        return roles;
    }

    @Override
    public List<Integer> getMenuIdsAndPermissionIdsByRoleId(Integer id) {
        List<Integer> pids=roleMapper.queryMenuIdsAndPermissionIdsByRoleId(id);
        return pids;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dispatchPermissionAndMenu(RoleAndMenu roleAndMenu) {
        // 先删除
        roleMapper.deleteMenuAndPermisssionIdsByRole(roleAndMenu.getRid());
        if(null ==roleAndMenu.getMids() || roleAndMenu.getMids().size()==0){
            return;
        }
        // 在添加
        roleMapper.insertMenuAndPermisssionIdsByRole(roleAndMenu);

    }




    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateByRole(Role role) {
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        String name = role.getName();
        if(StringUtils.isNotBlank(name)) {
            queryWrapper.eq("name", name);
            queryWrapper.notIn(null != role.getId(), "id", role.getId());
            Role old = roleMapper.selectOne(queryWrapper);
            if (null != old) {
                throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "当前角色名已存在！");
            }
            roleMapper.updateById(role);
        }else {
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请填写当前角色名");
        }
    }

    @Override
    public List<String> queryRoleNamesByUserId(Integer userId) {
        //根据userid查询rid
        List<Integer> rids=roleMapper.queryRoleIdByUid(userId);
        if(null ==rids ||rids.size()==0){
            return new ArrayList<>();
        }
        //根据rid查询名字
        QueryWrapper<Role> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", StateEnum.ENABLE.getCode());
        queryWrapper.in("id",rids);
        List<Role> roles=roleMapper.selectList(queryWrapper);
        List<String> names=new ArrayList<>();
        for (Role role : roles) {
            names.add(role.getName());
        }
        return names;

    }

}

