package com.yuan.demo.activity.two;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuan.album.bean.PhotoBean;
import com.yuan.album.helper.PhotoPreviewHelper;
import com.yuan.album.ui.AlbumWallActivity;
import com.yuan.album.ui.PhotoPreviewActivity;
import com.yuan.basemodule.common.adapter.GridDivider;
import com.yuan.basemodule.common.adapter.RLVAdapter;
import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.net.Glide.GlideHelper;
import com.yuan.basemodule.router.RouterHelper;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.demo.myapplication.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 相册模块
 */
public class AlbumDemoActivity extends MVPActivity {

    @BindView(R.id.rlv_album)
    RecyclerView rlvAlbum;
    @BindView(R.id.rlv_preview)
    RecyclerView rlvPreView;

    RLVAdapter adapter, previewAdapter;
    ArrayList<String> list; //图片数据

    @Override
    protected void initData(Bundle savedInstanceState) {
        getTitleBar().setToolbar("图库选择")
                .setLeftIcon(R.drawable.ic_base_back_white);

        list = new ArrayList<>();

        rlvAlbum.setLayoutManager(new LinearLayoutManager(mContext));
        rlvAlbum.addItemDecoration(new GridDivider((int) Kits.Dimens.dpToPx(mContext, 0.8f), ContextCompat.getColor(mContext, R.color.colorDivider)));
        rlvAlbum.setAdapter(getAdapter());

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlvPreView.setLayoutManager(manager);
        rlvPreView.setAdapter(getPreViewAdapter());

    }

    private RecyclerView.Adapter getPreViewAdapter() {
        if (previewAdapter != null) return previewAdapter;
        return previewAdapter = new RLVAdapter(mContext) {

            @Override
            public int getItemLayout(ViewGroup parent, int viewType) {
                return R.layout.album_photo_wall_item;
            }

            @Override
            public void onBindHolder(ViewHolder holder, int position) {
                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams((int) Kits.Dimens.dpToPx(mContext, 120)
                        , (int) Kits.Dimens.dpToPx(mContext, 120));
                holder.itemView.setLayoutParams(params);
                ImageView imageView = holder.getView(R.id.photo_wall_item_photo);
                GlideHelper.load(list.get(position), imageView);
            }

            @Override
            public void onItemClick(ViewHolder holder, View view, int position) {
                //预览图片
                PhotoPreviewHelper.getInstance().setData(list);
                Intent intent = new Intent(mContext, PhotoPreviewActivity.class);
                intent.putExtra("select_pos", position);
                startActivity(intent);
            }

            @Override
            public int getItemCount() {
                return list.size();
            }
        };
    }

    private RLVAdapter getAdapter() {
        if (adapter != null) return adapter;
        return adapter = new RLVAdapter(mContext) {
            String[] data = {"单选图库", "多选选图库", "剪切图片", "单选视频", "文件选择"};

            @Override
            public int getItemCount() {
                return data.length;
            }

            @Override
            public int getItemLayout(ViewGroup parent, int viewType) {
                return android.R.layout.simple_list_item_1;
            }

            @Override
            public void onBindHolder(ViewHolder holder, int position) {
                TextView textView = (TextView) holder.itemView;
                textView.setText(data[position]);
            }

            @Override
            public void onItemClick(ViewHolder holder, View view, int position) {
                switch (position) {
                    case 0:
                        //吊起图库
                        Intent intent = new Intent(mContext, AlbumWallActivity.class);
                        intent.putExtra("camera", true);
                        intent.putExtra("num", 1);
                        startActivityForResult(intent, 1002);
                        break;
                    case 1:
                        //TODO 路由方式  在Fragment调起Activity后，setResult()无效
                        RouterHelper.from((Activity) mContext)
                                .put("camera", false)
                                .put("num", 8)
                                .to("/album/ui/AlbumWallActivity", 1002);
                        break;
                    case 2: //剪裁图片
                        Intent intent2 = new Intent(mContext, AlbumWallActivity.class);
                        intent2.putExtra("camera", true);
                        intent2.putExtra("crop", true);
                        startActivityForResult(intent2, 1002);
                        break;
                }
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1002: //图库
                ArrayList list = data.getStringArrayListExtra("albumResult");
                this.list.clear();
                this.list.addAll(list);
                rlvPreView.getAdapter().notifyDataSetChanged();
                break;
        }

    }


    @Override
    public int getLayoutId() {
        return R.layout.act_two_album_demo;
    }
}
