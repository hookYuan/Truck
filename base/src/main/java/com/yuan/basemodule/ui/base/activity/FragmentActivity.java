package com.yuan.basemodule.ui.base.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.yuan.basemodule.ui.base.fragment.ExtraFragment;
import com.yuan.basemodule.ui.base.fragment.LazyFragement;
import com.yuan.basemodule.ui.base.comm.ETitleType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuanYe on 2017/7/10.
 * 包含Fragment的Activity可直接继承该类
 * 使用方法：
 * 新建Activity继承FragmentActivity,
 * 在initData()中调用 addFragment()初始化
 * 调用showFragment()切换Fragment
 */
public abstract class FragmentActivity<T extends LazyFragement> extends ExtraActivity {

    @IdRes
    private int container; //Fragment需要放置的容器
    private Class[] clazzs;
    private int showIndex = 0; //需要默认显示Fragment下标,默认显示第一页
    private List<Fragment> fragments;  //用于放置Fragment

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        fragments = new ArrayList<>();
        if (clazzs == null || clazzs.length == 0) {

        } else if (savedInstanceState != null) {
            // 解决重叠问题
            for (int i = 0; i < clazzs.length; i++) {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(clazzs[i].getName());
                fragments.add(fragment);
                if (i == showIndex)
                    ft.show(fragment);
                else
                    ft.hide(fragment);
            }
            ft.commit();
        } else {
            for (int i = 0; i < clazzs.length; i++) {
                LazyFragement fragment = (LazyFragement) LazyFragement.newInstance(clazzs[i]);
                fragments.add(fragment);
                ft.add(container, fragment, clazzs[i].getName());
                if (i == showIndex) {
                    ft.show(fragment);
                    fragment.setUserVisibleHint(true);
                } else {
                    ft.hide(fragment);
                    fragment.setUserVisibleHint(false);
                }
            }
            ft.commit();
        }
    }

    @Override
    public ETitleType showToolBarType() {
        return ETitleType.NO_TITLE;
    }

    /**
     * 根据下标，设置要展示的Fragment
     *
     * @param newIndex 要显示的Fragment的下标
     */
    protected void showFragment(int newIndex) {
        if (showIndex == newIndex) return;
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.show(fragments.get(newIndex))
                .hide(fragments.get(showIndex))
                .commit();
        fragments.get(newIndex).setUserVisibleHint(true);
        showIndex = newIndex;
    }

    /**
     * 把Fragment添加到FragmentActivity中
     *
     * @param container   需要添加的布局ID
     * @param packageName Fragment包全名
     */
    protected void addFragment(@IdRes int container, Class<T>... packageName) {
        addFragment(container, 0, packageName);
    }

    /**
     * 把Fragment添加到FragmentActivity中
     *
     * @param showIndex 默认显示的页数
     */
    protected void addFragment(@IdRes int container, int showIndex, Class<T>... packageName) {
        showIndex = showIndex >= packageName.length ? 0 : showIndex;
        this.container = container;
        this.clazzs = packageName;
        this.showIndex = showIndex;
    }
}
