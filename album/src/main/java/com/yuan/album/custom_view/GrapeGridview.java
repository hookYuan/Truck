package com.yuan.album.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Administrator on 2016/10/28.
 * 取消滑动冲突的GridView
 */

public class GrapeGridview extends GridView {

    public GrapeGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GrapeGridview(Context context) {
        super(context);
    }

    public GrapeGridview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
