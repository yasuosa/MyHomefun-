package com.rpy.qw.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.post.domain.Banner;
import com.rpy.qw.post.mapper.PostMapper;
import com.rpy.qw.post.service.CommentService;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.ShiroUtils;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.post.vo.CommentVo;
import com.rpy.qw.post.domain.Comment;
import com.rpy.qw.post.mapper.CommentMapper;
import com.rpy.qw.sys.mapper.UserMapper;
import com.rpy.qw.utils.UserDataUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserDataUtils userDataUtils;

    @Override
    public PageVo<CommentVo> getListPage(PageVo<CommentVo> pageVo) {
//        System.out.println(isLogin);
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        String postId = (String) pageVo.getParams().get("postId");
        Integer userId = (Integer) pageVo.getParams().get("userId");

        queryWrapper.eq(StringUtils.isNotBlank(postId),"post_id", postId);
        queryWrapper.eq(null !=userId,"userId",userId);
        queryWrapper.orderByAsc("created_time");
        IPage<Comment> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        commentMapper.selectPage(page,queryWrapper);
        List<Comment> records = page.getRecords();
        List<CommentVo> data=new ArrayList<>();
        records.forEach(e ->{
            CommentVo commentVo=new CommentVo();
            BeanUtils.copyProperties(e,commentVo);

            // 查询头像
            commentVo.setHeader(userMapper.selectHeaderByUserId(e.getUserId()));
            // 查询nickname
            commentVo.setNickname(userMapper.selectNickNameByUserId(e.getUserId()));
            // 查询是否点赞了
            if(userDataUtils.isLogin()) {
                int count = commentMapper.selectGoodComment(e.getId(), userDataUtils.getId());
                System.out.println(count);
                commentVo.setGood(count == 1);
            }
            data.add(commentVo);
        });
        pageVo.setData(data);
        pageVo.setTotal(page.getTotal());
        pageVo.setPageNum(page.getPages());

        return pageVo;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(Comment comment) {
        String postId = comment.getPostId();
        if(StringUtils.isBlank(postId)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"帖子id不为空");
        }
        int allow=postMapper.selectAllowCommentByPostId(postId);
        if(StateEnum.DENY_COMMENT.getCode() == allow){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前帖子禁止评论");
        }
        comment.setUserId(ShiroUtils.getUser().getUserId());
        comment.setUsername(ShiroUtils.getName());
        commentMapper.insert(comment);

        // 帖子评论数加1
        postMapper.updateCommentNumByPostId(comment.getPostId(),1);
        //TODO  评论加分(第一次)
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String goodsComment(Integer commentId) {
        Integer userId = ShiroUtils.getUser().getUserId();
        // 先查询有无点赞
        int count=commentMapper.selectGoodComment(commentId,userId);
        if(0 == count){
            // 添加一条记录
            commentMapper.insertGoodComment(commentId,userId);
            // 点赞数加1
            Comment comment = commentMapper.selectById(commentId);
            comment.setGoods(comment.getGoods()+1);
            commentMapper.updateById(comment);
            return "点赞成功";

        }else{
            // 删除这条记录
            commentMapper.deleteGoodComment(commentId,userId);
            // 点赞数见1
            Comment comment = commentMapper.selectById(commentId);
            comment.setGoods(comment.getGoods()-1);
            commentMapper.updateById(comment);
            return "取消成功";
        }
    }

    @Override
    public PageVo<CommentVo> getMyComment(PageVo<CommentVo> pageVo) {
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("userId",ShiroUtils.getId());
        queryWrapper.orderByDesc("created_time");
        IPage<Comment> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        commentMapper.selectPage(page,queryWrapper);

        List<Comment> records = page.getRecords();
        List<CommentVo> data=new ArrayList<>();
        records.forEach(e ->{
            CommentVo commentVo=new CommentVo();
            BeanUtils.copyProperties(e,commentVo);
            commentVo.setPostTitle(postMapper.selectPostTitleByPostId(e.getPostId()));
            data.add(commentVo);
        });
        pageVo.setData(data);
        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        return pageVo;

    }

    @Override
    public PageVo<Comment> getByPage(PageVo<Comment> pageVo) {
        String postId = (String) pageVo.getParams().get("postId");
        String username = (String) pageVo.getParams().get("username");
        String comment = (String) pageVo.getParams().get("comment");
        QueryWrapper<Comment> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(postId),"post_id",postId).
                like(StringUtils.isNoneBlank(username),"username",username)
                .like(StringUtils.isNoneBlank(comment),"comment",comment);
        IPage<Comment> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        // 排序
        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();
        if(StringUtils.isNotBlank(sortColumn)){
            if(StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())){
                queryWrapper.orderByAsc(sortColumn);
            }else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }
        commentMapper.selectPage(page,queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(page.getRecords());
        return pageVo;
    }
}
