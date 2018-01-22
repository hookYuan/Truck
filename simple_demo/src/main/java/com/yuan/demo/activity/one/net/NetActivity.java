package com.yuan.demo.activity.one.net;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupWindow;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.roundview.RoundTextView;
import com.yuan.album.ui.AlbumWallActivity;
import com.yuan.basemodule.net.Glide.GlideHelper;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.basemodule.ui.dialog.v7.DialogHelper;
import com.yuan.basemodule.ui.title.OnMenuItemClickListener;
import com.yuan.demo.myapplication.R;
import com.yuan.demo.presenter.PNet;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

@Route(path = "/net/NetActivity")
public class NetActivity extends MVPActivity<PNet> implements ISwipeBack, View.OnClickListener {

    @BindView(R.id.rtv_demo_json)
    RoundTextView json;

    @Override
    public int getLayoutId() {
        return R.layout.activity_net;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ArrayList<String> menus = new ArrayList<>();
        menus.add("OkHttpUtil");
        menus.add("Retrofit");
        menus.add("说明");
        menus.add("Gson解析");
        getTitleBar().setToolbar("OkHttp")
                .setLeftIcon(R.drawable.ic_base_back_white)
                .setRightMenu(R.drawable.ic_base_menu_more_white, menus, new OnMenuItemClickListener() {
                    @Override
                    public void onItemClick(PopupWindow popupWindow, AdapterView<?> adapterView, View view, int i) {
                        popupWindow.dismiss();
                        if (i == 2) {
                            new DialogHelper(mContext).alertText("说明",
                                    "本例中主要分为两个模块：" +
                                            "\r\n\t\t1.OkHttpUtil。此模块基于OKHttp3.0封装，线程切换采用RxJava.实现了基本的" +
                                            "get、post、上传、下载"
                                            + "\r\n\r\n \t\t\t\t\t\t\t\t\t  ----作者：袁冶"
                                    , null);
                        }
                        if (i == 3) {
                            open(GsonActivity.class);
                        }
                    }
                });
        initView();
    }


    private void initView() {
        RoundTextView rtvGet = (RoundTextView) findViewById(R.id.rtv_demo_get);
        rtvGet.setOnClickListener(this);
        RoundTextView rtvPost = (RoundTextView) findViewById(R.id.rtv_demo_post);
        rtvPost.setOnClickListener(this);
        RoundTextView rtvDown = (RoundTextView) findViewById(R.id.rtv_demo_down);
        rtvDown.setOnClickListener(this);
        RoundTextView rtvUpdate = (RoundTextView) findViewById(R.id.rtv_demo_update);
        rtvUpdate.setOnClickListener(this);
        json.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rtv_demo_get:
                getP().okUtil();
                break;
            case R.id.rtv_demo_post:
                getP().okHttpPost();
                break;
            case R.id.rtv_demo_down:
                getP().okHttpDown();
                break;
            case R.id.rtv_demo_update:
                getP().okHttpUpdate();
                break;
            case R.id.rtv_demo_json:
//                String jsonStr = "{\"success\":true,\"data\":\"访问失败\"}";
//                String jsonObj = "{\"success\":true,\"data\":{\"page\":10,\"pageCount\":29,\"list\":[{\"starLevel\":4,\"remarkCotnent\":\"评价方未及时做出评价，系统默认满意！\",\"remarkTime\":\"2013-02-27 07:21:48\",\"explainContent\":\"\",\"postMemberId\":\"y**f\",\"tpLogoURL\":\"http://i04.c.aliimg.com/cms/upload/2012/186/684/486681_1232736939.png\"},{\"starLevel\":4,\"remarkCotnent\":\"评价方未及时做出评价，系统默认满意！\",\"remarkTime\":\"2013-02-27 07:21:48\",\"explainContent\":\"\",\"postMemberId\":\"y**f\",\"tpLogoURL\":\"http://i04.c.aliimg.com/cms/upload/2012/186/684/486681_1232736939.png\"}],\"statistics\":{\"star5\":20,\"star4\":40,\"star3\":30,\"star2\":10,\"star1\":0}}}";
//                String jsonList = "{\"success\":true,\"data\":[{\"starLevel\":4,\"remarkCotnent\":\"评价方未及时做出评价，系统默认满意！\",\"remarkTime\":\"2013-02-27 07:21:48\",\"explainContent\":\"\",\"postMemberId\":\"y**f\",\"tpLogoURL\":\"http://i04.c.aliimg.com/cms/upload/2012/186/684/486681_1232736939.png\"},{\"starLevel\":4,\"remarkCotnent\":\"评价方未及时做出评价，系统默认满意！\",\"remarkTime\":\"2013-02-27 07:21:48\",\"explainContent\":\"\",\"postMemberId\":\"y**f\",\"tpLogoURL\":\"http://i04.c.aliimg.com/cms/upload/2012/186/684/486681_1232736939.png\"}]}";
//                getP().jsonParse();
//                getP().retorfitGet();
//                getP().jsonParse();
                //打开图库
                Intent intent2 = new Intent(mContext, AlbumWallActivity.class);
                intent2.putExtra("camera", true);
                intent2.putExtra("crop", true);
                startActivityForResult(intent2, 1002);

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1002: //图库
                ArrayList<String> list = data.getStringArrayListExtra("albumResult");
                if (list.size() > 0) {
                    String fileurl = list.get(0);
                    getP().retorfitGet(new File(fileurl));
                }
                break;
        }

    }
}
