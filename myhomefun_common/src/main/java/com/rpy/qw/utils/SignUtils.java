package com.rpy.qw.utils;

import cn.hutool.core.date.DateUtil;
import com.rpy.qw.data.BizStaticData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @program: myfunhome
 * @description: 签到管理的工具类
 * @author: 任鹏宇
 * @create: 2020-07-13 21:28
 **/
public class SignUtils {



    /**
     * 判断日子是不是当月的首日
     * @param date
     * @return
     */
    public static boolean isMonthBeginDay(Date date){
        return DateUtil.isSameDay(date,DateUtil.beginOfMonth(new Date()));
    }

    /**
     * 签到
     * @return
     */
    public static Integer sign(Integer mask) {
        return ((null != mask ? mask:0) | (BizStaticData.SIGN_CONTINUE_DAY_NUMBER_FIRST << DateUtil.dayOfMonth(new Date())-1));
    }

    /**
     * 判断今天有没有签到
     * @param mask
     * @return
     */
    public static boolean isTodaySign(Integer mask) {
        return ((null != mask ? mask:0) & (BizStaticData.SIGN_CONTINUE_DAY_NUMBER_FIRST << DateUtil.dayOfMonth(new Date())-1)) != 0;
    }



    private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
    public static String getYeayMonth(Date dateMonth) {
        return format.format(dateMonth);
    }


    /**
     * 查询当前哪些天签到了
     * @param mask
     * @return
     */
    public static List<Integer> querySignInDayOfMonth(Integer mask) {
        List<Integer> signInDay=new ArrayList<>();
        char[] chars = Integer.toBinaryString(mask).toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if(chars[i] == '1'){
                signInDay.add(chars.length-i);
            }
        }

        return signInDay;
    }

    /**
     * 查询一共签到几天
     * @param mask
     * @return
     */
    public static Integer querySignInDayTotal(Integer mask) {
        return querySignInDayOfMonth(mask).size();
    }

    /**
     * 判断昨天是否签到
     * @param mask
     * @return
     */
    public static boolean isYerterDaySign(Integer mask) {
        return ((null != mask ? mask:0) & (BizStaticData.SIGN_CONTINUE_DAY_NUMBER_FIRST << DateUtil.dayOfMonth(new Date())-2)) != 0;
    }
}
