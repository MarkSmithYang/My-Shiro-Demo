package com.yb.demo.service;

import com.yb.demo.model.UserInfo;
import com.yb.demo.repository.UserInfoRepository;
import com.yb.demo.request.RegisterRequest;
import com.yb.demo.utils.PasswordEncoderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
@Service
public class UserInfoService {
    public static final Logger log = LoggerFactory.getLogger(UserInfoService.class);

    @Autowired
    private UserInfoRepository userInfoRepository;

    /**
     * 通过用户名查询用户信息
     */
    public UserInfo findByUsername(String username) {
        UserInfo result = userInfoRepository.findByUsername(username);
        return result;
    }

    /**
     * 用户注册
     */
    @Transactional(rollbackFor = Exception.class)
    public String registerUser(RegisterRequest registerRequest) {
        //默认最开始给用户角色和权限
        //密码明码加密
        String password = PasswordEncoderUtils.encoderPassword(registerRequest.getPassword());
        //封装数据到映射实体
        UserInfo userInfo = new UserInfo(registerRequest.getUsername(), password);
        //检查用户名是否已经存在,避免数据库表抛出的(用户名唯一约束冲突异常而只会是个Exception捕捉的网络异常)
        //这里就可以提示用户出错原因,当然了可以不为用户名添加唯一约束,但是这个约束可以防止手动操作数据库表添加相同的用户名,
        // 导致获取的时候没用List接收而导致元素超过一个(封装)异常
        UserInfo username = findByUsername(registerRequest.getUsername());
        if (username != null) {
            return "用户名已经存在";
        }
        //添加用户
        try {
            userInfoRepository.save(userInfo);
        } catch (Exception e) {
            try {
                userInfoRepository.save(userInfo);
            } catch (Exception e1) {
                log.info("保存用户信息失败");
                return "注册失败";
            }
        }
        return "注册成功";
    }
}
