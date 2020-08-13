package com.rpy.qw.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.ShiroUtils;
import com.rpy.qw.utils.StringUtils;

import com.rpy.qw.sys.domain.Menu;
import com.rpy.qw.sys.mapper.MenuMapper;
import com.rpy.qw.sys.mapper.RoleMapper;
import com.rpy.qw.sys.service.MenuService;
import com.rpy.qw.utils.menu.MenuNode;
import com.rpy.qw.utils.menu.MenuNodeBuilder;
import com.rpy.qw.utils.menu.MenuParams;
import com.rpy.qw.utils.menu.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;
import sun.awt.SunHints;

import javax.annotation.PostConstruct;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class
MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;





    @Override
    public List<MenuNode> getTopMenuList() {
        // 查询当前用户的角色
        List<Integer> rids = roleMapper.queryRoleIdByUid(ShiroUtils.getId());
        // 查询菜单
        List<Integer> menuIds = roleMapper.queryMenuIdsByRoleIds(rids);

        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("type", StateEnum.TYPECODE_TOPMENU.getMsg());
        queryWrapper.in(menuIds.size()!=0,"id",menuIds);
        queryWrapper.orderByAsc("ordernum");
        // 查询可用
        queryWrapper.eq("available",StateEnum.ENABLE.getCode());
        queryWrapper.orderByAsc("ordernum");
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        return MenuNodeBuilder.conversionMenuToTopMenu(menus);
    }

    @Override
    public List<MenuNode> getLeftMenu(Integer parentId) {
        // 查询当前用户的角色
        List<Integer> rids = roleMapper.queryRoleIdByUid(ShiroUtils.getId());
        // 查询菜单
        List<Integer> menuIds = roleMapper.queryMenuIdsByRoleIds(rids);
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        queryWrapper.notIn("pid",StateEnum.TYPECODE_TOPMENU.getCode());
        queryWrapper.in(menuIds.size()!=0,"id",menuIds);
        queryWrapper.eq("type",StateEnum.TYPECODE_LEFTMENU.getMsg());
        // 查询可用
        queryWrapper.eq("available",StateEnum.ENABLE.getCode());
        queryWrapper.orderByAsc("ordernum");
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        return MenuNodeBuilder.conversionMenuToTreeMenuVo(menus,parentId);
    }

    @Override
    public List<MenuNode> getMenuList(MenuParams params) {
        String title = params.getTitle();
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        // 查询可用
        queryWrapper.notIn("type",StateEnum.TYPECODE_PERMISSSION.getMsg());
        queryWrapper.orderByAsc("ordernum");
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        // 如果条件查询到某一个菜单 连子菜单一起查询出来
        if(StringUtils.isNotBlank(title)){
            List<MenuNode> data=new ArrayList<>();
            queryWrapper.like("title",title);
            List<Menu> searchMenus = menuMapper.selectList(queryWrapper);
            // 带有子节点搜索
             Boolean isHasChildrens = params.getIsHasChildrens();
             if(isHasChildrens){
                 // 分别对各菜单进行转换
                 searchMenus=MenuNodeBuilder.expellentChildNodeList(searchMenus);
                 for (Menu searchMenu : searchMenus) {
                     data.addAll(MenuNodeBuilder.conversionMenuToTreeMenuVoIncludeMySelf(menus,searchMenu));
                 }
                 return data;
             }else {
                 return MenuNodeBuilder.conversionMenuListToMenuVoListNoChildren(searchMenus);
             }
        }else{
            // 总查询
            return MenuNodeBuilder.conversionMenuToTreeMenuVo(menus,StateEnum.TYPECODE_TOPMENU.getCode());
        }

    }

    @Override
    public Integer getNextOrderNum() {
        return menuMapper.selectTopOrderNum()+1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMenu(MenuNode menuNode) {
        // 判断当前是否有名字 相同
        checkNotHasSameMenu(menuNode);
        menuMapper.insert(MenuUtils.conversionMenuNodeToMenu(menuNode));
    }

    @Override
    public void deleteById(Integer id) {
        Integer count=menuMapper.queryByParentId(id);
        if(null != count && count!=0){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前菜单存在子菜单(或权限)！");
        }
        List<Integer> rids=roleMapper.queryRoleIdsByMid(id);
        if(rids.size()>0){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前菜单已被分配！");
        }
        menuMapper.deleteById(id);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateById(MenuNode menuNode) {
        // 判断当前是否有名字 相同
        checkNotHasSameMenu(menuNode);
        menuMapper.updateById(MenuUtils.conversionMenuNodeToMenu(menuNode));
    }

    @Override
    public PageVo<MenuNode> getPermissionList(PageVo<MenuNode> pageVo) {
        Map<String, Object> params = pageVo.getParams();
        String title = (String) params.get("title");
        String typecode = (String) params.get("typecode");
        Integer parentId = (Integer) params.get("parentId");
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        // 查询可用
        queryWrapper.like(StringUtils.isNotBlank(title),"title",title);
        queryWrapper.like(StringUtils.isNotBlank(typecode),"typecode",typecode);
        queryWrapper.eq(null !=parentId,"pid",parentId);
        queryWrapper.eq("type",StateEnum.TYPECODE_PERMISSSION.getMsg());
        queryWrapper.eq("available",StateEnum.ENABLE.getCode());
        queryWrapper.orderByAsc("ordernum");
        IPage<Menu> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();
        if(StringUtils.isNotBlank(sortColumn)){
            if(StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())){
                queryWrapper.orderByAsc(sortColumn);
            }else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }
        menuMapper.selectPage(page, queryWrapper);
        // 如果条件查询到某一个菜单 连子菜单一起查询出来
        List<MenuNode> menuNodes = MenuNodeBuilder.conversionMenuListToMenuVoListNoChildren(page.getRecords());
        menuNodes.forEach(e -> {
            Menu menu = menuMapper.selectById(e.getParentId());
            e.setParentName(menu.getTitle());
        });
        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(menuNodes);
        return pageVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void savePermission(MenuNode menuNode) {
        checkNotHasSamePermission(menuNode);
        menuNode.setType(StateEnum.TYPECODE_PERMISSSION.getMsg());
        menuMapper.insert(MenuUtils.conversionMenuNodeToMenu(menuNode));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(MenuNode menuNode) {
        checkNotHasSamePermission(menuNode);
        menuNode.setType(StateEnum.TYPECODE_PERMISSSION.getMsg());
        menuMapper.updateById(MenuUtils.conversionMenuNodeToMenu(menuNode));
    }

    @Override
    public void deletePermissionById(Integer id) {
        List<Integer> rids=roleMapper.queryRoleIdsByMid(id);
        if(rids.size()>0){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前权限已被分配！");
        }
        menuMapper.deleteById(id);
    }

    @Override
    public List<MenuNode> getAll() {
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available",StateEnum.ENABLE.getCode());
        queryWrapper.orderByAsc("ordernum");
        List<Menu> menus = menuMapper.selectList(queryWrapper);
        return MenuNodeBuilder.conversionMenuToTreeMenuVo(menus,StateEnum.TYPECODE_TOPMENU.getCode());
    }

    @Override
    public List<String> queryPermissionCodeByUserId(Integer userId) {
        // 查询角色ids
        List<Integer> roleIds = roleMapper.queryRoleIdByUid(userId);
        if(null ==roleIds || roleIds.size() ==0){
            return new ArrayList<>();
        }
        // 查询当前角色下的菜单
        List<Integer> menudIds = roleMapper.queryMenuIdsByRoleIds(roleIds);
        if(null ==menudIds || menudIds.size() ==0){
            return  new ArrayList<>();
        }
        QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available",StateEnum.ENABLE.getCode());
        queryWrapper.eq("type",StateEnum.TYPECODE_PERMISSSION.getMsg());
        queryWrapper.in("id",menudIds);
        queryWrapper.orderByAsc("ordernum");
        List<Menu> menus=menuMapper.selectList(queryWrapper);
        List<String> permissions=new ArrayList<>();
        for (Menu menu : menus) {
            permissions.add(menu.getTypecode());
        }
        return permissions;
    }



    /***
     * 检查有无相同菜单
     * @param menuNode
     */
    private void checkNotHasSameMenu(MenuNode menuNode){
        String label = menuNode.getLabel();
        if(StringUtils.isNotBlank(label)) {
            QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("pid", menuNode.getParentId());
            queryWrapper.eq("title", label);
            queryWrapper.eq("type", menuNode.getType());
            queryWrapper.notIn(null != menuNode.getId(), "id", menuNode.getId());
            Menu menu = menuMapper.selectOne(queryWrapper);
            if (null != menu) {
                throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "当前父菜单下有相同命名菜单！");
            }
        }else {
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "请填写菜单名称！");
        }
    }


    /**
     * 检查有无相同权限
     * @param menuNode
     */
    private void checkNotHasSamePermission(MenuNode menuNode){
        Menu menu =null;
        // 权限名
        String label = menuNode.getLabel();
        if(StringUtils.isNotBlank(label)) {
            QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("pid", menuNode.getParentId());
            queryWrapper.eq("title", label);
            queryWrapper.eq("type", StateEnum.TYPECODE_PERMISSSION.getMsg());
            queryWrapper.notIn(null != menuNode.getId(), "id", menuNode.getId());
            menu = menuMapper.selectOne(queryWrapper);
            if (null != menu) {
                throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "当前对应菜单下有相同【命名】权限！");
            }
        }else {
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "请填写权限名称！");
        }

        // 权限编码
        String typecode = menuNode.getTypecode();
        if(StringUtils.isNotBlank(typecode)) {
            QueryWrapper<Menu> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("pid", menuNode.getParentId());
            queryWrapper.eq("typecode", typecode);
            queryWrapper.eq("type", StateEnum.TYPECODE_PERMISSSION.getMsg());
            queryWrapper.notIn(null != menuNode.getId(), "id", menuNode.getId());
            menu = menuMapper.selectOne(queryWrapper);
            if (null != menu) {
                throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "当前对应菜单下有相同【权限编码】权限！");
            }
        }else {
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "请填写权限编码！");
        }
    }
}
