package com.xingguang.master.maincode.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.maincode.home.model.SearchBean;
import com.xingguang.master.view.CommonViewHolder;

import java.util.List;

/**
 * 创建日期：2018/5/30
 * 描述:搜索适配器
 * 作者:LiuYu
 */
public class SearchAdapter extends RecyclerView.Adapter<CommonViewHolder> {


    private Context mContext;
    private List<SearchBean.DataBean> list;

    public SearchAdapter(Context mContext, List<SearchBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    //列表点击
    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder commonViewHolder = null;
        commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_search);
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

        holder.setText(R.id.item_tv_right,list.get(position).getTypename());
        holder.setText(R.id.item_tv_title,list.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<SearchBean.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
