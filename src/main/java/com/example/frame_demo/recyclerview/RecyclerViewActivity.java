package com.example.frame_demo.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frame_demo.R;

import java.util.ArrayList;

/**
 * 作者：忆 on 2016/12/12 19:36
 * 功能：RecyclerViewActivity
 */

public class RecyclerViewActivity extends Activity implements View.OnClickListener {

    private Button btn_add;
    private Button btn_del;
    private Button btn_grid;
    private Button btn_list;
    private Button btn_flow;
    private RecyclerView mRecyclerView;
    private TextView tv_title;

    private ArrayList<String> datas;

    private RecyclerViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        //设置recyclerview的适配器
        adapter = new RecyclerViewAdapter(RecyclerViewActivity.this,datas);
        mRecyclerView.setAdapter(adapter);
        //LayoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this,LinearLayoutManager.VERTICAL,false));
        //添加RecyclerView的分割线
        mRecyclerView.addItemDecoration(new DividerListItemDecoration(RecyclerViewActivity.this,DividerListItemDecoration.VERTICAL_LIST));
        //设置点击某条的监听
        adapter.setOnItemOncCickListener(new RecyclerViewAdapter.OnItemOncCickListener() {
            @Override
            public void onItemOnClick(View view, String data) {
                Toast.makeText(RecyclerViewActivity.this, "data=="+data, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData() {
        datas = new ArrayList<>();
        //准备集合数据
        for (int i = 0; i <100 ; i++) {
            datas.add("content"+i);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_recyclerview);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_del = (Button) findViewById(R.id.btn_del);
        btn_grid = (Button) findViewById(R.id.btn_grid);
        btn_list = (Button) findViewById(R.id.btn_list);
        btn_flow = (Button) findViewById(R.id.btn_flow);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycleview);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("RecyclerView");

        //设置点击事件
        btn_add.setOnClickListener(this);
        btn_del.setOnClickListener(this);
        btn_grid.setOnClickListener(this);
        btn_list.setOnClickListener(this);
        btn_flow.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_add:
                adapter.addData(0,"New_Content");
                mRecyclerView.scrollToPosition(0);
                break;
            case R.id.btn_del:
                adapter.delData(0);
                break;
            case R.id.btn_list:
                //设置list类型效果
                mRecyclerView.setLayoutManager(new LinearLayoutManager(RecyclerViewActivity.this,
                        LinearLayoutManager.VERTICAL,false));
                break;
            case R.id.btn_grid:
                //设置grid类型效果
                mRecyclerView.setLayoutManager(new GridLayoutManager(RecyclerViewActivity.this,3,
                        LinearLayoutManager.VERTICAL,false));
                break;
            case R.id.btn_flow:
                //设置瀑布流类型效果
                mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4,LinearLayoutManager.VERTICAL));
                break;
        }
    }
}
