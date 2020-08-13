package com.rpy.qw.sys.service;


import com.rpy.qw.sys.domain.Menu;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.menu.MenuNode;
import com.rpy.qw.utils.menu.MenuParams;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MenuService extends IService<Menu>{


    /**
     * 得到顶部菜单
     * @return
     */
    List<MenuNode> getTopMenuList();


    /**
     * 得到左部菜单
     * @param parentId
     * @return
     */
    List<MenuNode> getLeftMenu(Integer parentId);


    /**
     * 得到菜单
     * @param params
     * @return
     */
    List<MenuNode> getMenuList(MenuParams params);


    /**
     * 获取下一个排序码
     * @return
     */
    Integer getNextOrderNum();


    /**
     * 添加菜单
     * @param menuNode
     */
    void saveMenu(MenuNode menuNode);


    /**
     * 根据id删除
     * @param id
     */
    void deleteById(Integer id);


    /**
     * 菜单更新
     * @param menuNode
     */
    void updateById(MenuNode menuNode);


    /**
     * 权限的添加
     * @param pageVo
     * @return
     */
    PageVo<MenuNode> getPermissionList(PageVo<MenuNode> pageVo);


    /**
     * 保存权限
     * @param menuNode
     */
    void savePermission(MenuNode menuNode);

    /**
     * 更新权限
     * @param menuNode
     */
    void updatePermission(MenuNode menuNode);

    /**
     * 删除权限
     * @param id
     */
    void deletePermissionById(Integer id);

    /**
     * 得到所有包括（菜单和权限)
     * @return
     */
    List<MenuNode> getAll();

    /**
     * 根据userid 查询权限id
     * @param userId
     * @return
     */
    List<String> queryPermissionCodeByUserId(Integer userId);
}
