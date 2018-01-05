package com.yuan.basemodule.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by YuanYe on 2018/1/5.
 * 管理SQLHelper  简化SQL语句操作
 * 1、实现数据库连接
 * 2、创建库
 * 3、创建表
 * 4、更新表字段
 * 5、删除表字段
 * 6、按id查询数据库
 * 7、模糊查询
 */

public class SQLManager {

    private SQLiteDatabase writDB; //写操作
    private SQLHelper helper;

    private int errorCode = 0; //错误码

    /**
     * 新建数据库，保存到项目目录下
     */
    public SQLManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        helper = new SQLHelper(context, name, factory, version);
        writDB = helper.getWritableDatabase();
    }

    /**
     * 打开指定路径数据库，操作其数据库文件
     */
    public SQLManager() {

    }

    /**
     * 创建表
     *
     * @param tableName            表名
     * @param map<String1,String2> 字段名集合 String1--字段名，String2--字段类型
     *                             .默认主键为_id,自增
     */
    public void createTable(String tableName, Map<String, String> map) {
        checkWritDB();
        String sql = "create table if not exists " + tableName + "(";//创建表
        sql = sql + "_id " + "integer " + " not null primary key autoincrement,";
        int i = 0;
        //默认添加主键
        for (Map.Entry<String, String> entry : map.entrySet()) {
            i++;
            if (i != map.size()) {
                sql = sql + entry.getKey() + " " + entry.getValue() + ",";
            } else {
                sql = sql + entry.getKey() + " " + entry.getValue();
            }
        }
        sql = sql + ")";
        writDB.execSQL(sql);
        writDB.close();
        errorCode = DBErrorCode.tableCreateSuccess;
    }

    /**
     * 批量插入数据
     * 采用事务处理，一次提交，提高数据库读写速度
     */
    public void insert(String tableName, List<Object> list) {
        checkWritDB();
        writDB.beginTransaction(); // 手动设置开始事务
        for (int i = 0; i < list.size(); i++) {
            insert(tableName, list.get(i));
        }
        writDB.setTransactionSuccessful(); // 设置事务处理成功，不设置会自动回滚不提交
        writDB.endTransaction(); // 处理完成
        writDB.close();
    }

    /**
     * 插入一条数据
     *
     * @param tableName 表名
     * @param object    插入对象
     */
    public void insert(String tableName, Object object) {
        checkWritDB();
        Class clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();//获取该类所有的属性
        ContentValues value = new ContentValues();
        for (Field field : fields) {
            try {
                field.setAccessible(true); //取消对age属性的修饰符的检查访问，以便为属性赋值
                String content = field.get(object) + "";//获取该属性的内容
                value.put(field.getName(), content);
                field.setAccessible(false);//恢复对age属性的修饰符的检查访问
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        writDB.insert(tableName, null, value);
    }

    public <T> ArrayList<T> queryTableAll(String tableName, Class<T> clazz) {
        return queryTableAll(tableName, null, clazz);
    }

    /**
     * 查询整张表
     *
     * @param tableName 表名
     * @param orderBy   排序字段名
     */
    public <T> ArrayList<T> queryTableAll(String tableName, String orderBy, Class<T> clazz) {
        checkWritDB();
        String orderby = null;
        if (!TextUtils.isEmpty(orderBy)) orderby = orderBy + " desc";

        ArrayList<T> list = new ArrayList<>();
        //查询获得游标
        try {
            Cursor cursor = writDB.query(tableName, null, null, null, null, null, orderby);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                T t = clazz.newInstance();
                for (int i = 1; i < cursor.getColumnCount(); i++) {
                    String content = cursor.getString(i);//获得获取的数据记录第i条字段的内容
                    String columnName = cursor.getColumnName(i);// 获取数据记录第i条字段名的
                    Field field = t.getClass().getDeclaredField(columnName);//获取该字段名的Field对象。
                    field.setAccessible(true);//取消对age属性的修饰符的检查访问，以便为属性赋值
                    if (field.getType() == String.class) {
                        field.set(t, content);
                    } else if (field.getType() == Integer.class || field.getType() == int.class) {
                        field.set(t, Integer.parseInt(content));
                    } else if (field.getType() == Float.class || field.getType() == float.class) {
                        field.set(t, Float.parseFloat(content));
                    } else if (field.getType() == Double.class || field.getType() == double.class) {
                        field.set(t, Double.parseDouble(content));
                    } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
                        field.set(t, Boolean.parseBoolean(content));
                    }
                    field.setAccessible(false);//恢复对age属性的修饰符的检查访问
                }
                list.add(t);
                cursor.moveToNext();
            }
            writDB.close();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            if (e instanceof InvocationTargetException) {
                errorCode = DBErrorCode.tableNotExist;
            } else if (e instanceof SQLException) {
                errorCode = DBErrorCode.SQLException;
            }
        }
        return list;
    }

    /**
     * 检验Db是否开启
     */
    private void checkWritDB() {
        if (!writDB.isOpen()) {
            writDB = helper.getWritableDatabase();
        }
    }
}
