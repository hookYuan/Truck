package com.yuan.basemodule.db;

/**
 * Created by YuanYe on 2018/1/5.
 * 数据库访问错误码查询
 */

public class DBErrorCode {

    /**
     * 成功回调
     */
    public static final int tableCreateSuccess = 1001;//表创建成功
    public static final int dataWriteSuccess = 1002;//单条数据写入成功


    /**
     * 失败状态码
     */
    public static final int tableNotExist = 2001;//表不存在
    public static final int SQLException = 2001;//sql语句执行异常
}
