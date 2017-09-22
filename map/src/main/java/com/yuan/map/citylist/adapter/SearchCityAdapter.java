package com.yuan.map.citylist.adapter;

import com.yuan.basemodule.ui.base.BaseListAdapter;
import com.yuan.map.R;
import com.yuan.map.citylist.module.CityBean;

import java.util.List;

/**
 * Created by YuanYe on 2017/8/8.
 */

public class SearchCityAdapter extends BaseListAdapter<CityBean> {

    public SearchCityAdapter(List<CityBean> mData) {
        super(mData, R.layout.item_city_list_type1);
    }

    @Override
    public void bindView(ViewHolder holder, CityBean obj) {
        holder.setText(R.id.tv_item_content,obj.getName());
    }
}
