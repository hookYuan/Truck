package com.yuan.simple.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuan.simple.R;

import java.util.List;

/**
 * Created by YuanYe on 2017/9/1.
 */
public class BaseIntroduction extends RecyclerView.Adapter<BaseIntroduction.ViewHolder> {

    List<String> data;

    public BaseIntroduction(List<String> data) {
        this.data = data;
    }

    @Override
    public BaseIntroduction.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.b_item_main,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(BaseIntroduction.ViewHolder holder, int position) {
        holder.tvContent.setText(data.get(position));
        holder.tvContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvContent = (TextView) itemView.findViewById(R.id.tv_main_content);
        }
    }
}
