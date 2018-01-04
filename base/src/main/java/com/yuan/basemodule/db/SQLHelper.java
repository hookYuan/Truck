package com.yuan.basemodule.db;

import android.database.DatabaseUtils;
import android.support.v4.database.DatabaseUtilsCompat;

/**
 * Created by YuanYe on 2018/1/3.
 * 简化SQL 语句操作
 * 1、实现数据库连接
 * 2、创建库
 * 3、创建表
 * 4、更新表字段
 * 5、删除表字段
 * 6、按id查询数据库
 * 7、模糊查询
 */

public class SQLHelper {

    private DatabaseUtils databaseUtils;

    private SQLHelper(){
        databaseUtils = new DatabaseUtils();

//        DatabaseUtilsCompat compat = new DatabaseUtilsCompat();
//        DatabaseUtilsCompat.
    }
}
