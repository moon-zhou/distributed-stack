package com.moonzhou.springbooteasyexcel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import java.util.Date;

public class User {
    @ExcelProperty("用户编号")
    private Long id;

    @ExcelProperty("用户姓名")
    private String name;

    @ExcelProperty("用户年龄")
    private Integer age;

    @ExcelProperty("用户邮箱")
    private String email;

    @ExcelProperty("创建时间")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}