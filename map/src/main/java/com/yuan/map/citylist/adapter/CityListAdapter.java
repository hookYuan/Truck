package com.yuan.map.citylist.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.yuan.map.R;
import com.yuan.map.citylist.event.CityChangeEvent;
import com.yuan.map.citylist.module.CityBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/8/2.
 */

public class CityListAdapter extends DelegateAdapter.Adapter<CityListAdapter.MainViewHolder> {

    private LayoutHelper mLayoutHelper;
    private Context mContext;
    private int mCount = 0;
    private int mType = 0;  //0.标题 1.热门 2.列表
    ArrayList<CityBean> mList;

    public CityListAdapter(LayoutHelper layoutHelper, int count,
                           ArrayList<CityBean> list,
                           int type) {
        this.mLayoutHelper = layoutHelper;
        this.mCount = count;
        this.mList = list;
        this.mType = type;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public int getItemViewType(int position) {
        if (mType == 0) {
            return R.layout.item_city_list_title;
        } else if (mType == 1) {
            return R.layout.item_city_list_type2;
        } else {
            return R.layout.item_city_list_type1;
        }
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View item = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        return new MainViewHolder(item);
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        switch (mType) {
            case 0:
                holder.content.setText(mList.get(position).getName());
                break;
            case 1:
            case 2:
                holder.content.setText(mList.get(position).getName());
                holder.content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EventBus.getDefault().post(
                                new CityChangeEvent(mList.get(position).getName(),mList.get(position).getCode()));
                    }
                });
                break;
        }
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        TextView content;

        public MainViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.tv_item_content);
        }

        @Override
        protected void finalize() throws Throwable {
            super.finalize();
        }
    }
}
