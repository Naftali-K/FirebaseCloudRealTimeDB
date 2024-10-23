package com.nk.firebaserealtimedb.models;

import java.util.List;

/**
 * @Author: naftalikomarovski
 * @Date: 2024/10/18
 */
public class User {

    private String id;
    private String user_name;
    private String family_name;
    private String age;
    private List<DateLogin> dateLogins;

    public User() {
    }

    public User(String user_name, String family_name, String age) {
        this.user_name = user_name;
        this.family_name = family_name;
        this.age = age;
    }

    public User(String id, String user_name, String family_name, String age) {
        this(user_name, family_name, age);
        this.id = id;
    }

    public User(String id, String user_name, String family_name, String age, List<DateLogin> dateLogins) {
        this(id, user_name, family_name, age);
        this.dateLogins = dateLogins;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public List<DateLogin> getDateLogins() {
        return dateLogins;
    }

    public void setDateLogins(List<DateLogin> dateLogins) {
        this.dateLogins = dateLogins;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", family_name='" + family_name + '\'' +
                ", age='" + age + '\'' +
                ", dateLogins=" + dateLogins +
                '}';
    }
}
