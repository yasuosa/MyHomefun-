package com.rpy.qw.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.qw.sys.domain.User;
import com.rpy.qw.sys.vo.UserAndRoleVo;

import java.util.List;

public interface UserMapper extends BaseMapper<User> {
    /**
     * 插入用户和角色表
     *
     * @param userAndRoleVo
     */
    void insertUserAndRole(UserAndRoleVo userAndRoleVo);

    /**
     * 删除用户和角色
     *
     * @param userId
     */
    void deleteUserAndRoleByUserId(Integer userId);

    /**
     * 根据userid 查询用户的角色
     *
     * @param userId
     * @return
     */
    List<UserAndRoleVo> selectUserAndRoleByUserId(Integer userId);


    /**
     * 根据id查询nickname
     * @param userId
     * @return
     */
    String selectNickNameByUserId(Integer userId);

    /**
     * 根据id查询头像
     * @param userId
     * @return
     */
    String selectHeaderByUserId(Integer userId);
}