package com.yuan.basemodule.ui.base.helper;

import android.content.Context;

import com.yuan.basemodule.R;
import com.yuan.basemodule.ui.base.extend.IRefresh;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

/**
 * Created by YuanYe on 2017/7/12.
 * 初始化下拉刷新控件
 * 参考地址：https://github.com/scwang90/SmartRefreshLayout
 */

public class HRefresh {
    /**
     * 静态代码块只执行一次
     */
    static{
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                BezierRadarHeader header = new BezierRadarHeader(context);
                return header;//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                BallPulseFooter footer = new BallPulseFooter(context);
                return footer;//指定为经典Footer，默认是 BallPulseFooter
            }
        });
    }

    public static void setRefreshLayout(RefreshLayout view, final IRefresh iRefresh){
        RefreshLayout refreshLayout = view;
        //初始化一些默认设置(内容区域是否可以跟随下拉等连动)
        refreshLayout.setEnableHeaderTranslationContent(true);
        refreshLayout.setEnableFooterTranslationContent(true);
        //第一个颜色刷新头部主题色,
        refreshLayout.setPrimaryColorsId(R.color.colorPrimary,R.color.background);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);
                iRefresh.onRefresh();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000);
                iRefresh.onLoad();
            }
        });
    }

}
