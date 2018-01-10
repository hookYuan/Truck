package com.yuan.basemodule.common.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuan.basemodule.R;
import com.yuan.basemodule.common.other.Views;


/**
 * Created by YuanYe on 2017/12/18.
 * 简化RecyclerView的Adapter代码
 */
public abstract class RLVAdapter extends RecyclerView.Adapter<RLVAdapter.ViewHolder> implements View.OnClickListener {

    Context mContext;

    public RLVAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder;
        if (getItemView(parent, viewType) != null && getItemLayout(parent, viewType) == 0) {
            viewHolder = new ViewHolder(getItemView(parent, viewType));
        } else {
            View itemView = Views.inflate(parent, getItemLayout(parent, viewType));
            viewHolder = new ViewHolder(itemView);
        }
        return viewHolder;
    }

    public abstract
    @LayoutRes
    int getItemLayout(ViewGroup parent, int viewType);

    public View getItemView(ViewGroup parent, int viewType) {
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private SparseArray<View> mViews;

        public ViewHolder(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }

        /**
         * 获取View
         */
        public <k extends View> k getView(@IdRes int resId) {
            k k = (k) mViews.get(resId);
            if (k == null) {
                k = Views.find(itemView, resId);
                mViews.put(resId, k);
            }
            return k;
        }

        /**
         * TextView设置文字
         */
        public void setText(@IdRes int resId, CharSequence text) {
            View view = getView(resId);
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
        }

        /**
         * 添加事件监听
         */
        public void setOnclick(@IdRes int resId, int position) {
            getView(resId).setTag(R.id.item_position, position);
            getView(resId).setOnClickListener(RLVAdapter.this);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(R.id.item_position, position);
        holder.itemView.setTag(R.id.item_holder, holder);
        holder.itemView.setOnClickListener(this);
        onBindHolder(holder, position);
    }

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     */
    public abstract void onBindHolder(ViewHolder holder, int position);

    /**
     * item的点击事件
     */
    public abstract void onItemClick(ViewHolder holder, View view, int position);

    @Override
    public void onClick(View view) {
        if (view.getTag(R.id.item_position) != null) {
            int position = (int) view.getTag(R.id.item_position);
            if (view.getTag(R.id.item_holder) != null){
                ViewHolder holder = (ViewHolder) view.getTag(R.id.item_holder);
                if (holder.itemView.getId() == view.getId()) {
                    onItemClick(holder, view, position);
                }
            }
        }
    }
}
