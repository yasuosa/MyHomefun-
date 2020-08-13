package com.rpy.qw.biz.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @program: myfunhome
 * @description: 当月签到情况
 * @author: 任鹏宇
 * @create: 2020-07-18 13:28
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignMsgVo  implements Serializable {



    // 当月签到的日子
    private List<Integer> signInDayOfMonth;

    // 总签到数
    private Integer total;

    // 年
    private Integer year;

    // 月
    private Integer month;
}
