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
    private boolean isCamera;
    private int num;

    public PhotoWallAdapter(Context mContext, List<PhotoBean> mData,
                            boolean isCamera, int num) {
        super(mData, R.layout.album_photo_wall_item);
        this.mContext = (AlbumWallAct) mContext;
        this.isCamera = isCamera;
        this.num = num;
//        if (isCamera){
//            mData.add();
//        }
    }

    @Override
    public void bindView(ViewHolder holder, PhotoBean obj) {

    }
}
