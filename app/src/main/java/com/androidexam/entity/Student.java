package com.androidexam.entity;

import java.util.Date;

public class Student {
    private Integer id;
    private String name;
    private String className;
    private Boolean gender;
    private String email;
    private Date birthday;
    private String phone;

    public Student() {
    }

    public Student(Integer id, String name, String className, Boolean gender, String email, Date birthday, String phone) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.phone = phone;
    }

    public Student(String name, String className, Boolean gender, String email, Date birthday, String phone) {
        this.name = name;
        this.className = className;
        this.gender = gender;
        this.email = email;
        this.birthday = birthday;
        this.phone = phone;
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

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
