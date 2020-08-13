package com.rpy.qw.biz.utils;

import com.rpy.qw.enums.StateEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: myfunhome
 * @description: 流水工具类
 * @author: 任鹏宇
 * @create: 2020-07-28 19:47
 **/
public class JournalUtils {

    public final static Map<Integer,String> journalTypeMap=new HashMap<>();
    static {
        journalTypeMap.put(StateEnum.JOURNAL_TYPE_SHIN.getCode(),StateEnum.JOURNAL_TYPE_SHIN.getMsg());
        journalTypeMap.put(StateEnum. JOURNAL_TYPE_BUY.getCode(),StateEnum.JOURNAL_TYPE_BUY.getMsg());
        journalTypeMap.put(StateEnum. JOURNAL_TYPE_EARN.getCode(),StateEnum.JOURNAL_TYPE_EARN.getMsg());
        journalTypeMap.put(StateEnum. JOURNAL_TYPE_COMMENT.getCode(),StateEnum.JOURNAL_TYPE_COMMENT.getMsg());
        journalTypeMap.put(StateEnum. JOURNAL_TYPE_PUBLISH.getCode(),StateEnum.JOURNAL_TYPE_PUBLISH.getMsg());
    }

    /**
     * 获取流水类型
     * @param key
     * @return
     */
    public static String getJournalTypeName(Integer key){
        return journalTypeMap.get(key);
    }
}
