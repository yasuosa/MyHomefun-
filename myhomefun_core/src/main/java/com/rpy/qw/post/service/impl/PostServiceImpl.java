package com.rpy.qw.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.biz.domain.Goods;
import com.rpy.qw.biz.domain.Order;
import com.rpy.qw.biz.mapper.GoodsMapper;
import com.rpy.qw.biz.mapper.OrderMapper;
import com.rpy.qw.data.BizStaticData;
import com.rpy.qw.data.PostStaticData;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.utils.*;
import com.rpy.qw.post.mapper.AuditingMapper;
import com.rpy.qw.post.mapper.PostMapper;
import com.rpy.qw.post.service.PostService;
import com.rpy.qw.post.domain.*;
import com.rpy.qw.post.mapper.*;
import com.rpy.qw.post.vo.*;
import com.rpy.qw.sys.domain.User;
import com.rpy.qw.sys.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    @Autowired
    private PostMapper postMapper;


    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private AuditingMapper auditingMapper;

    @Autowired
    private TypeMapper typeMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private OrderMapper orderMapper;


    @Autowired
    private UserDataUtils userDataUtils;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PageVo<Post> getByPage(PageVo<Post> pageVo) {
        Map<String, Object> params = pageVo.getParams();
        // 帖子id
        String postId = (String) params.get("postId");
        // 专栏id
        Integer categoryId = (Integer) params.get("categoryId");
        // 分类id
        Integer typeId = (Integer) params.get("typeId");
        // 帖子标题
        String title = (String) params.get("postTitle");
        // 用户名
        String username = (String) params.get("username");
        // 是否公开
        Integer isPublic = (Integer) params.get("isPublic");
        // userId
        Integer userId = (Integer) params.get("userId");

        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper
                .like(StringUtils.isNotBlank(title),"post_title",title)
                .like(StringUtils.isNotBlank(username),"username",username)
                .eq("allow", StateEnum.ALLOW.getCode())
                .eq("deleted",StateEnum.DELETED_FALSE.getCode())
                .eq(null !=categoryId,"post_category_id",categoryId)
                .eq(null !=typeId,"post_type_id",typeId)
                .eq(StringUtils.isNotBlank(postId),"post_id",postId)
                .eq(null !=isPublic,"is_public",isPublic)
                .eq( null !=userId,"user_id",userId);

        IPage<Post> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();

        // 排序
        if(StringUtils.isNotBlank(sortColumn)){
            if(StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())){
                queryWrapper.orderByAsc(sortColumn);
            }else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }else{
            queryWrapper.orderByDesc("created_time");
        }

        postMapper.selectPage(page, queryWrapper);
        List<Post> records = page.getRecords();
        records.forEach( i ->{
            BeanUtils.copyProperties(i,pageVo);
            // 查询专栏名称
            i.setCategoryName(categoryMapper.selectById(i.getPostCategoryId()).getName());
            // 查询typeName 名称
            i.setTypeName(typeMapper.selectById(i.getPostTypeId()).getTypeName());

            // 插入tagnames
            i.setTagNames(postMapper.selectTagNamsByPostId(i.getPostId()));

            Integer isHasHideContent = i.getIsHasHideContent();
            if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_EXIST == isHasHideContent){
                PostHideContent hideContent=postMapper.selectPostHideContentBy(i.getPostId());
                i.setHideContent(hideContent.getResource());
                i.setPrice(hideContent.getPrice());
            }
        });
        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(records);
        return pageVo;
    }


    @Override
    public PostAuditingVo getAuditingDetailByAuditingId(String id) {
        String postId=auditingMapper.selectPostIdByAuditingId(id);

        // post表内数据 （
        Post post = postMapper.selectById(postId);
        // 返回类型
        PostAuditingVo postAuditingVo=new PostAuditingVo();
        BeanUtils.copyProperties(post,postAuditingVo);
        // 拿到分类id 设置分类名
        Integer postTypeId = post.getPostTypeId();
        postAuditingVo.setPostTypeName(typeMapper.selectById(postTypeId).getTypeName());
        // 拿到专类id 设置专栏名
        Integer postCategoryId = post.getPostCategoryId();
        postAuditingVo.setPostCategoryName(categoryMapper.selectById(postCategoryId).getName());
        // 判断有无隐藏
        Integer isHasHideContent = post.getIsHasHideContent();
        if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_EXIST.equals(isHasHideContent)){
            PostHideContent hideContent=postMapper.selectPostHideContentBy(postId);
            postAuditingVo.setHideContent(hideContent);
        }

        // 加入用户nickname
        Integer userId = post.getUserId();
        postAuditingVo.setNickname(userMapper.selectById(userId).getNickname());

        // 加入tags
        List<String> tagNamse=postMapper.selectTagNamsByPostId(postId);
        postAuditingVo.setTagNames(tagNamse);

        return postAuditingVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(String postId) {
        // 取锁
        Post post = postMapper.selectById(postId);
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("post_id",postId);
        queryWrapper.eq("version",post.getVersion());
        post.setDeleted(StateEnum.DELETED_TRUE.getCode());
        post.setVersion(post.getVersion()+1);
        postMapper.update(post,queryWrapper);

        // 逻辑删除 帖子隐藏内容
        if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_EXIST.equals(post.getIsHasHideContent())){
            postMapper.deleteHideContentByPostId(postId);

            // 商品型 逻辑删除商品
            if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_HAS_GOODS.equals(post.getPostHideContentPermission())){
                log.info("有商品");
                QueryWrapper<Goods> q=new QueryWrapper<>();
                q.eq("post_id",postId);
                Goods goods=new Goods();
                goods.setPostId(postId);
                goods.setDeleted(StateEnum.DELETED_TRUE.getCode());
                goodsMapper.update(goods,q);
            }
        }

        // 逻辑删除标签
        postMapper.deleteTagNamsByPost(postId);

        // 分类下的贴子数减1
        Integer postTypeId = post.getPostTypeId();
        Type type = typeMapper.selectById(postTypeId);
        type.setTypePostCount(type.getTypePostCount()-1);
        typeMapper.updateById(type);

        // 逻辑删除评论
        QueryWrapper<Comment> cQw=new QueryWrapper<>();
        cQw.eq("post_id",postId);
        cQw.eq("deleted",StateEnum.DELETED_FALSE.getCode());
        Comment comment=new Comment();
        comment.setPostId(postId);
        comment.setDeleted(StateEnum.DELETED_TRUE.getCode());
        commentMapper.update(comment,cQw);



    }

    @Override
    public void setTop(String postId) {
        // 取锁
        Post post = postMapper.selectById(postId);
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("post_id",postId);
        queryWrapper.eq("version",post.getVersion());
        // 开锁
        post.setIsTop(PostStaticData.POST_IS_TOP);
        post.setVersion(post.getVersion()+1);
        postMapper.update(post,queryWrapper);
    }

    @Override
    public void setPerfect(String postId) {
        // 取锁
        Post post = postMapper.selectById(postId);
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("post_id",postId);
        queryWrapper.eq("version",post.getVersion());
        // 开锁
        post.setIsPerfect(PostStaticData.POST_IS_PERFECT);
        post.setVersion(post.getVersion()+1);
        postMapper.update(post,queryWrapper);
    }

    @Override
    public void cancelPerfect(String postId) {
        // 取锁
        Post post = postMapper.selectById(postId);
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("post_id",postId);
        queryWrapper.eq("version",post.getVersion());
        // 开锁
        post.setIsPerfect(PostStaticData.POST_IS_NOT_PERFECT);
        post.setVersion(post.getVersion()+1);
        postMapper.update(post,queryWrapper);
    }

    @Override
    public void cancelTop(String postId) {
        // 取锁
        Post post = postMapper.selectById(postId);
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("post_id",postId);
        queryWrapper.eq("version",post.getVersion());
        // 开锁
        post.setIsTop(PostStaticData.POST_IS_NOT_TOP);
        post.setVersion(post.getVersion()+1);
        postMapper.update(post,queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePost(Post post) {

        String postId = post.getPostId();
        // 判断有无隐藏
        Integer isHasHideContent = post.getIsHasHideContent();
        if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_EXIST == isHasHideContent){
            String postHideContentPermission = post.getPostHideContentPermission();
            // 存在修改
            PostHideContent postHideContent = postMapper.selectPostHideContentBy(postId);
            if(null == postHideContent){
                // 添加 隐藏资源
                postHideContent=new PostHideContent();
                postHideContent.setPostId(post.getPostId());
                postHideContent.setResource(post.getHideContent());
                if (StringUtils.equal(PostStaticData.POST_HIDE_CONTENT_PERMISSION_HAS_GOODS,postHideContentPermission)) {
                    postHideContent.setType(PostStaticData.POST_HIDE_CONTENT_TYPE_GOODS);
                    postHideContent.setPrice(post.getPrice());
                    // 加入 商品
                    Goods goods=new Goods();
                    goods.setPostId(postId);
                    goods.setType(BizStaticData.GOODS_TYPE_JIFEN);
                    goods.setName(post.getPostTitle());
                    goods.setContent(post.getHideContent());
                    goods.setPrice(post.getPrice());
                    goods.setRemark("隐藏资源");
                    goods.setUserContact(post.getUsername());
                    goods.setUsername(post.getUsername());
                    goodsMapper.insert(goods);
                }else {
                    postHideContent.setType(PostStaticData.POST_HIDE_CONTENT_TYPE_DEFAULT);
                }
                postMapper.insertPostHideContent(postHideContent);
            }else {
                // 修改
                postHideContent.setPrice(post.getPrice());
                postHideContent.setResource(post.getHideContent());
                if (StringUtils.equal(PostStaticData.POST_HIDE_CONTENT_PERMISSION_HAS_GOODS,postHideContentPermission)) {
                    // 查找原先有无商品
                   Goods goods= goodsMapper.selectGoodsByPostId(post.getPostId());
                   if(null == goods){
                       goods=new Goods();
                       // 添加
                       postHideContent.setType(PostStaticData.POST_HIDE_CONTENT_TYPE_GOODS);
                       postHideContent.setPrice(post.getPrice());
                       // 加入 商品
                       goods.setPostId(postId);
                       goods.setType(BizStaticData.GOODS_TYPE_JIFEN);
                       goods.setName(post.getPostTitle());
                       goods.setContent(post.getHideContent());
                       goods.setPrice(post.getPrice());
                       goods.setRemark("隐藏资源");
                       goods.setUserContact(post.getUsername());
                       goods.setUsername(post.getUsername());
                       goodsMapper.insert(goods);
                   }else {
                       // 修改
                       goods.setPrice(post.getPrice());
                       goodsMapper.updateById(goods);
                   }
                }else {
                    // 无商品
                     postHideContent.setType(PostStaticData.POST_HIDE_CONTENT_TYPE_DEFAULT);
                     postHideContent.setPrice(null);
                     goodsMapper.deleteByPostId(postId);
                }
                postMapper.updateHideContentByPost(postHideContent);
            }
        }else {
            // 删除
            postMapper.deleteHideContentByPostId(postId);
            goodsMapper.deleteByPostId(postId);
        }

        // 修改标签
        List<String> tagNames = post.getTagNames();
        if(null != tagNames && tagNames.size()>0){
            tagNames.forEach( t ->{
                // 查询当前标签是否存在数据库
                Tag tag=tagMapper.selectByTagName(post.getPostCategoryId(),t);
                // 如果不存在就添加
                if(null ==tag){
                    tag=new Tag();
                    tag.setCId(post.getPostCategoryId());
                    tag.setName(t);
                    tagMapper.insert(tag);
                }
                // 先删除
                tagMapper.deletePostAndTag(post.getPostId());
                // 添加中间表
                tagMapper.insertPostAndTag(post.getPostId(),tag.getId());
            });
        }

        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        Integer version= postMapper.selectVersionByPost(postId);
        queryWrapper.eq("post_id",postId)
                .eq("version",version);
        post.setVersion(version+1);
        postMapper.update(post,queryWrapper);
    }

    @Override
    public PageVo<Post> getByPageOfHome(PageVo<Post> pageVo) {
        List<Post> data=postMapper.getListPage(pageVo);
        long total = postMapper.selectTotalByListPage(pageVo);
        pageVo.setPageNum(total / pageVo.getPageSize());
        pageVo.setTotal(total);
        pageVo.setData(data);
        return pageVo;
    }

    @Override
    public PageVo<Post> getListPage(PageVo<Post> pageVo) {
        List<Post> data=postMapper.getListPage(pageVo);
        data.forEach( i ->{
            // 查询typeName 名称
            i.setTypeName(typeMapper.selectById(i.getPostTypeId()).getTypeName());
            // 查询任务的nickname
            i.setNickname(userMapper.selectNickNameByUserId(i.getUserId()));
            // 查询任务头像
            i.setUserHeader(userMapper.selectHeaderByUserId(i.getUserId()));
        });
        long total = postMapper.selectTotalByListPage(pageVo);
        pageVo.setPageNum(total / pageVo.getPageSize());
        pageVo.setTotal(total);
        pageVo.setData(data);
        return pageVo;
    }

    @Override
    public List<Map<String, Object>> getHostPostList(String categoryId) {
        List<Post> postList=postMapper.selectPostIdAndPostTitleOrderByReadAndCategortId(categoryId);
        List<Map<String,Object>> data=new ArrayList<>();
        postList.forEach(i ->{
            Map<String,Object> map=new HashMap<>();
            map.put("postId",i.getPostId());
            map.put("postTitle",i.getPostTitle());
            map.put("postRead",i.getPostRead());
            data.add(map);
        });
        return data;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ReadPostVo read(String postId) {
        if(StringUtils.isBlank(postId)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"帖子id不为空");
        }
        QueryWrapper<Post> postQ=new QueryWrapper<>();
        postQ.eq("deleted",StateEnum.DELETED_FALSE.getCode());
        postQ.eq("is_public",StateEnum.ALLOW_PUBLIC.getCode());
        postQ.eq("post_id",postId);
        Post post = postMapper.selectOne(postQ);
        if(null == post){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前帖子不存在!(已被删除或者私密贴)");
        }
        // 插入专栏名
        post.setCategoryName(categoryMapper.selectById(post.getPostCategoryId()).getName());
        // 插入f分类名
        post.setTypeName(typeMapper.selectById(post.getPostTypeId()).getTypeName());

        post.setNickname(userMapper.selectNickNameByUserId(post.getUserId()));
        // 查询任务头像
        post.setUserHeader(userMapper.selectHeaderByUserId(post.getUserId()));
        // 阅读数加1
        post.setPostRead(post.getPostRead()+1);

        postMapper.updateById(post);
        ReadPostVo postVo=new ReadPostVo();
        // 插入tags
        postVo.setTags(tagMapper.selectTagByPostId(postId));
        BeanUtils.copyProperties(post,postVo);

        // 是否存在资源
        Integer isHasHideContent = post.getIsHasHideContent();
        if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_EXIST == isHasHideContent){
            String postHideContentPermission = post.getPostHideContentPermission();
            //判断是否登录
            if(userDataUtils.isLogin()){
                log.info("登录了········");
                // 判断是不是作者自己写的
                if(userDataUtils.getId().equals(post.getUserId())){
                    log.info("自己写的随便看");
                    postVo.setHideData(selectHideContent(postId));
                }else {
                    // 判断是不是登录可见
                    if (PostStaticData.POST_HIDE_CONTENT_PERMISSION_HAS_LOGIN.equals(postHideContentPermission)) {
                        // 是 查询内容
                        log.info("登录可见");
                        postVo.setHideData(selectHideContent(postId));
                    }
                    // 评论可见
                    else if (PostStaticData.POST_HIDE_CONTENT_PERMISSION_HAS_COMMENT.equals(postHideContentPermission)) {
                        // 查询用户是否评论了
                        log.info("评论可见");
                        Integer userId = userDataUtils.getId();
                        Comment comment = commentMapper.selectCommentByPostIdAndUserId(postId, userId);
                        if (null != comment) {
                            log.info("评论了----");
                            postVo.setHideData(selectHideContent(postId));
                        }
                    } else if (PostStaticData.POST_HIDE_CONTENT_PERMISSION_HAS_GOODS.equals(postHideContentPermission)) {
                        // 查询有无订单表状态
                        log.info("购买可见");
                        // 查商品
                        Goods goods = goodsMapper.selectGoodsByPostId(postId);
                        if(null == goods){
                            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"商品不存在！请联系管理员");
                        }
                        QueryWrapper<Order> queryWrapper=new QueryWrapper<>();
                        queryWrapper.eq("user_id",userDataUtils.getId());
                        queryWrapper.eq("goods_id",goods.getId());
                        queryWrapper.eq("post_id",postId);
                        queryWrapper.eq("deleted",StateEnum.DELETED_FALSE.getCode());
                        Order order = orderMapper.selectOne(queryWrapper);
                        if(null != order){
                            log.info("购买了");
                            postVo.setHideData(selectHideContent(postId));
                        }
                    }
                }
            }

        }

        if(userDataUtils.isLogin()) {
            // 判断是否点赞
            int goodsTotal = postMapper.selectCountUserAndPost(userDataUtils.getId(), postId, PostStaticData.GOOD_TYPE);
            log.info("开始判断是否点赞········");
            postVo.setHasGood(goodsTotal == 1);
            // 判断是否收藏
            int collectTotal = postMapper.selectCountUserAndPost(userDataUtils.getId(), postId, PostStaticData.COLLECT_TYPE);
            log.info("开始判断是否收藏········");
            postVo.setHasCollection(collectTotal == 1);
        }

        return postVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String goodPost(String postId) {
        int count= postMapper.selectCountUserAndPost(ShiroUtils.getId(),postId,PostStaticData.GOOD_TYPE);
        if(0 == count){
            // 点赞行为
            postMapper.insertUserAndPost(ShiroUtils.getId(),postId,PostStaticData.GOOD_TYPE);
            // 对帖子点赞数加1
            Integer version = postMapper.selectVersionByPost(postId);
            postMapper.updateGoodsByPostId(postId,1,version);
            return "点赞成功";
        }else {
            // 取消点赞行为
            postMapper.deleteUserAndPost(ShiroUtils.getId(),postId,PostStaticData.GOOD_TYPE);
            Integer version = postMapper.selectVersionByPost(postId);
            postMapper.updateGoodsByPostId(postId,-1,version);
            return "取消成功";
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String collectPost(String postId) {
        int count= postMapper.selectCountUserAndPost(ShiroUtils.getId(),postId,PostStaticData.COLLECT_TYPE);
        if(0 == count){
            // 点赞行为
            postMapper.insertUserAndPost(ShiroUtils.getId(),postId,PostStaticData.COLLECT_TYPE);
            // 对帖子点赞数加1
            Integer version = postMapper.selectVersionByPost(postId);
            postMapper.updateCollectNumByPostId(postId,1,version);
            return "收藏成功";
        }else {
            // 取消点赞行为
            postMapper.deleteUserAndPost(ShiroUtils.getId(),postId,PostStaticData.COLLECT_TYPE);
            Integer version = postMapper.selectVersionByPost(postId);
            postMapper.updateCollectNumByPostId(postId,-1,version);
            return "取消成功";
        }
    }

    @Override
    public Goods getHideGoods(String postId) {
        if(null == postId){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"帖子id不为空");
        }
        Goods goods = goodsMapper.selectGoodsByPostId(postId);
        return goods;
    }

    @Override
    public PageVo<SimplePostVo> getDeedPostDataByPage(PageVo<SimplePostVo> pageVo) {
        List<String> postIds=postMapper.selectPostIdsUserDeepPostByPage(pageVo);
        long total =postMapper.selectTotalsUserDeepPostByPage(pageVo);
        List<SimplePostVo> data=new ArrayList<>();

        postIds.forEach(postId ->{
            SimplePostVo simplePostVo=new SimplePostVo();
            simplePostVo.setPostTitle(postMapper.selectPostTitleByPostId(postId));
            simplePostVo.setPostId(postId);
            data.add(simplePostVo);
        });

        pageVo.setTotal(total);
        pageVo.setPageNum(total/pageVo.getPageSize());
        pageVo.setData(data);
        return pageVo;
    }

    @Override
    public PageVo<SimplePostVo> getDeletePost(PageVo<SimplePostVo> pageVo) {
        pageVo.getParams().put("userId",ShiroUtils.getId());
        List<SimplePostVo> data=postMapper.selectSimplePostByParamsOfPage(pageVo);
        long total = postMapper.selectCountSimplePostByParamsOfPage(pageVo);
        pageVo.setTotal(total);
        pageVo.setPageNum(total/pageVo.getPageSize());
        pageVo.setData(data);
        return pageVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void trueDeletePost(String postId) {
        // 删除
        Post post = postMapper.selectById(postId);
        if(post.getUserId()  != ShiroUtils.getId()){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"无权限！");
        }
        postMapper.deleteById(postId);
        // 删除 帖子隐藏内容
        if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_EXIST.equals(post.getIsHasHideContent())){
            postMapper.deleteTrueHideContentByPostId(postId);

            // 商品型 逻辑删除商品
            if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_HAS_GOODS.equals(post.getPostHideContentPermission())){
                log.info("有商品");
                goodsMapper.deleteTrueGoodsByPostId(postId);
            }
        }
        // 删除标签
        postMapper.deleteTrueTagNamsByPost(postId);
        // 删除评论
        commentMapper.deleteTrueCommentByPostId(postId);

        // 删除用户的行为
        postMapper.deleteTruePostUserDeed(postId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recyclePost(String postId) {
        // 取锁
        Post post = postMapper.selectById(postId);
        QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("post_id",postId);
        queryWrapper.eq("deleted",StateEnum.DELETED_TRUE.getCode());
        queryWrapper.eq("version",post.getVersion());
        post.setDeleted(StateEnum.DELETED_FALSE.getCode());
        post.setVersion(post.getVersion()+1);
        postMapper.update(post,queryWrapper);

        // 还原 帖子隐藏内容
        if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_EXIST.equals(post.getIsHasHideContent())){
            postMapper.recycleHideContentByPostId(postId);

            // 商品型还原商品
            if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_HAS_GOODS.equals(post.getPostHideContentPermission())){
                log.info("有商品");
                QueryWrapper<Goods> q=new QueryWrapper<>();
                q.eq("post_id",postId);
                q.eq("deleted",StateEnum.DELETED_TRUE.getCode());
                Goods goods=new Goods();
                goods.setPostId(postId);
                goods.setDeleted(StateEnum.DELETED_FALSE.getCode());
                goodsMapper.update(goods,q);
            }
        }

        // 还原标签
        postMapper.recycleTagNamsByPost(postId);

        // 分类下的贴子数加1
        Integer postTypeId = post.getPostTypeId();
        Type type = typeMapper.selectById(postTypeId);
        type.setTypePostCount(type.getTypePostCount()+1);
        typeMapper.updateById(type);

        // 还原评论
        QueryWrapper<Comment> cQw=new QueryWrapper<>();
        cQw.eq("post_id",postId);
        cQw.eq("deleted",StateEnum.DELETED_TRUE.getCode());
        Comment comment=new Comment();
        comment.setPostId(postId);
        comment.setDeleted(StateEnum.DELETED_FALSE.getCode());
        commentMapper.update(comment,cQw);
    }

    @Override
    public List<Post> getUserPost(Integer userId) {
        PageVo<Post> pageVo=new PageVo<>();
        pageVo.setCurrentPage(1);
        pageVo.setPageSize(8);
        pageVo.getParams().put("userId",userId);
        List<Post> data = postMapper.getListPage(pageVo);
        return data;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publish(Post post) {
        String postTitle = post.getPostTitle();
        Integer postCategoryId = post.getPostCategoryId();
        Integer postTypeId = post.getPostTypeId();
        String postContent = post.getPostContent();
        if(StringUtils.isBlank(postTitle)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"标题不为空");
        }
        if(StringUtils.isBlank(postContent)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"内容不为空");
        }
        if(null == postCategoryId){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请选择专栏");
        }
        if(null == postTypeId){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请选择分类");
        }



        User user = ShiroUtils.getUser();
        String postId=idWorker.nextId()+"";
        post.setPostId(postId);
        post.setUserId(user.getUserId());
        post.setUsername(user.getUsername());

        Integer isHasHideContent = post.getIsHasHideContent();
        //存在隐藏资源
        if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_EXIST == isHasHideContent){
            String hideContent = post.getHideContent();
            if (StringUtils.isBlank(hideContent)) {
                throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "请填写隐藏资源");
            }

            PostHideContent postHideContent=new PostHideContent();
            postHideContent.setPostId(post.getPostId());
            postHideContent.setResource(hideContent);

            String postHideContentPermission = post.getPostHideContentPermission();
            if (StringUtils.equal(PostStaticData.POST_HIDE_CONTENT_PERMISSION_HAS_GOODS,postHideContentPermission)) {
                Double price = post.getPrice();
                if(null == price){
                    throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "请设置积分数");
                }
                postHideContent.setType(PostStaticData.POST_HIDE_CONTENT_TYPE_GOODS);
                postHideContent.setPrice(price);

                // 加入 商品
                Goods goods=new Goods();
                goods.setPostId(postId);
                goods.setType(BizStaticData.GOODS_TYPE_JIFEN);
                goods.setName(post.getPostTitle());
                goods.setContent(hideContent);
                goods.setPrice(price);
                goods.setRemark("隐藏资源");
                goods.setUserContact(user.getUsername());
                goods.setUsername(user.getUsername());
                goodsMapper.insert(goods);
            }else {
                postHideContent.setType(PostStaticData.POST_HIDE_CONTENT_TYPE_DEFAULT);
            }
            postMapper.insertPostHideContent(postHideContent);
        }

        // 加入标签
        List<String> tagNames = post.getTagNames();
        if(null != tagNames && tagNames.size()>0){
            tagNames.forEach( t ->{
                // 查询当前标签是否存在数据库
                Tag tag=tagMapper.selectByTagName(post.getPostCategoryId(),t);
                // 如果不存在就添加
                if(null ==tag){
                    tag=new Tag();
                    tag.setCId(post.getPostCategoryId());
                    tag.setName(t);
                    tagMapper.insert(tag);
                }
                // 添加中间表
                tagMapper.insertPostAndTag(post.getPostId(),tag.getId());
            });
        }

        // 取消审核管理
        post.setAllow(1);
        postMapper.insert(post);

//        // 加入审核
//        Auditing auditing=new Auditing();
//        auditing.setId(idWorker.nextId()+"");
//        auditing.setUserId(user.getUserId());
//        auditing.setUsername(user.getUsername());
//        auditing.setPostId(post.getPostId());
//        auditing.setStatus(PostStaticData.POST_ALLOW_TYPE_AUDITING);
//        System.out.println(auditing.toString());
//        auditingMapper.insert(auditing);

    }


    /**
     * 查询隐藏
     * @param postId
     * @return
     */
    private PostHideContentVo selectHideContent(String postId){
        PostHideContent postHideContent = postMapper.selectPostHideContentBy(postId);
        PostHideContentVo hideData = new PostHideContentVo();
        BeanUtils.copyProperties(postHideContent, hideData);
        hideData.setTypeName(postHideContent.getType() == 0 ? "普通类型" : "商品类型");
        return hideData;
    }

}

