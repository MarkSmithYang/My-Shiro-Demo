package com.example.demo.config;

import com.example.demo.exception.ParameterErrorException;
import com.example.demo.model.UserInfo;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.UserInfoService;
import com.example.demo.utils.PasswordEncoderUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.Set;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/30
 */
public class MyRealm extends AuthorizingRealm {

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
        UserInfo user = (UserInfo) args;
        //获取全面的用户信息(库里的全部信息)
        UserInfo userInfo = userInfoRepository.findById(user.getId()).get();
        //获取用户权限
        Set<String> permissions = userInfo.getPermissions();
        //获取用户角色
        Set<String> roles = userInfo.getRoles();
        //给用户授权(把用户拥有的权限加入到登陆中)
        info.addStringPermission("admin");
//        info.setStringPermissions(permissions);
//        info.setRoles(roles);
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
            ParameterErrorException.messages("用户名或密码错误");
        }
        //验证密码是否一致
        Boolean matchPassword = PasswordEncoderUtils.matchPassword(String.valueOf(token.getPassword()), userInfo.getPassword());
        //验证密码(这里可能有问题--不清楚这的认证)---->现在明白了,是因为它这里传入的数据库中的密码,而用户输入的密码,
        //它会自己去获取,然后比较,所以加密过的密码老是验证不过,所以需要我们在验证密码无误后在放入UsernamePasswordToken中
       if(matchPassword){
          //密码正确,把数据库中的密码覆盖掉用户输入的密码(目的是为了下面的shiro认证)
           token.setPassword(userInfo.getPassword().toCharArray());
       }
        return new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), "");
    }
}
