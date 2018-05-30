package com.xingguang.master.maincode.classifly.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.xingguang.master.R;
import com.xingguang.master.view.CommonViewHolder;

/**
 * 基本功能：左侧Adapter
 * 创建：王杰
 * 创建时间：16/4/14
 * 邮箱：w489657152@gmail.com
 */
public class LeftListAdapter extends RecyclerView.Adapter<CommonViewHolder> {
    private String[] leftStr;
    boolean[] flagArray;
    private Context context;

    public LeftListAdapter(Context context, String[] leftStr,boolean[] flagArray) {
        this.leftStr = leftStr;
        this.flagArray = flagArray;
        this.context = context;
    }

    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder commonViewHolder = null;
        commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_lvleft);
        return commonViewHolder;
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {
        final TextView left_list_item = holder.getItemView().findViewById(R.id.left_list_item);
        if (mOnItemClickListener != null) {
            holder.getItemView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(holder.getItemView(),left_list_item, position);
                }
            });
        }

        holder.setText(R.id.left_list_item,leftStr[position]);
        if (flagArray[position]) {
            left_list_item.setBackgroundColor(Color.rgb(255, 255, 255));
            left_list_item.setTextColor(ContextCompat.getColor(context,R.color.textBlack));
        } else {
            left_list_item.setBackgroundColor(Color.parseColor("#1DC9FB")); //蓝底
            left_list_item.setTextColor(ContextCompat.getColor(context,R.color.white));
        }


    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view,TextView left_list_item, int position);
    }

    @Override
    public int getItemCount() {
        return leftStr.length;
    }



}
