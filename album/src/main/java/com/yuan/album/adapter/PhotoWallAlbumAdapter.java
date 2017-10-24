package com.yuan.album.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.yuan.album.R;
import com.yuan.album.bean.AlbumBean;
import com.yuan.album.util.PopupWindowUtil;
import com.yuan.basemodule.net.Glide.GlideHelper;
import com.yuan.basemodule.ui.base.BaseListAdapter;

import java.util.List;

/**
 * Created by YuanYe on 2017/10/23.
 * 照片墙 相册列表Adapter
 */
public class PhotoWallAlbumAdapter extends BaseListAdapter<AlbumBean> {

    private Activity mContext;

    public PhotoWallAlbumAdapter(Activity context, List<AlbumBean> mData, int mLayoutRes) {
        super(mData, mLayoutRes);
        this.mContext = context;
    }

    @Override
    public void bindView(final ViewHolder holder, AlbumBean obj) {
        GlideHelper.with(mContext).load(obj.getImgPath())
                .loading(R.mipmap.album_bg).into((ImageView) holder.getView(R.id.iv_album));
        holder.setText(R.id.tv_album_name, obj.getAlbumName());
        holder.setText(R.id.tv_image_number, obj.getNumber() + "张");
        if ((int) (getListView().getTag()) == holder.getItemPosition()) {
            holder.getView(R.id.radioButton).setVisibility(View.VISIBLE);
        } else {
            holder.getView(R.id.radioButton).setVisibility(View.GONE);
        }
        holder.setOnClickListener(R.id.ll_album_item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置Tag
                getListView().setTag(holder.getItemPosition());
                //刷新AlbumWallAct界面数据


                notifyDataSetChanged();
                //隐藏相册目录
                PopupWindowUtil.showMyWindow(getListView());
            }
        });
    }
}
