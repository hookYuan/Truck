package com.yuan.demo.presenter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;

import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.common.other.RxUtil;
import com.yuan.basemodule.common.other.SystemAppUtils;
import com.yuan.basemodule.net.okhttp.okUtil.OKHttpUtil;
import com.yuan.basemodule.net.okhttp.okUtil.base.NetBean;
import com.yuan.basemodule.net.okhttp.okUtil.callback.FileBack;
import com.yuan.basemodule.net.okhttp.okUtil.callback.GsonBack;
import com.yuan.basemodule.net.okhttp.okUtil.callback.GsonBaseBack;
import com.yuan.basemodule.net.okhttp.retrofit.RetrofitBack;
import com.yuan.basemodule.net.okhttp.retrofit.RetrofitUtil;
import com.yuan.basemodule.ui.base.mvp.XPresenter;
import com.yuan.basemodule.ui.dialog.v7.MaterialDialog;
import com.yuan.demo.activity.one.net.JsonBean;
import com.yuan.demo.activity.one.net.NetActivity;
import com.yuan.demo.activity.one.net.RegisterBean;
import com.yuan.demo.activity.one.net.RxCallBack;
import com.yuan.demo.bean.LoginBean;
import com.yuan.demo.net.RequestUrl;

import java.io.File;
import java.util.List;

import okhttp3.Call;

/**
 * Created by YuanYe on 2017/9/19.
 */
public class PNet extends XPresenter<NetActivity> {

    //OKHttp 自己封装----get请求
    public void okUtil() {
        new OKHttpUtil(getV()).url("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.4.255.255")
                .get()
                .execute(new GsonBaseBack<JsonBean>() {

                    @Override
                    public void onSuccess(Call call, JsonBean jsonBean) {
                        new MaterialDialog().alertText(getV(), jsonBean.toString());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        new MaterialDialog().alertText(getV(), "错误:" + e.getMessage());
                    }
                });
    }

    //OKHttp 自己封装----下载文件
    public void okHttpDown() {
        final ProgressDialog progressDialog = new MaterialDialog().alertProgress(getV(), "下载进度", 100);
        new OKHttpUtil(getV()).url("http://122.143.192.38:8010/userFile/131492350696019991.mp4")
                .get()
                .execute(new FileBack() {

                    @Override
                    public void onDownloadSuccess(final String fileDir) {
                        new MaterialDialog().alertText(getV(), "保存路径--" + fileDir, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //播放视频
                                SystemAppUtils.openFile(new File(fileDir), getV());
                            }
                        });
                    }

                    @Override
                    public void onDownloading(int progress) {
                        progressDialog.setProgress(progress);
                        if (progress == 100)
                            progressDialog.dismiss();
                    }

                    @Override
                    public void onDownloadFailed(Exception e) {
                        ToastUtil.showShort(mContext, "失败");
                    }
                });
    }

    //OKHttp 自己封装----上传文件
    public void okHttpUpdate() {
        String path = Environment.getExternalStorageDirectory() + "/Download/12345.mp4";
        new OKHttpUtil(getV()).url("http://192.168.0.24:31132/WebService1.asmx/UploadFile1")
                .uploadFile("yuanye", path)
                .put("password", "123456")
                .put("username", "yuanye")
                .build()
                .execute(new GsonBaseBack() {
                    @Override
                    public void onSuccess(Call call, Object o) {
                        ToastUtil.showShort(getV(), "成功");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        ToastUtil.showShort(getV(), "失败：" + e.getMessage());
                    }
                });
    }

    //OKHttp 自己封装----post请求
    public void okHttpPost() {
        new OKHttpUtil(getV()).url("http://122.143.192.38:8010/userservice.asmx/GetAdmin_Notice_List")
                .post("pageindex", "1")
                .build()
                .execute(new GsonBaseBack() {

                });
    }

    /**
     * OKHttp 自己封装----解析以上三种数据类型
     * ＃第一种：
     * {"success":true,"data":"访问失败"}
     * ＃第二种：
     * {"success":true,"data":{"page":10,"pageCount":29,"list":[{"starLevel":4,"remarkCotnent":"评价方未及时做出评价，系统默认满意！","remarkTime":"2013-02-27 07:21:48","explainContent":"","postMemberId":"y**f","tpLogoURL":"http://i04.c.aliimg.com/cms/upload/2012/186/684/486681_1232736939.png"},{"starLevel":4,"remarkCotnent":"评价方未及时做出评价，系统默认满意！","remarkTime":"2013-02-27 07:21:48","explainContent":"","postMemberId":"y**f","tpLogoURL":"http://i04.c.aliimg.com/cms/upload/2012/186/684/486681_1232736939.png"}],"statistics":{"star5":20,"star4":40,"star3":30,"star2":10,"star1":0}}}
     * ＃第三种：{"success":true,"data":[{"starLevel":4,"remarkCotnent":"评价方未及时做出评价，系统默认满意！","remarkTime":"2013-02-27 07:21:48","explainContent":"","postMemberId":"y**f","tpLogoURL":"http://i04.c.aliimg.com/cms/upload/2012/186/684/486681_1232736939.png"},{"starLevel":4,"remarkCotnent":"评价方未及时做出评价，系统默认满意！","remarkTime":"2013-02-27 07:21:48","explainContent":"","postMemberId":"y**f","tpLogoURL":"http://i04.c.aliimg.com/cms/upload/2012/186/684/486681_1232736939.png"}]}
     */
    public <T> void jsonParse() {
        new OKHttpUtil(getV()).url("http://192.168.0.24:8080/lemon/rs/android/task/tasksPersonal")
                .post("token","7a9f8dd8-13ab-41ba-b7ea-db4250a19a9a")
                .post("username", "lingo")
                .post("password", "1")
                .build()
                .execute(new GsonBack<RegisterBean>() {

                    @Override
                    public Class<RegisterBean> getType() {
                        return RegisterBean.class;
                    }

                    @Override
                    public void onSuccess(Call call, List<RegisterBean> list) {
                        ToastUtil.showShort(mContext, "-list-----------" + list.toString());
                    }

                    @Override
                    public void onSuccess(Call call, String list) {
                        ToastUtil.showShort(mContext, "-list-----------" + list.toString());
                    }
                });
    }

    //Retrofit+Rxjava+RxAndroid+OKHttp ---get网络请求
    public void retorfitGet() {
        RetrofitUtil.create(RequestUrl.class).get("v2/book/1220562")
                .compose(getV().bindToLifecycle())
                .compose(RxUtil.io_main())
                .subscribe(new RetrofitBack<LoginBean>() {
                    @Override
                    public void onSuccess(LoginBean loginBean) {

                    }

                    @Override
                    public void onFailure(Throwable e) {

                    }
                });
    }
}
