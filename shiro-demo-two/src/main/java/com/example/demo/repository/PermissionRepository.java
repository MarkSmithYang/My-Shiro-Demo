package com.example.demo.repository;

import com.example.demo.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/28
 */
public interface PermissionRepository extends JpaRepository<Permission,String> {
}
