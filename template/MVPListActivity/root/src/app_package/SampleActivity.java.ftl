
package  ${relativePackage};


import android.os.Bundle;

import ${applicationPackage}.R;
import ${applicationPackage}.view.${modular}.presenter.${activityName}Presenter;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;
import com.yuan.basemodule.common.other.Views;
import com.yuan.basemodule.common.kit.Kits;
import com.yuan.basemodule.common.adapter.GridDivider;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ${applicationPackage}.view.${modular}.adapter.${activityName}Adapter;

/**
 * create by Yuan ye.
 * download truck before use this
 */
public class ${activityClass} extends MVPActivity<${activityName}Presenter>{

	private RecyclerView rlvRecycler;

   @Override
    protected void initData(Bundle savedInstanceState) {
		//init title bar.
		getTitleBar().setLeftIcon(R.drawable.ic_base_back_white)
                .setToolbar("List Activity");
		
		
		rlvRecycler = Views.find(mContext,R.id.rlv_recycler);
		rlvRecycler.setLayoutManager(new LinearLayoutManager(mContext));
		//add divider.
		rlvRecycler.addItemDecoration(new GridDivider((int) Kits.Dimens.dpToPx(mContext, 0.8f)
                , ContextCompat.getColor(mContext, R.color.colorDivider)));
		rlvRecycler.setAdapter(new ${activityName}Adapter(mContext,null));
	}

    @Override
    public int getLayoutId() {
        return R.layout.${activityLayoutName};
    }
}
