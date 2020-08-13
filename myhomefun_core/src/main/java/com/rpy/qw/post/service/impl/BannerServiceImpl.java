package com.rpy.qw.post.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.post.domain.Category;
import com.rpy.qw.post.domain.Post;
import com.rpy.qw.post.domain.Type;
import com.rpy.qw.post.mapper.PostMapper;
import com.rpy.qw.post.service.CategoryService;
import com.rpy.qw.post.service.TypeService;
import com.rpy.qw.post.vo.TreeBannerTypeNode;
import com.rpy.qw.sys.domain.Notice;
import com.rpy.qw.sys.mapper.NoticeMapper;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rpy.qw.post.mapper.BannerMapper;
import com.rpy.qw.post.domain.Banner;
import com.rpy.qw.post.service.BannerService;
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService{

    @Autowired
    private BannerMapper bannerMapper;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private NoticeMapper noticeMapper;
    
    
    @Override
    public PageVo<Banner> getByPage(PageVo<Banner> pageVo) {
        String bannerTitle = (String) pageVo.getParams().get("bannerTitle");
        QueryWrapper<Banner> queryWrapper=new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNoneBlank(bannerTitle),"banner_title",bannerTitle);
        IPage<Banner> page=new Page<>(pageVo.getCurrentPage(),pageVo.getPageSize());
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
        bannerMapper.selectPage(page,queryWrapper);

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(page.getRecords());
        return pageVo;
    }

    @Override
    public List<TreeBannerTypeNode> getBannerType() {
        List<TreeBannerTypeNode> data=new ArrayList<>();
        // 广告曾
        data.add(new TreeBannerTypeNode(StateEnum.BANNER_TYPE_GUANGGAO));
        // 公告
        TreeBannerTypeNode nNode = new TreeBannerTypeNode(StateEnum.BANNER_TYPE_GONGGAO);
        data.add(nNode);
        // 帖子
        TreeBannerTypeNode postNode = new TreeBannerTypeNode(StateEnum.BANNER_TYPE_POST);
        data.add(postNode);

        // 帖子分类
        List<Type> typeList= typeService.getList();
        // 专栏分类
        List<Category> categoryList = categoryService.getList();
        categoryList.forEach( c ->{
            TreeBannerTypeNode cNode = new TreeBannerTypeNode(c.getName(),c.getId()+"");
            postNode.getChildren().add(cNode);
            typeList.forEach( t->{
                TreeBannerTypeNode tNode = new TreeBannerTypeNode(t.getTypeName(),t.getTypeId()+"");
                cNode.getChildren().add(tNode);
                QueryWrapper<Post> queryWrapper=new QueryWrapper<>();
                queryWrapper.eq("post_category_id",c.getId())
                        .eq("post_type_id",t.getTypeId())
                        .eq("deleted",StateEnum.DELETED_FALSE.getCode())
                        .eq("is_public",StateEnum.ALLOW_PUBLIC.getCode());
                List<Post> posts = postMapper.selectList(queryWrapper);
                posts.forEach( p ->{
                    tNode.getChildren().add(new TreeBannerTypeNode(p.getPostTitle(),p.getPostId(),p.getPostCover()));
                });
            });
        });

        QueryWrapper<Notice> nQ=new QueryWrapper<>();
        nQ.eq("deleted",StateEnum.DELETED_FALSE.getCode())
                .eq("enable",StateEnum.ENABLE.getCode());
        List<Notice> noticeList = noticeMapper.selectList(nQ);
        noticeList.forEach(n ->{
            nNode.getChildren().add(new TreeBannerTypeNode(n.getTitle(),n.getId()+"",n.getCover()));
        });

        return data;
    }

    @Override
    public List<Banner> getList() {
        QueryWrapper<Banner> queryWrapper=new QueryWrapper<>();
        queryWrapper.orderByDesc("created_time");
        List<Banner> banners = bannerMapper.selectList(queryWrapper);
        return banners;
    }
}
