package com.yuan.map.citylist.module;

/**
 * Created by YuanYe on 2017/6/26.
 */
public class CityBean {
    String pinyin;  //汉字全拼
    String sortLetters; //汉字首字母（大写）
    String name; //city名称
    String code;//城市编码
    public CityBean(String name) {
        this.name = name;
    }

    public CityBean(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getSortLetters() {
        //转大写
        return sortLetters.toUpperCase();
    }
    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
