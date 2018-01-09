
package  ${relativePackage};


import android.os.Bundle;

import ${applicationPackage}.R;
import ${applicationPackage}.view.${modular}.presenter.${activityName}Presenter;
import com.yuan.basemodule.ui.base.mvp.MVPActivity;

/**
 * create by Yuan ye.
 * download truck before use this
 */
public class ${activityClass} extends MVPActivity<${activityName}Presenter>{

   @Override
    protected void initData(Bundle savedInstanceState) {
	
	}

    @Override
    public int getLayoutId() {
        return R.layout.${activityLayoutName};
    }
}
