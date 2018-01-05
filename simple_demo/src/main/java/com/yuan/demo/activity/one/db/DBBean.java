package com.yuan.demo.activity.one.db;

/**
 * Created by YuanYe on 2018/1/5.
 */

public class DBBean {
    int id;
    String name;
    String phone;
    String address;
    int age;

    public DBBean() {
    }

    public DBBean(int id, String name, String phone, String address, int age) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "DBBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", age=" + age +
                '}';
    }
}
