package com.yuan.basemodule.ui.title;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuan.basemodule.R;

/**
 * Created by YuanYe on 2017/8/17.
 * Title基本布局加载
 */
class BaseTitle extends LinearLayout {

    protected Context context;
    protected LinearLayout rootView; //根布局

    protected View statusBarView;//状态栏
    protected RelativeLayout statusRoot;//状态栏根View

    protected FrameLayout titleRootView;//titleBarRootView
    protected RelativeLayout leftRoot;//左侧根布局
    protected RelativeLayout centerRoot;//中间根布局
    protected RelativeLayout rightRoot;//右侧根布局

    protected TextView leftTextView;//左侧
    protected TextView centerTextView;//中间
    protected TextView rightTextView;//右侧

    protected View lineTitle;
    protected int floatZ = 4; //悬浮的高度默认悬浮4dp

    protected String TAG = "TitleBar";

    protected BaseTitle(Context _context, @Nullable AttributeSet attrs) {
        super(_context, attrs);
        this.context = _context;
        initView();
    }

    /**
     * 初始化布局文件
     */
    private void initView() {
        rootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.title_tool_bar, this, true);

        statusRoot = (RelativeLayout) findViewById(R.id.rl_status);
        statusBarView = findViewById(R.id.status_bar);

        titleRootView = (FrameLayout) findViewById(R.id.fl_title_bar);
        leftRoot = (RelativeLayout) findViewById(R.id.rl_left_toolbar);
        centerRoot = (RelativeLayout)findViewById(R.id.rl_center_toolbar);
        rightRoot = (RelativeLayout) findViewById(R.id.rl_right_toolbar);
        leftTextView = (TextView) findViewById(R.id.tv_left);
        centerTextView = (TextView) findViewById(R.id.tv_center);
        rightTextView = (TextView) findViewById(R.id.tv_right);

        lineTitle = findViewById(R.id.title_line);
    }

}
