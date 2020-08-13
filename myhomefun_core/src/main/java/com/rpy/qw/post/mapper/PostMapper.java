package com.rpy.qw.post.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rpy.qw.post.domain.Post;
import com.rpy.qw.post.domain.PostHideContent;
import com.rpy.qw.post.vo.SimplePostVo;
import com.rpy.qw.utils.PageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PostMapper extends BaseMapper<Post> {

    /**
     * 插入隐藏资源
     * @param postHideContent
     */
    void insertPostHideContent(PostHideContent postHideContent);

    /**
     * 根据postid查询隐藏内容
     * @param postId
     * @return
     */
    PostHideContent selectPostHideContentBy(String postId);

    /**
     * 根据postid查询tags
     * @param postId
     * @return
     */
    List<String> selectTagNamsByPostId(String postId);


    /**
     * 根据postid删除隐藏内容
     * @param postId
     */
    void deleteHideContentByPostId(String postId);

    /**
     * 根据postid删除tag中间表
     * @param postId
     */
    void deleteTagNamsByPost(String postId);

    /**
     * 更新隐藏内容
     */
    void updateHideContentByPost(PostHideContent postHideContent);

    /**
     * 获取锁
     * @param postId
     * @return
     */
    Integer selectVersionByPost(String postId);


    /**
     * 分页查询前端公开的帖子
     * @param pageVo
     * @return
     */
    List<Post> getListPage(@Param("page") PageVo<Post> pageVo);


    /**
     * 查询（     * 前端）总条数
     * @param pageVo
     * @return
     */
    long selectTotalByListPage(@Param("page") PageVo<Post> pageVo);


    /**
     * 查询热门
     * @param categoryId
     * @return
     */
    List<Post> selectPostIdAndPostTitleOrderByReadAndCategortId(String categoryId);

    /**
     * 评论数加1
     * @param postId
     */
    void updateCommentNumByPostId(@Param("pid") String postId,@Param("nb") Integer number);

    /**
     * 查询用户行为帖子表是否有行为
     * @param userId
     * @param postId
     * @param type
     * @return
     */
    int selectCountUserAndPost(@Param("userId") Integer userId, @Param("postId") String postId, @Param("type") Integer type);

    /**
     * 添加用户对帖子行为
     * @param userId
     * @param postId
     * @param goodType
     */
    void insertUserAndPost(@Param("userId") Integer userId, @Param("postId") String postId, @Param("type") Integer goodType);


    /**
     * 删除用户对帖子行为
     * @param userId
     * @param postId
     * @param goodType
     */
    void deleteUserAndPost(@Param("userId") Integer userId, @Param("postId") String postId, @Param("type") Integer goodType);

    /**
     * 更新帖子的点赞数
     * @param postId
     * @param i
     */
    void updateGoodsByPostId(@Param("postId") String postId, @Param("value") int i,@Param("version") int version);

    void updateCollectNumByPostId(@Param("postId") String postId, @Param("value") int i,@Param("version") int version);


    /**
     * 查询帖子隐藏权限
     * @param postId
     * @return
     */
    String selectPostHideContentPermission(String postId);

    /**
     * 查询帖子隐藏花费
     * @param postId
     * @return
     */
    Double selectHideCostByPostId(String postId);


    List<String> selectPostIdByTagId(@Param("page") PageVo<Post> pageVo);

    long selectCountPostIdByTagId(@Param("page") PageVo<Post> pageVo);

    /**
     * 批量查询
     * @param postIds
     * @return
     */
    List<Post> selectPostInPostIds(@Param("pids") List<String> postIds);


    /**
     * 根据帖子查询是否可以评论
     * @param postId
     * @return
     */
    int selectAllowCommentByPostId(String postId);


    /**
     * 查询帖子
     * @param userId
     * @return
     */
    List<SimplePostVo> selectSimplePostByParams(@Param("userId") Integer userId, @Param("deleted") Integer code, @Param("isPublish") Integer code1);

    /**
     * 根绝postid查询文章标题
     * @param postId
     * @return
     */
    String selectPostTitleByPostId(String postId);

    /**
     * 分页拆查询用户操作帖子表
     * @param pageVo
     * @return
     */
    List<String> selectPostIdsUserDeepPostByPage(@Param("page") PageVo<SimplePostVo> pageVo);

    long selectTotalsUserDeepPostByPage(@Param("page")PageVo<SimplePostVo> pageVo);

    /**
     * 彻底删除帖子隐藏
     * @param postId
     */
    void deleteTrueHideContentByPostId(String postId);

    /**
     * 彻底删除帖子和标签的关联表数据 根据帖子id
     * @param postId
     */
    void deleteTrueTagNamsByPost(String postId);

    /**
     * 根据帖子id 回复
     * @param postId
     */
    void recycleHideContentByPostId(String postId);

    /**
     * 还原帖子和标签的关联表数据 根据帖子id
     * @param postId
     */
    void recycleTagNamsByPost(String postId);

    /**
     * 分页查询逻辑删除的帖子
     * @param pageVo
     * @return
     */
    List<SimplePostVo> selectSimplePostByParamsOfPage(@Param("page") PageVo<SimplePostVo> pageVo);

    long selectCountSimplePostByParamsOfPage(@Param("page") PageVo<SimplePostVo> pageVo);

    /**
     * 彻底删除 用户对帖子的操作
     * @param postId
     */
    void deleteTruePostUserDeed(String postId);
}