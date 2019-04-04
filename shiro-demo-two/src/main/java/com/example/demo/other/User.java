package com.example.demo.other;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author yangbiao
 * @Description:
 * @date 2018/9/11
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 5123020951483359287L;

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;

    private Integer age;

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
