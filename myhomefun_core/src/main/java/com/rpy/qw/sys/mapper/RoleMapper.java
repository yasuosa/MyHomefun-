package com.rpy.qw.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.qw.sys.domain.Role;
import com.rpy.qw.sys.vo.RoleAndMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {


    /**
     * 通过角色id 查询 所有菜单
     *
     * @param id
     * @return
     */
    List<Integer> queryMenuIdsAndPermissionIdsByRoleId(Integer id);

    /**
     * 根绝 角色id 删除 角色和菜单关系
     *
     * @param rid
     */
    void deleteMenuAndPermisssionIdsByRole(Integer rid);


    /**
     * 根绝 角色id 添加 角色和菜单关系
     *
     * @param roleAndMenu
     */
    void insertMenuAndPermisssionIdsByRole(RoleAndMenu roleAndMenu);


    /**
     * 根据mid查询rids
     *
     * @param id
     * @return
     */
    List<Integer> queryRoleIdsByMid(Integer id);

    /**
     * 根基userid 查询Roleid
     *
     * @param userId
     * @return
     */
    List<Integer> queryRoleIdByUid(@Param("userId") Integer userId);

    /**
     * 根据 roleids 查询菜单id
     *
     * @param roleIds
     * @return
     */
    List<Integer> queryMenuIdsByRoleIds(@Param("rids") List<Integer> roleIds);
}