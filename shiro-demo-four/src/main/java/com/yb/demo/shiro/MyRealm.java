package com.yb.demo.shiro;

import com.yb.demo.model.Permission;
import com.yb.demo.model.Role;
import com.yb.demo.model.UserInfo;
import com.yb.demo.repository.UserInfoRepository;
import com.yb.demo.service.UserInfoService;
import com.yb.demo.utils.PasswordEncoderUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/30
 */
public class MyRealm extends AuthorizingRealm {
    public static final Logger log = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 执行授权逻辑
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection args) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取用户的登录信息(这个对象里只有用户名和密码),这个是从登陆状态下获取的信息,所以一定能够从库里查询到用户
        //java.lang.ClassCastException: org.apache.shiro.subject.SimplePrincipalCollection cannot be cast to UserInfo
//        UserInfo user = (UserInfo) args;//结果就是这个报了异常,debug看的时候,args确实是UserInfo的信息(认证那里传递进来的)
        //获取全面的用户信息(库里的全部信息)

        //正确的获取登录信息的方式(因为用这种方式上面的参数相当于没用了,所以就试了试,果然还是不行)
        UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();

        UserInfo userInfo = null;
        try {
            userInfo = userInfoRepository.findById(user.getId()).get();
        } catch (Exception e) {
            //如果发生异常,重试一次
            userInfo = userInfoRepository.findById(user.getId()).get();
        }
        if (userInfo != null) {
            //获取用户权限
            Set<Permission> permissions = userInfo.getPermissions();
            //获取用户角色
            Set<Role> roles = userInfo.getRoles();
            //获取权限
            Set<String> pers = new HashSet<>();
            if (CollectionUtils.isNotEmpty(permissions)) {
                permissions.forEach(s ->pers.add(s.getPermission()));
            }
            //获取角色
            Set<String> ros = new HashSet<>();
            if (CollectionUtils.isNotEmpty(roles)) {
                roles.forEach(s -> {
                    //获取角色拥有的权限
                    Set<Permission> permissionSet = s.getPermissions();
                    if(CollectionUtils.isNotEmpty(permissionSet)){
                        //将其添加到用户的权限中(用户角色的权限也是用户的权限)
                        permissionSet.forEach(a->pers.add(a.getPermission()));
                    }
                    ros.add((s.getRole()));
                });
            }
            //给用户授权(把用户拥有的权限加入到登陆中)
            info.addRoles(ros);
            info.addStringPermissions(pers);
        } else {
            log.info("获取用户的角色权限失败");
        }
        //返回
        return info;
    }

    /**
     * 执行认证逻辑--(登录)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken args) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) args;
        //获取用户信息
        String username = token.getUsername();
        UserInfo userInfo = userInfoService.findByUsername(username);
        if (userInfo == null) {
            log.info("用户名或密码错误");
            //shiro底层会抛出UnKnowAccountException
            return null;
        }
        //验证密码是否一致
        Boolean matchPassword = PasswordEncoderUtils.matchPassword(String.valueOf(token.getPassword()), userInfo.getPassword());
        //验证密码(这里可能有问题--不清楚这的认证)---->现在明白了,是因为它这里传入的数据库中的密码,而用户输入的密码,
        //它会自己去获取,然后比较,所以加密过的密码老是验证不过,所以需要我们在验证密码无误后在放入UsernamePasswordToken中
        if (matchPassword) {
            //密码正确,用用户输入的密码把数据库中的密码覆盖掉(目的是为了下面的shiro认证)
            //因为shiro的密码认证是用用户登录的密码和你数据库查询的密码进行匹配,我这里只能自己做校验
            token.setPassword(userInfo.getPassword().toCharArray());
            //返回数据
            return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), "");
        } else {
            log.info("用户名或密码错误");
            //shiro底层会抛出UnKnowAccountException
            return null;
        }
    }
}
