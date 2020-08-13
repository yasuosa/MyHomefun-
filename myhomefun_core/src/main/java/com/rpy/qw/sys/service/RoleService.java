package com.rpy.qw.sys.service;

import com.rpy.qw.sys.domain.Role;
import com.rpy.qw.sys.vo.RoleAndMenu;
import com.rpy.qw.utils.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RoleService extends IService<Role> {


    /**
     * 分页查询
     *
     * @param pageVo
     * @return
     */
    PageVo<Role> getByPage(PageVo<Role> pageVo);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 添加角色
     *
     * @param role
     */
    void saveRole(Role role);


    /**
     * 不分页获取所有可用role
     *
     * @return
     */
    List<Role> getByList();


    /**
     * 根据 角色id获取权限id
     * @param id
     * @return
     */
    List<Integer> getMenuIdsAndPermissionIdsByRoleId(Integer id);

    /**
     * 分配权限和菜单
     * @param roleAndMenu
     */
    void dispatchPermissionAndMenu(RoleAndMenu roleAndMenu);

    /**
     * 修改
     * @param role
     */
    void updateByRole(Role role);

    /**
     * 根据userid查询权限名称
     * @param userId
     * @return
     */
    List<String> queryRoleNamesByUserId(Integer userId);
}

