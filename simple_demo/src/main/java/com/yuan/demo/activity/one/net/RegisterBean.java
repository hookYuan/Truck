package com.yuan.demo.activity.one.net;

/**
 * Created by YuanYe on 2017/10/18.
 */

public class RegisterBean {

    /**
     * id : 12
     * name : asda
     */

    private String id;
    private String name;

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

    @Override
    public String toString() {
        return "RegisterBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
