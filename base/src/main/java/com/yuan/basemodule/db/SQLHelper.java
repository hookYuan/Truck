package com.yuan.basemodule.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
public class SQLHelper extends SQLiteOpenHelper {

    /**
     * @param context 上下文对象
     * @param name    数据库文件名
     * @param factory 创建Cursor的工厂类,参数为了可以自定义Cursor创建(ps:一般为null)、
     * @param version 数据库版本号  表示数据库的版本号。如果当前传入的数据库版本号比
     *                上一次创建的版本高，SQLiteOpenHelper就会调用onUpgrade()方法。
     */
    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * @param context      上下文对象
     * @param name         数据库文件名
     * @param factory      创建Cursor的工厂类,参数为了可以自定义Cursor创建(ps:一般为null)、
     * @param version      数据库版本号
     * @param errorHandler 错误信息回调函数
     */
    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    /**
     * 当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行.
     * 重写onCreate方法，调用execSQL方法创建表
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }


    /**
     * 打开数据库
     *
     * @param db
     */
    @Override
    public void onOpen(SQLiteDatabase db) {

    }

    /**
     * 更新数据库
     *
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}
