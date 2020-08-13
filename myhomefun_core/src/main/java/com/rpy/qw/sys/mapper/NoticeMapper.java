package com.rpy.qw.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.qw.sys.domain.Notice;

import java.util.List;

public interface NoticeMapper extends BaseMapper<Notice> {
    List<Notice> getTwoHomeNotice();

}