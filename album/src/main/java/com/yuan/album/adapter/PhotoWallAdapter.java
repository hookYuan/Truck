package com.yuan.album.adapter;

import android.content.Context;

import com.yuan.album.R;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.ui.AlbumWallAct;
import com.yuan.basemodule.ui.base.BaseListAdapter;

import java.util.List;

/**
 * Created by YuanYe on 2017/10/13.
 * 照片墙Adapter
 */

public class PhotoWallAdapter extends BaseListAdapter<PhotoBean> {

    private AlbumWallAct mContext;

    public PhotoWallAdapter(Context mContext, List<PhotoBean> mData) {
        super(mData, R.layout.album_photo_wall_item);
        this.mContext = (AlbumWallAct) mContext;
    }

    @Override
    public void bindView(ViewHolder holder, PhotoBean obj) {

    }
}
