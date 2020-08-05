package com.zyz.cloud.entity;

/**
 * @program:springcloudzyz
 * @description:
 * @author: Mr.zyz
 * @create: 2020-08-05 14:25
 */
public class User {

    private Integer id;

    private String name;

    private String pass;

    public User(Integer id, String name, String pass) {
        this.id = id;
        this.name = name;
        this.pass = pass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
