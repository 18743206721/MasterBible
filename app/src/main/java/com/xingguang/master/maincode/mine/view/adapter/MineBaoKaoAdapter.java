package com.xingguang.master.maincode.mine.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.view.CommonViewHolder;

import java.util.List;

/**
 * 创建日期：2018/5/30
 * 描述:报考记录适配器
 * 作者:LiuYu
 */
public class MineBaoKaoAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private Context mContext;
    private List<String> list;
    int type;//1是报考记录，2是培训记录

    public MineBaoKaoAdapter(Context mContext, List<String> list, int type) {
        this.mContext = mContext;
        this.list = list;
        this.type = type;
    }
    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder commonViewHolder = null;
        commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_mine_baokao);
        return commonViewHolder;
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {
        LinearLayout item_ll = holder.getItemView().findViewById(R.id.item_ll);
        LinearLayout item_llbaokao = holder.getItemView().findViewById(R.id.item_llbaokao);
        View view1 = holder.getItemView().findViewById(R.id.view1);
        TextView tv1 =holder.getItemView().findViewById(R.id.tv1);
        TextView tv2 = holder.getItemView().findViewById(R.id.tv2);
        TextView tv3 = holder.getItemView().findViewById(R.id.tv3);

        if (position == 0){
            item_ll.setVisibility(View.VISIBLE);
            item_llbaokao.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            if (type == 1){
                tv1.setText("报考部门");
                tv2.setText("报考职务");
                tv3.setText("报考时间");
            }else if (type == 2){
                tv1.setText("培训工种");
                tv2.setText("选择部门");
                tv3.setText("培训时间");
            }
        }else {
            item_ll.setVisibility(View.GONE);
            item_llbaokao.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
        }

        if (mOnItemClickListener != null) {
            holder.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(holder.getItemView(), position);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }


    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }



}
