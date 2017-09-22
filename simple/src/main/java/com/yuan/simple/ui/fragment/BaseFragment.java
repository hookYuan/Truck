package com.yuan.simple.ui.fragment;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yuan.basemodule.ui.base.fragment.LazyFragement;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuan.simple.R;
import com.yuan.simple.adapter.base.BaseIntroduction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuanYe on 2017/9/1.
 */
public class BaseFragment extends LazyFragement {

    RecyclerView rlvBase;

    @Override
    public int getLayoutId() {
        return R.layout.m_fragment_base;
    }

    @Override
    public void initData(Bundle savedInstanceState, View view) {
        initView(view);
        initTitle();
    }

    private void initTitle() {
        getTitleBar().setTitleAndStatusBgColor(ContextCompat.getColor(mContext, com.yuan.basemodule.R.color.transparent))
                .setFontColor(ContextCompat.getColor(mContext, R.color.colorFont33))
                .setToolbar("基础介绍");
        rlvBase.setPadding(rlvBase.getPaddingLeft(), rlvBase.getPaddingTop() + getTitleBar().getTitleBarHeight() +
                        getTitleBar().getStatusBarHeight(),
                rlvBase.getPaddingRight(), rlvBase.getPaddingBottom());
    }

    private void initView(View view) {
        rlvBase = (RecyclerView) view.findViewById(R.id.rlv_base);
        rlvBase.setLayoutManager(new GridLayoutManager(mContext, 3));
        rlvBase.setAdapter(getAdapter());
    }

    @Override
    public ETitleType showToolBarType() {
        return ETitleType.OVERLAP_TITLE;
    }

    public RecyclerView.Adapter getAdapter() {
        List data = new ArrayList<String>();
        data.add("Title");
        data.add("Main");
        data.add("Fragment");
        data.add("Net");
        data.add("Glide");
        data.add("Fragment");
        data.add("Fragment");
        data.add("Fragment");
        data.add("Fragment");
        data.add("Fragment");
        data.add("Fragment");
        data.add("Fragment");
        data.add("Fragment");
        data.add("Fragment");
        data.add("Fragment");
        BaseIntroduction intrAdapter = new BaseIntroduction(data);

        return intrAdapter;
    }
}
