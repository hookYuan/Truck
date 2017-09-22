package com.yuan.demo.activity.fragmentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.yuan.basemodule.ui.base.activity.FragmentActivity;
import com.yuan.demo.myapplication.R;

public class MainActivity extends FragmentActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        addFragment(R.id.content,TestFragment.class, TestFragment2.class);
        Button btn01 = (Button) findViewById(R.id.btn_01);
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(0);
            }
        });
        Button btn02 = (Button) findViewById(R.id.btn_02);
        btn02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragment(1);
            }
        });
    }
}
