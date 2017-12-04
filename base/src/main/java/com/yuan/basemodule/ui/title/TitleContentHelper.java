package com.yuan.basemodule.ui.title;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.yuan.basemodule.R;
import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.common.adapter.BaseListAdapter;
import com.yuan.basemodule.ui.dialog.custom.RxDialog;

import java.util.List;

/**
 * Created by YuanYe on 2017/8/17.
 * 用于设置Title的文字、点击事件等
 * String -- null ：代表没有
 * int  --  -1: 资源ID代表没有
 */
public class TitleContentHelper<T extends TitleContentHelper> extends BaseTitle {


    private int EMPTY_RESOURS_TYPE = -1;
    private String EMPTY_TEXT_TYPE = "";
    private T child;

    protected TitleContentHelper(Context _context, @Nullable AttributeSet attrs) {
        super(_context, attrs);
        child = (T) this;
    }

    public TitleContentHelper(Context _context, @Nullable AttributeSet attrs, T child) {
        super(_context, attrs);
        this.child = child;
    }

    /**
     * ------------------------------------左侧toolbar按钮设置----------------------------------------
     **/
    public T setLeftTextIcon(String text, @DrawableRes int icon, View.OnClickListener listener) {
        if (leftTextView != null && !Kits.Empty.check(text)) {
            leftTextView.setText(text);
        }
        if (icon != -1) {
            Drawable drawable = getResources().getDrawable(icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            leftTextView.setCompoundDrawables(drawable, null, null, null);//画在左边
        }
        if (leftRoot != null) {
            leftRoot.setOnClickListener(listener);
        }
        return child;
    }

    public T setLeftTextIcon(String text, @DrawableRes int icon) {
        return setLeftTextIcon(text, icon, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) context;
                if (activity != null)
                    activity.finish();
            }
        });
    }

    public T setLeftIcon(@DrawableRes int icon) {
        return setLeftTextIcon(EMPTY_TEXT_TYPE, icon);
    }

    public T setLeftIcon(@DrawableRes int icon, View.OnClickListener listener) {
        return setLeftTextIcon(EMPTY_TEXT_TYPE, icon, listener);
    }

    public T setLeftText(String text) {
        return setLeftTextIcon(text, EMPTY_RESOURS_TYPE);
    }

    public T setLeftText(String text, View.OnClickListener listener) {
        return setLeftTextIcon(text, EMPTY_RESOURS_TYPE, listener);
    }


    /**
     * ------------------------------------中间toolbar按钮设置----------------------------------------
     **/
    public T setToolbar(String text) {
        if (centerTextView != null && !Kits.Empty.check(text)) {
            centerTextView.setText(text);
        }
        return child;
    }

    public T setToolbar(@DrawableRes int icon, String centerText) {
        setToolbar(centerText);
        setLeftIcon(icon);
        return child;
    }

    public T setToolbar(String Lefttext, String centerText) {
        setToolbar(centerText);
        setLeftText(Lefttext);
        return child;
    }

    /**
     * ------------------------------------右侧toolbar按钮设置----------------------------------------
     **/
    public T setRightTextIcon(String text, @DrawableRes int icon, OnClickListener listener) {
        if (rightTextView != null && !Kits.Empty.check(text)) {
            rightTextView.setText(text);
        }
        if (icon != EMPTY_RESOURS_TYPE) {
            Drawable drawable = getResources().getDrawable(icon);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
            rightTextView.setCompoundDrawables(drawable, null, null, null);//画在左边
        }
        if (listener != null) {
            rightRoot.setOnClickListener(listener);
        }
        return child;
    }

    public T setRightTextIcon(String text, @DrawableRes int icon) {
        return setRightTextIcon(text, icon, null);
    }

    public T setRightIcon(@DrawableRes int icon) {
        return setRightTextIcon(EMPTY_TEXT_TYPE, icon);
    }

    public T setRightIcon(@DrawableRes int icon, OnClickListener listener) {
        return setRightTextIcon(EMPTY_TEXT_TYPE, icon, listener);
    }

    public T setRightText(String text) {
        return setRightTextIcon(text, EMPTY_RESOURS_TYPE, null);
    }

    public T setRightText(String text, OnClickListener listener) {
        return setRightTextIcon(text, EMPTY_RESOURS_TYPE, listener);
    }

    public T setRightClickEnable(boolean clickAble) {
        if (rightTextView != null) {
            rightTextView.setEnabled(clickAble);
            rightRoot.setClickable(clickAble);
        }
        return child;
    }

    public T setRightAsButton(@DrawableRes int res) {
        if (rightTextView != null) {
            rightTextView.setBackgroundResource(res);
            int left = Kits.Dimens.dpToPxInt(context, 8);
            int top = Kits.Dimens.dpToPxInt(context, 4);
            int right = Kits.Dimens.dpToPxInt(context, 8);
            int bottom = Kits.Dimens.dpToPxInt(context, 4);
            rightTextView.setPadding(left, top, right, bottom);
        }
        return child;
    }

    /**
     * ------------------------------------右侧toolbar的menu菜单按钮设置----------------------------------------
     **/
    public T setRightMenu(final String rightText, @DrawableRes int icon, final List<String> popupData, final OnMenuItemClickListener listener) {
        setRightTextIcon(rightText, icon, new OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopMenu(rightTextView, popupData, listener);
//                showRxDialog(popupData,listener);
            }
        });
        return child;
    }

    public T setRightMenu(String rightText, final List<String> popupData, final OnMenuItemClickListener listener) {
        setRightMenu(rightText, 0, popupData, listener);
        return child;
    }

    public T setRightMenu(@DrawableRes int icon, final List<String> popupData, final OnMenuItemClickListener listener) {
        setRightMenu("", icon, popupData, listener);
        return child;
    }


    /**
     * 自定义弹窗
     * TODO 有bug,View中放listView无法全部展开
     */
    public void showRxDialog(List<String> popupData, AdapterView.OnItemClickListener listener) {
        final View popupView = LayoutInflater.from(context).inflate(R.layout.title_menu, null);

        ListView listView = (ListView) popupView.findViewById(R.id.pop_listView);
        listView.setAdapter(new BaseListAdapter<String>(popupData, R.layout.title_menu_item) {
            @Override
            public void bindView(ViewHolder holder, String obj) {
                holder.setText(R.id.tv_item_content, obj);
            }
        });
        listView.setOnItemClickListener(listener);

        new RxDialog(popupView).setViewBottom(rightTextView)
                .setGravity(Gravity.RIGHT)
                .setMargin(0, 0, Kits.Dimens.dpToPxInt(context, 16), 0)
                .show();
    }


    private PopupWindow popupWindowMenu;

    public void showPopMenu(View view, List<String> popupData, final OnMenuItemClickListener listener) {
        if (popupWindowMenu != null && popupWindowMenu.isShowing()) {
            //关闭popupWindow
            popupWindowMenu.dismiss();
        } else {
            final View popupView = LayoutInflater.from(context).inflate(R.layout.title_menu, null);
            popupWindowMenu = new PopupWindow(popupView, ListPopupWindow.WRAP_CONTENT, ListPopupWindow.WRAP_CONTENT, true);

            //设置弹出的popupWindow不遮挡软键盘
            popupWindowMenu.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
            popupWindowMenu.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

            //设置点击外部让popupWindow消失
            popupWindowMenu.setFocusable(true);//可以试试设为false的结果
            popupWindowMenu.setOutsideTouchable(true); //点击外部消失

            //必须设置的选项
            popupWindowMenu.setBackgroundDrawable(ContextCompat.getDrawable(context, android.R.color.transparent));
            popupWindowMenu.setTouchInterceptor(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                    // 这里如果返回true的话，touch事件将被拦截
                    // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                }
            });
            //将window视图显示在点击按钮下面(向上偏移20像素)
            popupWindowMenu.showAsDropDown(view, 0, 0);
            ListView listView = (ListView) popupView.findViewById(R.id.pop_listView);
            listView.setAdapter(new BaseListAdapter<String>(popupData, R.layout.title_menu_item) {
                @Override
                public void bindView(ViewHolder holder, String obj) {
                    holder.setText(R.id.tv_item_content, obj);
                }
            });
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    listener.onItemClick(popupWindowMenu, adapterView, view, i);
                }
            });
        }
    }
}
