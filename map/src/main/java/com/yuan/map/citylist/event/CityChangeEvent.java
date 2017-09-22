package com.yuan.map.citylist.event;

/**
 * Created by YuanYe on 2017/8/8.
 * 城市选择
 */
public class CityChangeEvent {

    private String mMsg;
    private int code;
    public CityChangeEvent(String msg,String code) {
        // TODO Auto-generated constructor stub
        mMsg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg(){
        return mMsg;
    }
}
