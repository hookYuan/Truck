package com.yuan.demo.presenter;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Environment;

import com.yuan.basemodule.common.log.ToastUtil;
import com.yuan.basemodule.common.other.RxUtil;
import com.yuan.basemodule.common.other.SystemAppUtils;
import com.yuan.basemodule.net.okhttp.okUtil.OKHttp;
import com.yuan.basemodule.net.okhttp.okUtil.OKHttpUtil;
import com.yuan.basemodule.net.okhttp.okUtil.callback.FileBack;
import com.yuan.basemodule.net.okhttp.okUtil.callback.GsonBack;
import com.yuan.basemodule.net.okhttp.okUtil.callback.BaseJsonBack;
import com.yuan.basemodule.net.okhttp.retrofit.RetrofitBack;
import com.yuan.basemodule.net.okhttp.retrofit.RetrofitUtil;
import com.yuan.basemodule.ui.base.mvp.XPresenter;
import com.yuan.basemodule.ui.dialog.v7.DialogHelper;
import com.yuan.demo.activity.one.net.JsonBean;
import com.yuan.demo.activity.one.net.NetActivity;
import com.yuan.demo.activity.one.net.RegisterBean;
import com.yuan.demo.bean.LoginBean;
import com.yuan.demo.net.RequestUrl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by YuanYe on 2017/9/19.
 */
public class PNet extends XPresenter<NetActivity> {

    //OKHttp 自己封装----get请求
    public void okUtil() {
        OKHttpUtil.url("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json&ip=218.4.255.255")
                .get()
                .execute(new BaseJsonBack<JsonBean>() {

                    @Override
                    public void onSuccess(Call call, JsonBean jsonBean) {
                        new DialogHelper(mContext).alertText(jsonBean.toString());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        new DialogHelper(mContext).alertText("错误:" + e.getMessage());
                    }
                });
    }

    //OKHttp 自己封装----下载文件
    public void okHttpDown() {
        final DialogHelper progressDialog = new DialogHelper(getV());
        progressDialog.alertProgress("下载进度", 100);
        new OKHttp(getV()).url("http://122.143.192.38:8010/userFile/131492350696019991.mp4")
                .get()
                .execute(new FileBack() {

                    @Override
                    public void onDownloadSuccess(final String fileDir) {
                        new DialogHelper(mContext).alertText("保存路径--" + fileDir, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //播放视频
                                SystemAppUtils.openFile(new File(fileDir), getV());
                            }
                        });
                    }

                    @Override
                    public void onDownloading(int progress) {
                        progressDialog.setProgressCurrent(progress);
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
        new OKHttp(getV()).url("http://192.168.0.24:8080/lemon/rs/android/moblie/uploadAttchment")
                .file("image", path)
                .post("name", "123456")
                .addHead("name", "456151")
                .execute(new BaseJsonBack() {
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
        OKHttpUtil.url("http://122.143.192.38:8010/userservice.asmx/GetAdmin_Notice_List")
                .post("pageindex", "1")
                .execute(new BaseJsonBack() {

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
        new OKHttp(getV()).url("http://192.168.0.24:8080/lemon/rs/android/task/tasksPersonal")
                .post("token", "7a9f8dd8-13ab-41ba-b7ea-db4250a19a9a")
                .post("username", "lingo")
                .post("password", "1")
                .execute(new GsonBack<RegisterBean>() {

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
    public void retorfitGet(File file) {
        // 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);

        // MultipartBody.Part  和后端约定好Key，这里的partName是用image
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        // 添加描述
        String descriptionString = "hello, 这是文件描述";
        RequestBody description = RequestBody.create(MediaType.parse("multipart/form-data"), descriptionString);

        RetrofitUtil.create(RequestUrl.class, getV()).upload(description, body)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            String str = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {

                    }
                });
//                .compose(getV().bindToLifecycle())
//                .compose(RxUtil.io_main())
//                .subscribe(new RetrofitBack<LoginBean>() {
//                    @Override
//                    public void onSuccess(LoginBean loginBean) {
//
//                    }
//
//                    @Override
//                    public void onFailure(Throwable e) {
//
//                    }
//                });
    }
}
