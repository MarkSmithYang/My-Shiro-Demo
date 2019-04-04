package com.yb.shiro.shiro;

import com.yb.shiro.exception.ParameterErrorException;
import com.yb.shiro.mapper.ShiroDemoMapper;
import com.yb.shiro.model.UserRequest;
import com.yb.shiro.service.ShiroDemoService;
import com.yb.shiro.utils.EncodePasswordUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author yangbiao
 * @Description:
 * @Date $date$ $time$
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private ShiroDemoMapper shiroDemoMapper;
    @Autowired
    private ShiroDemoService shiroDemoService;

    /**
     * @Author yangbiao
     * @Description:用户授权
     * @Date 2018/8/5/005 20:30
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.err.println("执行授权");
        return null;
    }

    /**
     * @Author yangbiao
     * @Description:用户认证
     * @Date 2018/8/5/005 20:30
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UserRequest user = new UserRequest();
        //强转token
        UsernamePasswordToken passwordToken = (UsernamePasswordToken) token;
        //查询用户名是否存在
         user = shiroDemoMapper.findUserByUsername(passwordToken.getUsername());
        if(!user.getUsername().equals(passwordToken.getUsername())){
            ParameterErrorException.messages("用户名或密码错误");
        }
//        else {
//            user.setUsername(UserInfoDic.USERNAME);
//            user.setPassword(String.valueOf(passwordToken.getPassword()));
//        }
        //认证密码是否正确
        System.err.println("执行认证");
        //密码校验(新的加密的特殊)
        String inputPassword = String.valueOf(passwordToken.getPassword());
        if(!EncodePasswordUtils.checkPassword(inputPassword, user.getPassword())){
            inputPassword = user.getPassword();
            ParameterErrorException.messages("用户名或密码错误");
        }
        //shiro的校验方式是通过数据库查询的密码和输入的密码比较,所以传递数据库里的加密的密码给它,它才老是匹配不上
        //所以现在的做法就是有把输入的密码又传递给它,当然了我们要在上面自己做密码的校验,但是实测,输错了密码也能验证通过
        //所以最好是密码不对时,覆盖传递的密码
        return  new SimpleAuthenticationInfo(user, inputPassword, "");
    }
}