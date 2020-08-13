package com.rpy.qw.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.qw.post.domain.Auditing;import com.rpy.qw.post.vo.AuditingVo;
import com.rpy.qw.utils.PageVo;
import org.apache.ibatis.annotations.Param;import java.util.List;

public interface AuditingMapper extends BaseMapper<Auditing> {
    /**
     * 连表分页查询
     *
     * @param pageVo
     * @return
     */
    List<AuditingVo> getByPage(@Param("page") PageVo<AuditingVo> pageVo);


    /**
     * 根据audtingId 查postId
     * @param id
     * @return
     */
    String selectPostIdByAuditingId(String id);


    /**
     * 查询总条数
     * @param pageVo
     * @return
     */
    long selectTotalByPage(PageVo<AuditingVo> pageVo);
}