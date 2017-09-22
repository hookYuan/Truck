package com.yuan.map.citylist.ui;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.yuan.basemodule.common.cache.SPUtil;
import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.ui.base.activity.ExtraActivity;
import com.yuan.basemodule.ui.base.extend.ISwipeBack;
import com.yuan.map.MConfig;
import com.yuan.map.R;
import com.yuan.map.citylist.adapter.CityListAdapter;
import com.yuan.map.citylist.adapter.SearchCityAdapter;
import com.yuan.map.citylist.event.CityChangeEvent;
import com.yuan.map.citylist.module.CityBean;
import com.yuan.map.citylist.module.CityManager;
import com.yuan.map.citylist.view.SideLetterBar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 城市选择界面
 * 数据从CityManager中获取，通过回调回传数据。
 * 布局采用vLayout方式实现.启动方式建议通过ARouter方式启动
 * Created by YuanYe on 2017/6/26.
 */
@Route(path = "/map/citylist/ui/MapSelectCityActivity")
public class MapSelectCityActivity extends ExtraActivity implements ISwipeBack, CityManager.ICityReadyListener,
        TextWatcher, AdapterView.OnItemClickListener {

    ListView lv_search_city;
    RecyclerView rv_all_city;
    EditText et_search;
    private ArrayList<CityBean> currentCity; //当前城市
    private ArrayList<CityBean> hotCityList; //热门城市
    private ArrayList<CityBean> allCityList;  //所有城市
    private ArrayList<CityBean> searchList;//搜索的城市列表
    SideLetterBar side_letter_bar;
    LinearLayout empty_view; //未搜索到数据
    TextView overlay;
    private List<Title> titlePos;
    private List<DelegateAdapter.Adapter> adapters;
    private static final boolean LOCATION_LAYOUT = true;
    private static final boolean HOT_LAYOUT = true;
    private static final boolean CITY_LAYOUT = true;

    @Override
    public int getLayoutId() {
        return R.layout.map_act_select_city;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        getTitleBar().setLeftIcon(R.drawable.ic_base_back_white)
                .setToolbar("城市选择");
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        rv_all_city = (RecyclerView) findViewById(R.id.rv_all_city);
        lv_search_city = (ListView) findViewById(R.id.lv_search_city);
        lv_search_city.setOnItemClickListener(this);
        side_letter_bar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        empty_view = (LinearLayout) findViewById(R.id.empty_view);
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.addTextChangedListener(this);
        side_letter_bar.setOverlay(overlay);
        CityManager manager = new CityManager(mContext);
        manager.setCityReadyListener(this);
        hotCityList = new ArrayList<>();
        hotCityList.add(new CityBean("北京", "010101"));
        hotCityList.add(new CityBean("上海", "020101"));
        hotCityList.add(new CityBean("天津", "030101"));
        hotCityList.add(new CityBean("重庆", "040101"));
        hotCityList.add(new CityBean("哈尔滨", "050101"));
        hotCityList.add(new CityBean("杭州", "210101"));
        hotCityList.add(new CityBean("广州", "280101"));
        hotCityList.add(new CityBean("深圳", "280601"));
        hotCityList.add(new CityBean("成都", "270101"));
        titlePos = new ArrayList<>();
    }


    /**
     * 加载数据后回调
     *
     * @param list
     */
    @Override
    public void onCityReadyListener(ArrayList<CityBean> list) {
        allCityList = list;
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(mContext);
        rv_all_city.setLayoutManager(layoutManager);
        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        rv_all_city.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 30);
        //设置Helper
        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        rv_all_city.setAdapter(delegateAdapter);
        delegateAdapter.setAdapters(getAdapters(list));
        //设置滑动监听
        side_letter_bar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(int pos, String letter) {
                for (int i = 0; i < titlePos.size(); i++) {
                    if (letter.equals(titlePos.get(i).getLetter())) {
                        rv_all_city.scrollToPosition(titlePos.get(i).getPosition());
                        rv_all_city.getAdapter().notifyDataSetChanged();
                        return;
                    }
                }
            }
        });
    }

    /**
     * 输入框框监听
     *
     * @param charSequence
     * @param start
     * @param count
     * @param after
     */
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        if (searchList == null) {
            searchList = new ArrayList<>();
        } else {
            searchList.clear();
        }
        empty_view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if ("".equals(et_search.getText().toString()))
            return;
        //检索城市列表
        for (int i = 0; i < allCityList.size(); i++) {
            if (allCityList.get(i).getName().contains(et_search.getText().toString().trim())) {
                searchList.add(allCityList.get(i));
            }
        }
        if (searchList.size() == 0) {
            empty_view.setVisibility(View.VISIBLE);
        } else {
            empty_view.setVisibility(View.GONE);
            lv_search_city.setVisibility(View.VISIBLE);
        }
        lv_search_city.setAdapter(new SearchCityAdapter(searchList));
    }

    /**
     * 搜索列表点击监听
     *
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        EventBus.getDefault().post(
                new CityChangeEvent(searchList.get(i).getName(), searchList.get(i).getCode()));
    }

    //处理Event事件
    @Subscribe
    public void onEventMainThread(CityChangeEvent event) {
        String msg = event.getMsg();
        if (!"".equals(msg)) {
            empty_view.setVisibility(View.GONE);
            lv_search_city.setVisibility(View.GONE);
            rv_all_city.scrollToPosition(0); //滚动到第一位
            //更改数据定位
            currentCity.get(0).setName(msg);
            getAdapters(allCityList).get(1).notifyDataSetChanged();
            SPUtil.put(mContext,MConfig.LASTSELECTCITY, msg);
            SPUtil.put(mContext,MConfig.LASTSELECTCODE, event.getCode());
        }
    }

    /**
     * 设置根据数据初始化Adapter集合
     *
     * @param list
     * @return
     */
    private List<DelegateAdapter.Adapter> getAdapters(ArrayList<CityBean> list) {
        if (adapters != null)
            return adapters;
        adapters = new LinkedList<>();
        if (LOCATION_LAYOUT) {
            //定位当前城市标题
            StickyLayoutHelper title = new StickyLayoutHelper();
            ArrayList<CityBean> titleList = new ArrayList<>();
            titleList.add(new CityBean("定位"));
            adapters.add(new CityListAdapter(title, 1, titleList, 0));
            titlePos.add(new Title("定位", 0));
            //定位当前城市
            GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
            gridLayoutHelper.setMargin(Kits.Dimens.dpToPxInt(mContext, 8),
                    0,
                    Kits.Dimens.dpToPxInt(mContext, 28),
                    0);
            gridLayoutHelper.setAutoExpand(false);
            currentCity = new ArrayList<>();
            if (!"".equals(SPUtil.get(mContext,MConfig.LASTSELECTCITY, ""))) {
                currentCity.add(new CityBean((String)SPUtil.get(mContext,MConfig.LASTSELECTCITY, ""),
                        (int)SPUtil.get(mContext,MConfig.LASTSELECTCODE, 0)+""));
            } else
                currentCity.add(new CityBean("北京", "010101"));
            adapters.add(new CityListAdapter(gridLayoutHelper, 1, currentCity, 1));
        }

        if (HOT_LAYOUT) {
            //城市热门标题
            StickyLayoutHelper title = new StickyLayoutHelper();
            ArrayList<CityBean> titleList = new ArrayList<>();
            titleList.add(new CityBean("热门"));
            adapters.add(new CityListAdapter(title, 1, titleList, 0));
            titlePos.add(new Title("热门", 2));
            //城市热门
            GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(3);
            gridLayoutHelper.setMargin(Kits.Dimens.dpToPxInt(mContext, 8),
                    0,
                    Kits.Dimens.dpToPxInt(mContext, 28),
                    0);
            gridLayoutHelper.setAutoExpand(false);
            adapters.add(new CityListAdapter(gridLayoutHelper, hotCityList.size(), hotCityList, 1));
        }

        if (CITY_LAYOUT) {
            int k = 0;
            for (int i = 0; i < list.size(); i++) {
                if ("平台".equals(list.get(i).getName())) {
                    Log.i("yuanye", "平台-----" + i);
                }
                if (i > 0 && list.get(i).getSortLetters().equals(list.get(i - 1).getSortLetters())) {
                    //城市列表
                    LinearLayoutHelper content = new LinearLayoutHelper();
                    ArrayList<CityBean> contentList = new ArrayList<>();
                    CityBean bean = list.get(i);
                    bean.setName(bean.getName());
                    contentList.add(bean);
                    adapters.add(new CityListAdapter(content, 1, contentList, 2));
                } else {
                    StickyLayoutHelper title = new StickyLayoutHelper();
                    ArrayList<CityBean> titleList = new ArrayList<>();
                    titleList.add(new CityBean(list.get(i).getSortLetters()));
                    adapters.add(new CityListAdapter(title, 1, titleList, 0));
                    titlePos.add(new Title(list.get(i).getSortLetters(), 3 + hotCityList.size() + k + i));
                    k = k + 1;
                    LinearLayoutHelper content = new LinearLayoutHelper();
                    ArrayList<CityBean> contentList = new ArrayList<>();
                    CityBean bean = list.get(i);
                    bean.setName(bean.getName());
                    contentList.add(bean);
                    adapters.add(new CityListAdapter(content, 1, contentList, 2));
                }
            }
        }
        return adapters;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 纪录title真实的位置，优化跳转
     */
    class Title {
        private String letter;
        private int position;

        public Title(String letter, int position) {
            this.letter = letter;
            this.position = position;//真实跳转的位置
        }

        public String getLetter() {
            return letter;
        }

        public void setLetter(String letter) {
            this.letter = letter;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

}
