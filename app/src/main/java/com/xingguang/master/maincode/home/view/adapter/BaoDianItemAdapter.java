package com.xingguang.master.maincode.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xingguang.master.R;
import com.xingguang.master.view.CommonViewHolder;

import java.util.List;

/**
 * 创建日期：2018/5/26
 * 描述:
 * 作者:LiuYu
 */
public class BaoDianItemAdapter extends RecyclerView.Adapter<CommonViewHolder>  {

    private Context mContext;
    private List<String> list;
    private int type;

    public BaoDianItemAdapter(Context mContext, List<String> list, int type) {
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
            commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_baodian);
        return commonViewHolder;
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {

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
        return 9;
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }



}
