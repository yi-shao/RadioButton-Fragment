package com.example.frame_demo.fragment;


import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.frame_demo.R;
import com.example.frame_demo.activity.OKHttpActivity;
import com.example.frame_demo.adapter.CommonFrameFragmentAdapter;
import com.example.frame_demo.base.BaseFragment;
import com.example.frame_demo.recyclerview.RecyclerViewActivity;

/**
 * Created by HASEE on 2016/11/22.
 * 常用框架
 */
public class CommonFrameFragment extends BaseFragment {
    private ListView mListView;
    private String[] datas;
    private CommonFrameFragmentAdapter adapter;
    @Override
    protected View initView() {
        View view = View.inflate(mContext, R.layout.fragment_common_frame, null);
        mListView = (ListView) view.findViewById(R.id.listview);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String data = datas[position];//okhttp
                if (data.toLowerCase().equals("okhttp")){
                    Intent intent = new Intent(mContext,OKHttpActivity.class);
                    mContext.startActivity(intent);
                }else if (data.toLowerCase().equals("recyclerview")){
                    Intent intent = new Intent(mContext,RecyclerViewActivity.class);
                    mContext.startActivity(intent);
                }
                Toast.makeText(mContext, "data==" + data, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    @Override
    protected void initData() {
        super.initData();
        //准备数据
        datas = new String[]{"OKHttp","RecyclerView","xUtils3", "Retrofit2", "Fresco", "Glide", "greenDao", "RxJava",
                "volley", "Gson", "FastJson", "picasso", "evenBus", "jcvideoplayer", "pulltorefresh",
                "Expandablelistview", "UniversalVideoView", "....."};
        //设置适配器
        adapter = new CommonFrameFragmentAdapter(mContext, datas);
        mListView.setAdapter(adapter);
    }
}
