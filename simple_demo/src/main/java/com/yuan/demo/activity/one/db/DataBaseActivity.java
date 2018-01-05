package com.yuan.demo.activity.one.db;

import android.os.Bundle;
import android.view.View;

import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.db.SQLManager;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.demo.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * 数据库操作测试类
 */
public class DataBaseActivity extends MVPActivity implements ISwipeBack {

    SQLManager manager;

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_create_db:
                manager = new SQLManager(mContext, "yuedao", null, 1);
                break;
            case R.id.tv_create_table:
                Map<String, String> map = new HashMap();
                map.put("id", "integer");
                map.put("name", "varchar(20)");
                map.put("phone", "varchar(20)");
                map.put("address", "varchar(20)");
                map.put("age", "integer");
                manager.createTable("user1", map);
                break;
            case R.id.tv_write_data:
                DBBean bean = new DBBean(1, "袁冶", "1565654651", "重庆", 25);
                manager.insert("us1", bean);
                break;
            case R.id.tv_query_data:
                ArrayList<DBBean> list = manager.queryTableAll("us1", DBBean.class);
                ToastUtil.showShort(mContext, list.toString());
                break;
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_data_base;
    }
}
