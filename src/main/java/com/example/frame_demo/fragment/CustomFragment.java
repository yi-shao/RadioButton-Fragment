package com.example.frame_demo.fragment;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.frame_demo.base.BaseFragment;

/**
 * Created by HASEE on 2016/11/22.
 * 自定义
 */
public class CustomFragment extends BaseFragment {
    private TextView textView;
    @Override
    protected View initView() {
        textView = new TextView(mContext);
        textView.setTextSize(20 );
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    protected void initData() {
        super.initData();
        textView.setText("自定义");
    }
}
