package com.example.demo.other;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/17
 */
public interface UsersRepository extends JpaRepository<User,String> {
}
