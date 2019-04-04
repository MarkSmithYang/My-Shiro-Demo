package com.yb.shiro.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author yangbiao
 * @Description:为用户的密码加密
 * @Date $date$ $time$
 */
public class EncodePasswordUtils {

    public static String encodePassword(String password){
        //进行加密,仅仅只需要一次就可以了,否则它的api不能匹配上(实测)
        String encode = new BCryptPasswordEncoder().encode(password);
        return encode;
    }
    
    public static boolean checkPassword(String inputPassword,String dbPassword){
        //给输入的密码加密
        boolean matches = new BCryptPasswordEncoder().matches(inputPassword, dbPassword);
        return matches;
    }
}