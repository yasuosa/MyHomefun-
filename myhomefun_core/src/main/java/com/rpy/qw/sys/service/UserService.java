package com.rpy.qw.sys.service;

import com.rpy.qw.sys.domain.User;
import com.rpy.qw.sys.vo.RegisterUserVo;
import com.rpy.qw.sys.vo.UserAndRoleVo;
import com.rpy.qw.sys.vo.UserHomeVo;
import com.rpy.qw.sys.vo.UserVo;
import com.rpy.qw.utils.PageVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

public interface UserService extends IService<User> {


    /**
     * 根据username 获取用户
     *
     * @param username
     * @return
     */
    User getByUserName(String username);


    /**
     * 后台分页查询
     *
     * @return
     */
    PageVo<UserVo> getByPage(PageVo<UserVo> pageVo);


    /**
     * 删除成功
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 更新用户
     *
     * @param user
     */
    void updateUser(User user);

    /**
     * 添加用户
     *
     * @param user
     */
    void saveUser(User user);


    /**
     * 用户角色分配
     *
     * @param userAndRoleVo
     */
    UserAndRoleVo dispatchRole(UserAndRoleVo userAndRoleVo);

    /**
     * 取消用户角色
     *
     * @param userId
     */
    void cancelRole(Integer userId);


    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    Map<String, Object> login(User user);


    /***
     * 获取角色id
     * @return
     */
    UserAndRoleVo getRoleId(Integer userId);

    /**
     * 用户首页信息查询
     * @param userId
     * @return
     */
    UserHomeVo getUserHome(Integer userId);

    /**
     * 修改密码
     * @param user
     */
    void modifyPassWord(User user);

    /**
     * 注册
     * @param userVo
     */
    void register(RegisterUserVo userVo);

}

