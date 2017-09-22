package com.yuan.basemodule.ui.base.mvp;

/**
 * Created by YuanYe on 2017/9/19.
 */
public class XPresenter<V extends IView> {

    private V View;

    protected void attachView(V View) {
        this.View = View;
    }

    protected V getV() {
        return View;
    }
}
