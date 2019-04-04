package com.example.demo.other;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/20
 */
public interface UserDao extends JpaRepository<User,String> {

}
