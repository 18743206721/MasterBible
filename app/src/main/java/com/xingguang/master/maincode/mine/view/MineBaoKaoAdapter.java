package com.xingguang.master.maincode.mine.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

    public MineBaoKaoAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
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
        if (position == 0){
            item_ll.setVisibility(View.VISIBLE);
            item_llbaokao.setVisibility(View.GONE);
        }else {
            item_ll.setVisibility(View.GONE);
            item_llbaokao.setVisibility(View.VISIBLE);
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
