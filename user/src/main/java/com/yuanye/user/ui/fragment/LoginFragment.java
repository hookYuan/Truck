package com.yuanye.user.ui.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.yuan.basemodule.router.RouterHelper;
import com.yuan.basemodule.ui.base.fragment.LazyFragement;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import com.yuanye.user.R;
import com.yuan.basemodule.common.other.AndroidBug5497Workaround;

/**
 * Created by YuanYe on 2017/8/9.
 */

public class LoginFragment extends LazyFragement implements View.OnClickListener {

    private ImageView logo;
    private ScrollView scrollView;
    private EditText et_mobile;
    private EditText et_password;
    private ImageView iv_clean_phone;
    private ImageView clean_password;
    private ImageView iv_show_pwd;
    private ImageView iv_ic_phone, iv_ic_lock;
    private RoundTextView btn_login;
    private TextView forget_password;
    private float scale = 0.5f; //logo缩放比例
    private View service, content;
    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private int moveH;  //布局移动的位置
    private float logoH = -50;// logo移动的距离

    @Override
    public int getLayoutId() {
        return R.layout.u_fragment_login;
    }

    @Override
    public void initData(Bundle savedInstanceState, View view) {
        getTitleBar().setTitleAndStatusBgColor(Color.parseColor("#00000000"))
                .setFontColor(ContextCompat.getColor(mContext, R.color.colorFont33))
                .setRightText("注册", this)
                .setLeftIcon(R.drawable.ic_base_back_black);
        AndroidBug5497Workaround.assistActivity(mContext);
        initView(view);
        initListener();
    }


    /**
     * 初始化控件
     */
    private void initView(View mView) {
        logo = (ImageView) mView.findViewById(R.id.logo);
        scrollView = (ScrollView) mView.findViewById(R.id.scrollView);
        et_mobile = (EditText) mView.findViewById(R.id.et_mobile);
        et_password = (EditText) mView.findViewById(R.id.et_password);
        iv_clean_phone = (ImageView) mView.findViewById(R.id.iv_clean_phone);
        clean_password = (ImageView) mView.findViewById(R.id.clean_password);
        iv_show_pwd = (ImageView) mView.findViewById(R.id.iv_show_pwd);
        iv_ic_phone = (ImageView) mView.findViewById(R.id.iv_ic_phone);
        iv_ic_lock = (ImageView) mView.findViewById(R.id.iv_ic_lock);
        btn_login = (RoundTextView) mView.findViewById(R.id.btn_login);
        forget_password = (TextView) mView.findViewById(R.id.forget_password);
        content = mView.findViewById(R.id.content);
        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
    }

    /**
     * 初始化监听
     */
    private void initListener() {
        iv_clean_phone.setOnClickListener(this);
        clean_password.setOnClickListener(this);
        iv_show_pwd.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        et_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_mobile.getText().toString().toString().length() > 0) {
                    iv_ic_phone.setImageResource(R.drawable.ic_u_phone_s);
                    iv_clean_phone.setVisibility(View.VISIBLE);
                } else {
                    iv_clean_phone.setVisibility(View.GONE);
                    iv_ic_phone.setImageResource(R.drawable.ic_u_phone_u);
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (et_password.getText().toString().toString().length() > 0) {
                    iv_ic_lock.setImageResource(R.drawable.ic_u_lock_s);
                    clean_password.setVisibility(View.VISIBLE);
                } else {
                    clean_password.setVisibility(View.GONE);
                    iv_ic_lock.setImageResource(R.drawable.ic_u_lock_u);
                }
            }
        });

        /**
         * 禁止键盘弹起的时候可以滚动
         */
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        scrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                //计算布局移动的高度
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) btn_login.getLayoutParams();
                moveH = btn_login.getHeight() + params.topMargin + params.bottomMargin +
                        forget_password.getHeight();

                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>" + moveH);
                    int dist = content.getBottom() - bottom;
                    if (dist > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", 0.0f, -moveH);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        zoomIn(logo, dist);
                        getTitleBar().setToolbar("登录");
                        //更改间隔
                    }
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>" + (bottom - oldBottom));
                    if ((content.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(content, "translationY", -moveH, 0);
                        mAnimatorTranslateY.setDuration(300);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        //键盘收回后，logo恢复原来大小，位置同样回到初始位置
                        zoomOut(logo);
                        getTitleBar().setToolbar("");
                    }
                }
            }
        });
    }

    /**
     * 缩小
     *
     * @param view
     */
    public void zoomIn(final View view, float dist) {
        if (logoH == -50) {
            logoH = logo.getY() + logo.getHeight();
        }
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, scale);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, scale);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", 0.0f, -logoH);
        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }

    /**
     * f放大
     *
     * @param view
     */
    public void zoomOut(final View view) {
        if (logoH == -50) {
            logoH = logo.getY() + logo.getHeight();
        }
        view.setPivotY(view.getHeight());
        view.setPivotX(view.getWidth() / 2);
        AnimatorSet mAnimatorSet = new AnimatorSet();
        ObjectAnimator mAnimatorScaleX = ObjectAnimator.ofFloat(view, "scaleX", scale, 1.0f);
        ObjectAnimator mAnimatorScaleY = ObjectAnimator.ofFloat(view, "scaleY", scale, 1.0f);
        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", -logoH, 0);
        mAnimatorSet.play(mAnimatorTranslateY).with(mAnimatorScaleX);
        mAnimatorSet.play(mAnimatorScaleX).with(mAnimatorScaleY);
        mAnimatorSet.setDuration(300);
        mAnimatorSet.start();
    }


    @Override
    public void onClick(View view) {
        /**library中不能把ID做为switch判断*/
        if (view.getId() == R.id.iv_clean_phone) {
            et_mobile.setText("");
        } else if (view.getId() == R.id.clean_password) {
            et_password.setText("");
        } else if (view.getId() == R.id.iv_show_pwd) {
            if (et_password.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                iv_show_pwd.setImageResource(R.drawable.ic_u_eye_s);
            } else {
                et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                iv_show_pwd.setImageResource(R.drawable.ic_u_eye_u);
            }
            String pwd = et_password.getText().toString();
            if (!TextUtils.isEmpty(pwd))
                et_password.setSelection(pwd.length());
        } else if (view.getId() == R.id.btn_login) { //登录成功
            RouterHelper.from(mContext).to("/simple/ui/MainActivity");
        } else if (view.getId() == R.id.rl_right_toolbar) { //注册跳转
            RouterHelper.from(mContext).to("/user/ui/activity/RegisterActivity");
        }
    }

    /**
     * 界面销毁时调用
     *
     * @param outState
     */
    @Override
    protected void onSaveState(Bundle outState) {
        super.onSaveState(outState);
        outState.putString("username", et_mobile.getText().toString().trim());
        outState.putString("password", et_password.getText().toString().trim());
    }

    /**
     * 恢复界面时调用
     *
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreState(Bundle savedInstanceState) {
        super.onRestoreState(savedInstanceState);
//        AndroidBug5497Workaround.assistActivity(mContext);
//        initListener();
        onCreate(savedInstanceState);
//        onCreateView(LayoutInflater.from(mContext),)
    }

    @Override
    public ETitleType showToolBarType() {
        return ETitleType.SIMPLE_TITLE;
    }
}
