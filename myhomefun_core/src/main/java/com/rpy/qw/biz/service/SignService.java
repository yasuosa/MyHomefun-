package com.rpy.qw.biz.service;

import com.rpy.qw.biz.domain.Sign;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rpy.qw.biz.vo.SignMsgVo;
import com.rpy.qw.biz.vo.SignVo;
import com.rpy.qw.utils.PageVo;

import java.util.Map;

public interface SignService extends IService<Sign> {


    /**
     * 签到
     */
    Map<String,Object> sigIn();


    /**
     * 查询签到
     *
     * @param pageVo
     * @return
     */
    PageVo<Sign> getByPage(PageVo<Sign> pageVo);


    /**
     * 查询当月签到情况
     * @param username
     * @return
     */
    SignMsgVo getMonthSign(String username,String month);

    PageVo<SignVo> getTodayAllSign(PageVo<SignVo> pageVo);

    PageVo<SignVo> getContinueSignDay(PageVo<SignVo> pageVo);
}

