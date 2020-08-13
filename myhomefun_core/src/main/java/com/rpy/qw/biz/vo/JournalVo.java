package com.rpy.qw.biz.vo;

import com.rpy.qw.biz.domain.Journal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @program: myfunhome
 * @description: 流水
 * @author: 任鹏宇
 * @create: 2020-07-28 19:42
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JournalVo extends Journal {

    // 类型名称
    private String typeName;
}
