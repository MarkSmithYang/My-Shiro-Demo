package com.example.demo.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author yangbiao
 * @Description: 密码明码加密工具
 * @date 2018/9/28
 */
public class PasswordEncoderUtils {

    /**
     * 为密码加密
     *
     * @param password
     * @return
     */
    public static String encoderPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode = passwordEncoder.encode(password);
        return encode;
    }

    /**
     * 验证输入密码是否和数据库中密码一致
     *
     * @param password
     * @return
     */
    public static Boolean matchPassword(String password, String dbPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(password, dbPassword);
        return matches;
    }

}
