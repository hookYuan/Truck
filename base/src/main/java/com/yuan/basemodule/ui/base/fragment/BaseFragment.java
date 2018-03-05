package com.yuan.basemodule.ui.base.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuan.basemodule.common.kit.SysTool;
import com.yuan.basemodule.common.log.XLog;
import com.yuan.basemodule.ui.base.mvp.IView;

/**
 * Created by YuanYe on 2017/4/30.
 * 7/10日修复BaseFragment部分bug,完善功能
 */
public abstract class BaseFragment extends Fragment implements IView {

    protected Activity mContext;  //防止getActivity()空指针
    //保存Fragment的状态，防止重启后Fragment重叠
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected View mview;

    public void open(Class clazz) {
        Intent intent = new Intent(mContext, clazz);
        mContext.startActivity(intent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(getLayoutId(), container, false);
        return mview;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //保存状态
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    @TargetApi(23) //API<23不对调用该方法
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    protected void onAttachToContext(Context context) {
        this.mContext = (Activity) context;
    }

    @Override
    public void onResume() {
        this.mContext = this.getActivity();
        super.onResume();
    }


    @Override
    public void onDestroy() {
        SysTool.Input.hideSoftInput(mContext);
        super.onDestroy();
    }

    /**
     * 获取Fragment实例,无其他参数
     */
    public static <T extends BaseFragment> T newInstance(Class<T> packageName) {
        return newInstance(packageName, null);
    }

    /**
     * 获取Fragment实例
     *
     * @param packageName 子类包名
     * @param bundle      需要传递给Fragment的参数
     * @return 返回子类对象实例
     */
    public static <T extends BaseFragment> T newInstance(Class<T> packageName, Bundle bundle) {
        T child = null;
        try {
            child = (T) Class.forName(packageName.getName()).newInstance();
            if (bundle != null) {
                Bundle bundle1 = new Bundle();
                child.setArguments(bundle1);
                //建议通过这样的方式给Fragment传值,内存重启前,系统可以帮你保存数据
                //界面恢复后,不会造成数据的丢失。
                child.setArguments(bundle);
            }
        } catch (ClassNotFoundException e) {
            XLog.e("BaseFragment", packageName + "未找到");
        } catch (java.lang.InstantiationException e) {

        } catch (IllegalAccessException e) {

        }
        return child;
    }

    protected abstract void initData(Bundle savedInstanceState, View parent);
}
