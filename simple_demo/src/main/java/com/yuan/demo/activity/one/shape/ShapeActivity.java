package com.yuan.demo.activity.one.shape;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.yuan.basemodule.common.adapter.BaseListAdapter;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.demo.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 减少shape文件引用
 */
public class ShapeActivity extends MVPActivity implements ISwipeBack {

    GridView gv_simple_demo;
    int selecte = 0;

    @Override
    protected void initData(Bundle savedInstanceState) {
        gv_simple_demo = (GridView) findViewById(R.id.gv_simple_demo);
        gv_simple_demo.setAdapter(getAdapter());
        getTitleBar().setToolbar("RoundView").setLeftIcon(R.drawable.ic_base_back_white);
    }

    private ListAdapter getAdapter() {
        return new BaseListAdapter<String>(getData(), R.layout.shape_gv_item) {
            @Override
            public void bindView(final ViewHolder holder, String obj) {
                holder.setText(R.id.rtv_text, obj);
                if (selecte == holder.getItemPosition()) {
                    holder.getView(R.id.rtv_text).setSelected(true);
                } else {
                    holder.getView(R.id.rtv_text).setSelected(false);
                }
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selecte = holder.getItemPosition();
                        notifyDataSetChanged();
                    }
                });

            }
        };
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        data.add("28");
        data.add("68");
        data.add("128");
        data.add("198");
        data.add("298");
        data.add("自定义");
        return data;
    }


    @Override
    public int getLayoutId() {
        return R.layout.act_shape;
    }
}
