package com.yb.shiro.service;

import com.yb.shiro.dic.UserInfoDic;
import com.yb.shiro.mapper.ShiroDemoMapper;
import com.yb.shiro.utils.EncodePasswordUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Author yangbiao
 * @Description:
 * @Date $date$ $time$
 */
@Service
public class ShiroDemoService {
    public static final Logger log = LoggerFactory.getLogger(ShiroDemoService.class);

    @Autowired
    private ShiroDemoMapper shiroDemoMapper;

    /**
     * @Author yangbiao
     * @Description:保存用户信息(注册)
     * @Date 2018/8/5/005 19:35
     */
    @Transactional(rollbackFor = Exception.class)
    public void save(String username, String password) {
        String id= UUID.randomUUID().toString().replace("-","");
        //给密码加密
        String encodePassword = EncodePasswordUtils.encodePassword(password.trim());
//        try {
//            shiroDemoMapper.save(username.trim(),encodePassword);
//        } catch (Exception e) {
//            try {
//                shiroDemoMapper.save(username.trim(),encodePassword);
//            } catch (Exception e1) {
//                log.info("保存用户信息错误");
//                ParameterErrorException.messages("注册失败");
//            }
//        }
        System.err.println("加密密码:"+encodePassword);
        log.info("注册成功:用户名为--"+ UserInfoDic.USERNAME+"--用户密码为--"+UserInfoDic.PASSWORD);
    }
}