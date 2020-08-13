package com.rpy.qw.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rpy.qw.biz.domain.Goods;
import com.rpy.qw.biz.mapper.GoodsMapper;
import com.rpy.qw.data.PostStaticData;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.post.mapper.AuditingMapper;
import com.rpy.qw.post.mapper.PostMapper;
import com.rpy.qw.post.service.AuditingService;
import com.rpy.qw.post.service.PostService;
import com.rpy.qw.post.domain.Post;
import com.rpy.qw.post.domain.Type;
import com.rpy.qw.post.mapper.CategoryMapper;
import com.rpy.qw.post.mapper.TypeMapper;
import com.rpy.qw.post.vo.AuditingVo;
import com.rpy.qw.post.vo.FeedBackContentVo;
import com.rpy.qw.post.vo.PostAuditingVo;
import com.rpy.qw.post.domain.Auditing;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.ShiroUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuditingServiceImpl extends ServiceImpl<AuditingMapper, Auditing> implements AuditingService {

    @Autowired
    private AuditingMapper auditingMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private TypeMapper typeMapper;


    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostService postService;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public PageVo<AuditingVo> getByPage(PageVo<AuditingVo> pageVo) {
        pageVo.setSortColumn("created_time");
        pageVo.setSortMethod("desc");
        List<AuditingVo> data = auditingMapper.getByPage(pageVo);
        data.forEach(d -> {
            // 查询分类名字
            d.setCategoryName(categoryMapper.selectById(d.getCategoryId()).getName());
            //查询 专栏名字
            d.setTypeName(typeMapper.selectById(d.getTypeId()).getTypeName());
        });
        pageVo.setData(data);
        long total=auditingMapper.selectTotalByPage(pageVo);
        pageVo.setTotal(total);
        pageVo.setPageNum( (total / pageVo.getPageSize()));
        return pageVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "com.rpy.qw.post.service.AuditingServiceImpl",key = "#feedBackContentVo.auditingId")
    public PostAuditingVo allow(FeedBackContentVo feedBackContentVo) {
        String auditingId = feedBackContentVo.getAuditingId();
        if(null == auditingId){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请输入审批单号");
        }
        String feedContent = feedBackContentVo.getFeedContent();
        // 审核通过
        Auditing auditing = auditingMapper.selectById(auditingId);
        auditing.setStatus(PostStaticData.POST_ALLOW_TYPE_AUDITING_ALLOW);
        auditing.setOperateBy(ShiroUtils.getName());
        auditing.setFeedbackContent(feedContent);
        auditingMapper.updateById(auditing);
        // 修改帖子状态
        String postId = auditing.getPostId();
        Post post = postMapper.selectById(postId);
        post.setAllow(PostStaticData.POST_ALLOW_TYPE_AUDITING_ALLOW);
        postMapper.updateById(post);

        // 分类下的贴子数加1
        Integer postTypeId = post.getPostTypeId();
        Type type = typeMapper.selectById(postTypeId);
        type.setTypePostCount(type.getTypePostCount()+1);
        typeMapper.updateById(type);
        return postService.getAuditingDetailByAuditingId(auditingId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "com.rpy.qw.post.service.AuditingServiceImpl",key = "#feedBackContentVo.auditingId")
    public PostAuditingVo deny(FeedBackContentVo feedBackContentVo) {
        // 审核拒绝
        String auditingId = feedBackContentVo.getAuditingId();
        if(null == auditingId){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请输入审批单号");
        }
        String feedContent = feedBackContentVo.getFeedContent();
        Auditing auditing = auditingMapper.selectById(auditingId);
        auditing.setStatus(PostStaticData.POST_ALLOW_TYPE_AUDITING_DENY);
        auditing.setOperateBy(ShiroUtils.getName());
        auditing.setFeedbackContent(feedContent);
        auditingMapper.updateById(auditing);
        // 修改帖子状态
        String postId = auditing.getPostId();
        Post post = postMapper.selectById(postId);
        post.setAllow(PostStaticData.POST_ALLOW_TYPE_AUDITING_DENY);
        postMapper.updateById(post);

        // 逻辑删除 帖子隐藏内容
        if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_EXIST == post.getIsHasHideContent()){
            postMapper.deleteHideContentByPostId(postId);

            // 商品型 逻辑删除商品
            if(PostStaticData.POST_HIDE_CONTENT_PERMISSION_HAS_GOODS== post.getPostHideContentPermission()){
                QueryWrapper<Goods> q=new QueryWrapper<>();
                q.eq("post_id",post);
                Goods goods=new Goods();
                goods.setDeleted(StateEnum.DELETED_TRUE.getCode());
                goodsMapper.update(goods,q);
            }
        }

        // 逻辑删除标签
        postMapper.deleteTagNamsByPost(postId);
        return postService.getAuditingDetailByAuditingId(auditingId);
    }

    @Override
    @Cacheable(value = "com.rpy.qw.post.service.AuditingServiceImpl",key = "#id")
    public PostAuditingVo showDetail(String id) {
        PostAuditingVo postAuditingVo =postService.getAuditingDetailByAuditingId(id);
        postAuditingVo.setAuditingId(id);

        return postAuditingVo;
    }
}

