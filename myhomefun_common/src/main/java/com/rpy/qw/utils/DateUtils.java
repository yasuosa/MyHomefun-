package com.rpy.qw.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: myfunhome
 * @description: 日期转化
 * @author: 任鹏宇
 * @create: 2020-06-21 19:03
 **/
public class DateUtils {

    private static final SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");


    /**
     * 获取日期
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date getTime(String date) throws ParseException {
        return  format.parse(date);
    }

}
