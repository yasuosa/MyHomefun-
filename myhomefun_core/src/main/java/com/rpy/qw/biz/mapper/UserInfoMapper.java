package com.rpy.qw.biz.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.qw.biz.domain.UserInfo;

public interface UserInfoMapper extends BaseMapper<UserInfo> {
    Integer selectVersionById(Integer id);

    UserInfo selectUserInfoByUserName(String username);


    /**
     * 根据userId查询积分
     * @param userId
     * @return
     */
    Double selectPointsByUserId(Integer userId);
}