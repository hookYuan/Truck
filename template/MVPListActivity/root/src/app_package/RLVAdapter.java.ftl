
package  ${applicationPackage}.view.${modular}.adapter;

import com.xczn.visitorapp.R;
import com.yuan.basemodule.common.adapter.RLVAdapter;

import android.view.View;
import android.content.Context;
import android.view.ViewGroup;

import java.util.List;
/**
 * create by Yuan ye.
 */
public class ${activityName}Adapter extends RLVAdapter {
  
    private List<?> mData;
  
    public ${activityName}Adapter(Context context, List<?> list) {
        super(context);
		this.mData = list;
    }

	/**
	 * You can select other method
	 */						
	@Override
    public int getItemLayout(ViewGroup parent, int viewType) {
		return R.layout.${activityLayoutName}_item;
	}
	
	 @Override
    public void onBindHolder(ViewHolder holder, final int position) {
		
	}
	
	@Override
    public void onItemClick(ViewHolder holder, View view, int position) {

    }
	
	@Override
    public int getItemCount() {
		//TODO set list count. 
        return mData == null ? 0 : mData.size();
    }
}
