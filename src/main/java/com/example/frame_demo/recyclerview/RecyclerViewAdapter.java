package com.example.frame_demo.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.frame_demo.R;

import java.util.ArrayList;

/**
 * 作者：忆 on 2016/12/12 20:59
 * 功能：RecyclerView的适配器
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHold>{
    private final Context context;
    private  ArrayList<String> datas;

    public RecyclerViewAdapter(Context context, ArrayList<String> datas) {
        this.context=context;
        this.datas = datas;
    }

    @Override
    public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_recycerview,null);
        return new MyViewHold(itemView);
    }

    /**
     * 绑定数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHold holder, int position) {
        //根据位置得到对应数据
        String data = datas.get(position);
        holder.tv_name.setText(data);
    }

    /**
     * 得到数目总条数
     * @return
     */
    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * 添加数据
     * @param position
     * @param data
     */
    public void addData(int position, String data) {
        datas.add(position,data);
        //刷新适配器
        notifyItemInserted(position);
    }

    public void delData(int position) {
        datas.remove(position);
        //刷新适配器
        notifyItemRemoved(position);
    }

    class MyViewHold extends RecyclerView.ViewHolder{
        private ImageView iv_icon;
        private TextView tv_name;
        public MyViewHold(View itemView) {
            super(itemView);
            iv_icon= (ImageView) itemView.findViewById(R.id.iv_icon);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "图片=="+getLayoutPosition(), Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "data=="+datas.get(getLayoutPosition()), Toast.LENGTH_SHORT).show();
                    if (onItemOncCickListener!=null){
                        onItemOncCickListener.onItemOnClick(v,datas.get(getLayoutPosition()));
                    }
                }
            });
        }
    }

    /**
     * 点击RecyclerView某条的监听
     */
    public interface OnItemOncCickListener{
        /**
         * 当RecyclerView某个被点击的时候回调
         * @param view 点击item的视图
         * @param data  点击得到的数据
         */
         void onItemOnClick(View view,String data);
    }

    private OnItemOncCickListener onItemOncCickListener;

    /**
     * 设置RecyclerView的某个监听
     * @param onItemOncCickListener
     */
    public void setOnItemOncCickListener(OnItemOncCickListener onItemOncCickListener) {
        this.onItemOncCickListener = onItemOncCickListener;
    }
}
