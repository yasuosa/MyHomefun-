package com.rpy.qw.shiro.realm;


import com.rpy.qw.enums.ResultEnum;
import com.rpy.qw.enums.StateEnum;
import com.rpy.qw.exception.MyFunHomeException;
import com.rpy.qw.jwt.JWTUtils;
import com.rpy.qw.shiro.JwtToken;
import com.rpy.qw.sys.domain.User;
import com.rpy.qw.sys.service.MenuService;
import com.rpy.qw.sys.service.RoleService;
import com.rpy.qw.sys.service.UserService;
import com.rpy.qw.utils.ActiviUser;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;

/**
 * @program: myfunhome
 * @description:
 * @author: 任鹏宇
 * @create: 2020-06-18 22:40
 **/
public class AdminUserRealm extends AuthorizingRealm {





    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    @Lazy
    private RoleService roleService;


    @Autowired
    @Lazy
    private MenuService menuService;

    /**
     * 身份认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        JwtToken jwtToken= (JwtToken) authenticationToken;
        String tokenStr = (String) jwtToken.getCredentials();
        String username = JWTUtils.getUsername(tokenStr);
        if(null == username){
            throw new MyFunHomeException(ResultEnum.NOT_LOGIN.getCode(),ResultEnum.NOT_LOGIN.getMsg());
        }
        if(JWTUtils.isExpire(tokenStr)){
            throw new MyFunHomeException(ResultEnum.NOT_LOGIN_TOKEN.getCode(),ResultEnum.NOT_LOGIN_TOKEN.getMsg());
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = null;
        ActiviUser activiUser=new ActiviUser();
        User user = userService.getByUserName(username);
        if(null == user || StateEnum.DELETED_TRUE.getCode().equals(user.getDeleted())){
            throw new MyFunHomeException(ResultEnum.PARAMS_ERROR.getCode(),"当前账号未注册");
        }else {
            // 验证token
            if(!JWTUtils.verify(tokenStr,user.getUsername(),user.getPassword())){
                throw new MyFunHomeException(ResultEnum.PASSWORD_ERROE.getCode(),ResultEnum.PASSWORD_ERROE.getMsg());
            }
            activiUser.setUser(user);

            //根据用户id查询角色的集合
            List<String> roles=this.roleService.queryRoleNamesByUserId(user.getUserId());
            //根据角色id查询权限编码
            List<String> permissions=this.menuService.queryPermissionCodeByUserId(user.getUserId());
            if(null == roles || roles.size()==0){
                throw new MyFunHomeException(ResultEnum.ERROR.getCode(),"请联系管理员开通进入后台权限权限");
            }
            activiUser.setRoles(roles);
            activiUser.setPermissions(permissions);

            simpleAuthenticationInfo = new SimpleAuthenticationInfo(activiUser,tokenStr, this.getName());
        }
        return simpleAuthenticationInfo;
    }

    /**
     * 权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        ActiviUser activiUser= (ActiviUser) principalCollection.getPrimaryPrincipal();
        List<String> roles = activiUser.getRoles();
        List<String> permissions = activiUser.getPermissions();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }



    /**
     * 大坑！，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
}
