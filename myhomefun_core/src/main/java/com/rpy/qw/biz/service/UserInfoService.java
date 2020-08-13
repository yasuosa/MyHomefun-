package com.rpy.qw.biz.service;

import com.rpy.qw.biz.domain.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.utils.PageVo;

public interface UserInfoService extends IService<UserInfo>{


    /**
     * 分页查询
     * @param pageVo
     * @return
     */
    PageVo<UserInfo> getByPage(PageVo<UserInfo> pageVo);

    /**
     * 更新
     * @param userInfo
     */
    void updateInfo(UserInfo userInfo);

    void addAccumulatePoints(String username,Double points);


    /**
     * 签到的积分加分规则
     * @param username
     * @param total
     */
    Double signInAddPoints(String username,Integer total);


    /**
     * 得到自己的userinfo
     * @return
     */
    UserInfo getMyUserInfo();


}
