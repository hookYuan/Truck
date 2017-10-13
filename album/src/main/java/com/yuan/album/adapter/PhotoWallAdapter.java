package com.yuan.album.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yuan.album.R;
import com.yuan.album.bean.PhotoBean;
import com.yuan.album.ui.AlbumWallAct;
import com.yuan.basemodule.ui.base.BaseListAdapter;

import java.util.List;

/**
 * Created by YuanYe on 2017/10/13.
 * 照片墙Adapter
 */

public class PhotoWallAdapter extends BaseAdapter {

    private AlbumWallAct mContext;
    private boolean isCamera;
    private int num;
    private List<PhotoBean> mData;

    public PhotoWallAdapter(Context mContext, List<PhotoBean> mData,
                            boolean isCamera, int num) {
//        super(mData, R.layout.album_photo_wall_item);
        this.mContext = (AlbumWallAct) mContext;
        this.isCamera = isCamera;
        this.num = num;
        if (isCamera) {
            PhotoBean bean = new PhotoBean();
            mData.add(0, bean);
        }
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
