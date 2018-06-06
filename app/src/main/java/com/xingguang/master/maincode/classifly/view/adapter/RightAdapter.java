package com.xingguang.master.maincode.classifly.view.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xingguang.master.R;
import com.xingguang.master.view.CommonViewHolder;

/**
 * 基本功能：右侧Adapter
 * 创建：王杰
 * 创建时间：16/4/14
 * 邮箱：w489657152@gmail.com
 */
public class RightAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private Context mContext;
    private String[] rightStr;

    public RightAdapter(Context context, String[] rightStr) {
        this.mContext = context;
        this.rightStr = rightStr;
    }
    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder commonViewHolder = null;
        commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_right);
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
        return rightStr.length;
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
