package com.rpy.qw.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.qw.post.domain.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 在当前专栏下根据name
     * @param tagNames
     * @return
     */
    Tag selectByTagName(@Param("cid") Integer postCategoryId, @Param("name") String tagNames);


    int insert(Tag tag);


    void insertPostAndTag(@Param("postId") String postId, @Param("tagId") Integer tagId);

    /**
     * 根据post删除 post和 tag中间表
     * @param postId
     */
    void deletePostAndTag(String postId);

    /**
     * 查询标签的贴子数
     * @param id
     * @return
     */
    Integer selectConutByPostAndTag(Integer id);

    /**
     * 根绝postid 查询tag
     * @param postId
     * @return
     */
    List<Tag> selectTagByPostId(String postId);
}