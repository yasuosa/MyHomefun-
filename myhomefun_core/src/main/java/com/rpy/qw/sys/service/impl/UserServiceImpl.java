package com.rpy.qw.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rpy.qw.biz.domain.UserInfo;
import com.rpy.qw.biz.mapper.UserInfoMapper;
import com.rpy.qw.data.SysStaticData;
import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.jwt.JWTUtils;
import com.rpy.qw.shiro.JwtToken;
import com.rpy.qw.sys.vo.RegisterUserVo;
import com.rpy.qw.utils.ActiviUser;
import com.rpy.qw.utils.PageVo;
import com.rpy.qw.utils.ShiroUtils;
import com.rpy.qw.utils.StringUtils;
import com.rpy.qw.post.mapper.PostMapper;
import com.rpy.qw.post.vo.SimpleCommentVo;
import com.rpy.qw.post.domain.Comment;
import com.rpy.qw.post.mapper.CommentMapper;
import com.rpy.qw.post.vo.SimplePostVo;
import com.rpy.qw.sys.domain.User;
import com.rpy.qw.sys.mapper.UserMapper;
import com.rpy.qw.sys.service.UserService;
import com.rpy.qw.sys.vo.UserAndRoleVo;
import com.rpy.qw.sys.vo.UserHomeVo;
import com.rpy.qw.sys.vo.UserVo;
import lombok.SneakyThrows;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

@Service

public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private RedisTemplate<String,String> stringRedisTemplate;




    @Override
    public User getByUserName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userMapper.selectOne(queryWrapper);
    }

    @SneakyThrows
    @Override
    public PageVo<UserVo> getByPage(PageVo<UserVo> pageVo) {
        // 设置分页
        IPage<User> page = new Page<>(pageVo.getCurrentPage(), pageVo.getPageSize());
        // 设置查询的内容
        Map<String, Object> params = pageVo.getParams();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String username = (String) params.get("username");
        String realname = (String) params.get("realname");
        String nickname = (String) params.get("nickname");
        String userEmail = (String) params.get("userEmail");
        Integer sex = (Integer) params.get("sex");
        queryWrapper.like(StringUtils.isNotBlank(username), "username", username)
                .like(StringUtils.isNotBlank(realname), "realname", realname)
                .like(StringUtils.isNotBlank(userEmail), "user_email", userEmail)
                .like(StringUtils.isNotBlank(nickname), "nickname", nickname)
                .eq(null !=sex, "sex", sex)
                .eq("deleted", StateEnum.DELETED_FALSE.getCode());
        // 排序
        String sortColumn = pageVo.getSortColumn();
        String sortMethod = pageVo.getSortMethod();
        if (StringUtils.isNotBlank(sortColumn)) {
            if (StateEnum.ASC.getMsg().equals(sortMethod.toLowerCase())) {
                queryWrapper.orderByAsc(sortColumn);
            } else {
                queryWrapper.orderByDesc(sortColumn);
            }
        }
        userMapper.selectPage(page, queryWrapper);
        // 设置返回值
        List<User> records = page.getRecords();
        List<UserVo> data=new ArrayList<>();
        for (User record : records) {
            record.setPassword(null);
            record.setSalt(null);
            record.setVersion(null);
            UserVo userVo=new UserVo();
            BeanUtils.copyProperties(record,userVo);
            Integer userId = record.getUserId();
            List<UserAndRoleVo> userAndRoleVo = userMapper.selectUserAndRoleByUserId(userId);
            if (null != userAndRoleVo) {
                userVo.setRoleIds(userAndRoleVo.stream().map(UserAndRoleVo::getRoleId).collect(Collectors.toList()));
                userVo.setRoleNames(userAndRoleVo.stream().map(UserAndRoleVo::getRolename).collect(Collectors.toList()));
            }
            data.add(userVo);
        }

        pageVo.setPageNum(page.getPages());
        pageVo.setTotal(page.getTotal());
        pageVo.setData(data);
        return pageVo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Integer id) {
        // 逻辑删除
        User user = new User();
        user.setUserId(id);
        user.setDeleted(StateEnum.DELETED_TRUE.getCode());
        userMapper.updateById(user);
        //TODO 判断好多
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        // 查询手机和邮箱
        checkHasSamePhoneOrEmail(user);
        //
        // 查询锁
        User oldUser = userMapper.selectById(user.getUserId());
        Integer version = oldUser.getVersion();
        // 开锁
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("version", version);
        queryWrapper.eq("user_id",user.getUserId());
        user.setVersion(version + 1);

        userMapper.update(user,queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        String username = user.getUsername();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User oldUser = userMapper.selectOne(queryWrapper);
        if (null != oldUser) {
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "当前用户名已存在！");
        }
        // 查询手机和邮箱
        checkHasSamePhoneOrEmail(user);
        user.setSalt(UUID.randomUUID().toString());
        user.setPassword(new Md5Hash("123456",user.getSalt()).toString());
        userMapper.insert(user);
        // 初始化用户资产
        UserInfo userInfo=new UserInfo(user.getUserId(),username);
        userInfoMapper.insert(userInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CachePut(value = "com.rpy.qw.sys.service.UserServiceImpl",key = "#result.userId")
    public UserAndRoleVo dispatchRole(UserAndRoleVo userAndRoleVo) {
        if(null == userAndRoleVo.getRoleIds() || userAndRoleVo.getRoleIds().size()==0){
            throw new RuntimeException("请选择用户角色");
        }
        try {
            // 先删除
            userMapper.deleteUserAndRoleByUserId(userAndRoleVo.getUserId());
            // 在添加
            userMapper.insertUserAndRole(userAndRoleVo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "分配角色失败");
        }
        return userAndRoleVo;
    }

    @Override
    @CacheEvict(value = "com.rpy.qw.sys.service.UserServiceImpl",key = "#userId")
    public void cancelRole(Integer userId) {
        userMapper.deleteUserAndRoleByUserId(userId);
    }

    @Override
    public Map<String, Object> login(User user) {
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            throw new MyFunHomeException(ResultEnum.PARAMS_NULL.getCode(), ResultEnum.PARAMS_NULL.getMsg());
        }

        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User old = userMapper.selectOne(queryWrapper);
        if(null == old){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"当前用户名不存在！");
        }
        String salt = old.getSalt();
        try {
            Map<String, Object> map = new HashMap<>(4);
            String token = JWTUtils.sign(username, new Md5Hash(password,salt).toString());
            JwtToken jwtToken = new JwtToken(token);
            Subject subject = SecurityUtils.getSubject();
            subject.login(jwtToken);
            map.put("token", token);
            ActiviUser activiUser = (ActiviUser) subject.getPrincipal();
            redisTemplate.opsForValue().set(SysStaticData.USER_REDIS_KEY_PRE+token,activiUser, Duration.ofDays(1));
//            map.put("info",subject.getPrincipal());
            return map;
        } catch (MyFunHomeException e) {
            e.printStackTrace();
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(), e.getMessage());
        }

    }

    @Override
    @Cacheable(value = "com.rpy.qw.sys.service.UserServiceImpl",key = "#userId")
    public UserAndRoleVo getRoleId(Integer userId) {
        List<UserAndRoleVo> userAndRoleVos = userMapper.selectUserAndRoleByUserId(userId);
        UserAndRoleVo userAndRoleVo=new UserAndRoleVo();
        userAndRoleVo.setUserId(userId);
        userAndRoleVo.setRoleIds(userAndRoleVos.stream().map(UserAndRoleVo::getRoleId).collect(Collectors.toList()));
        return userAndRoleVo;
    }

    @Override
    public UserHomeVo getUserHome(Integer userId) {
        User user = userMapper.selectById(userId);
        if(null == user){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"无效用户");
        }
        UserHomeVo userHomeVo=new UserHomeVo();
        BeanUtils.copyProperties(user,userHomeVo);
        // 查询积分
        Double points = userInfoMapper.selectPointsByUserId(userId);
        userHomeVo.setPoints(points);
        // 查询最近帖子
        List<SimplePostVo> simplePostVos = postMapper.selectSimplePostByParams(userId, StateEnum.DELETED_FALSE.getCode(), StateEnum.ALLOW_PUBLIC.getCode());
        // 查询最近评论
        QueryWrapper<Comment> cQw=new QueryWrapper<>();
        cQw.eq("deleted",StateEnum.DELETED_FALSE.getCode())
                .eq("userId",userId)
                .orderByDesc("created_time");
        List<Comment> comments = commentMapper.selectList(cQw);
        List<SimpleCommentVo> commentVoList=new ArrayList<>();
        comments.forEach(e ->{
            SimpleCommentVo simpleCommentVo=new SimpleCommentVo();
            BeanUtils.copyProperties(e,simpleCommentVo);
            // 插入nickname
            simpleCommentVo.setNickname(userMapper.selectNickNameByUserId(userId));
            // 插入postTitle
            simpleCommentVo.setPostTitle(postMapper.selectPostTitleByPostId(e.getPostId()));
            commentVoList.add(simpleCommentVo);
        });
        userHomeVo.setPostList(simplePostVos);
        userHomeVo.setCommentList(commentVoList);
        return userHomeVo;
    }

    @Override
    public void modifyPassWord(User user) {
        String password = user.getPassword();
        if(StringUtils.isBlank(password)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请填写新密码");
        }
        Integer userId = ShiroUtils.getId();
        User old = userMapper.selectById(userId);
        old.setPassword(new Md5Hash(password,old.getSalt()).toString());
        updateUser(old);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(RegisterUserVo userVo) {
        String code = userVo.getCode();
        String nickname = userVo.getNickname();
        String password = userVo.getPassword();
        String userEmail = userVo.getUserEmail();
        if(StringUtils.isBlank(code)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"验证码不为空");
        }
        if(StringUtils.isBlank(nickname)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"昵称不为空");
        }
        if(StringUtils.isBlank(password)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"密码不为空");
        }
        if(StringUtils.isBlank(userEmail)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"用户邮箱不为空");
        }
        Boolean isExit = stringRedisTemplate.hasKey(SysStaticData.REGITSER_CODE_REDIS_KEY_PRE + userEmail);
        if(!isExit){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"验证码已过期!请重新获取");
        }
        String oldCode = stringRedisTemplate.opsForValue().get(SysStaticData.REGITSER_CODE_REDIS_KEY_PRE + userEmail);
        if(!oldCode.equals(code)){
            throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"验证码错误!");
        }
        User user=new User();
        user.setUsername(userEmail);
        user.setUserEmail(userEmail);
        user.setNickname(nickname);
        user.setSalt(UUID.randomUUID().toString());
        user.setPassword(new Md5Hash(password,user.getSalt()).toString());
        checkHasSamePhoneOrEmail(user);
        userMapper.insert(user);

        // 注册userInfo
        UserInfo userInfo=new UserInfo(user.getUserId(),userEmail);
        userInfoMapper.insert(userInfo);

        stringRedisTemplate.delete(SysStaticData.REGITSER_CODE_REDIS_KEY_PRE+userEmail);
    }

    /**
     * 查询是否除自己之后 的手机号和邮箱相同
     * @param user
     */
    private void checkHasSamePhoneOrEmail(User user){
        User old=null;
        String phone = user.getPhone();

        // 判断手机
        if(StringUtils.isNotBlank(phone)) {
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.notIn(null !=user.getUserId(),"user_id",user.getUserId());
            queryWrapper.eq(StringUtils.isNotBlank(phone), "phone", user.getPhone());
            old = userMapper.selectOne(queryWrapper);
            if (null != old) {
                throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "当前手机号已存在");
            }
        }

        // 判断邮箱
        String userEmail = user.getUserEmail();
        if(StringUtils.isNotBlank(userEmail)) {
            QueryWrapper<User> queryWrapper=new QueryWrapper<>();
            queryWrapper.clear();
            queryWrapper.notIn(null != user.getUserId(), "user_id", user.getUserId());
            queryWrapper.eq(StringUtils.isNotBlank(userEmail), "user_email", user.getUserEmail());
            old = userMapper.selectOne(queryWrapper);
            if (null != old) {
                throw new MyFunHomeException(ResultEnum.ERROR.getCode(), "当前邮箱地址已存在");
            }
        }
    }
}

