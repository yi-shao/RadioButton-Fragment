package com.example.frame_demo.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;

import com.example.frame_demo.R;
import com.example.frame_demo.base.BaseFragment;
import com.example.frame_demo.fragment.CommonFrameFragment;
import com.example.frame_demo.fragment.CustomFragment;
import com.example.frame_demo.fragment.OtherFragment;
import com.example.frame_demo.fragment.ThridPartyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HASEE on 2016/11/21.
 * 主界面
 */
public class MainActivity extends FragmentActivity {

    private List<BaseFragment> mBaseFragment;
    private RadioGroup mRg_main;
    private int position;//选中Fragment对应的位置
    /**
     * 上次切换的Fragment
     */
    private Fragment mContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化view
        initView();
        //初始化Fragment
        initFragment();
        //设置RadioGroup监听
        setlistener();
    }

    private void setlistener() {
        mRg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
        mRg_main.check(R.id.rb_frame);
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rb_frame:
                    position=0;
                    break;
                case R.id.rb_thirdparty:
                    position=1;
                    break;
                case R.id.rb_custom:
                    position=2;
                    break;
                case R.id.rb_other:
                    position=3;
                    break;
                default:
                    break;
            }
            //根据位置得到对应的Fragment
            BaseFragment to = getFragment();
            //替换
            switchFragment(mContent,to);
        }
    }

    /**
     * 碎片切换的优化
     * @param from 刚显示的Fragment,马上就要被隐藏了
     * @param to 马上要切换到的Fragment，一会要显示
     */
    private void switchFragment(Fragment from, Fragment to){
        if (from!=to){
            mContent=to;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            //才切换
            //判断有没有被添加
            if(!to.isAdded()){
                //to 没有被添加
                //from隐藏
                if (from!=null){
                    ft.hide(from);
                }
                //添加to
                if (to!=null){
                    ft.add(R.id.fl_content,to).commit();
                }
            }else {
                //to 已经被添加
                //from隐藏
                if (from!=null){
                    ft.hide(from);
                }
                //显示to
                if (to!=null){
                    ft.show(to).commit();
                }
            }
        }
    }

    /*private void switchFragment(BaseFragment fragment) {
        //1.得到FragmentManger
        FragmentManager fm = getFragmentManager();
        //2.开启事务
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        //3.替换
        transaction.replace(R.id.fl_content, fragment);
        //4.提交事务
        transaction.commit();
    }*/

    /**
     * 根据位置得到对应的Fragment
     * @return
     */
    private BaseFragment getFragment() {
        BaseFragment fragment = mBaseFragment.get(position);
        return fragment;
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        mBaseFragment.add(new CommonFrameFragment());
        mBaseFragment.add(new ThridPartyFragment());
        mBaseFragment.add(new CustomFragment());
        mBaseFragment.add(new OtherFragment());
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        mRg_main = (RadioGroup) findViewById(R.id.rg_main);
    }
}
