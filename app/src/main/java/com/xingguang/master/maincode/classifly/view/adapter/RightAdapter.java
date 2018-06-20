package com.xingguang.master.maincode.classifly.view.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xingguang.master.R;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.util.RoundRectImageView;
import com.xingguang.master.view.CommonViewHolder;
import com.xingguang.master.view.ImageLoader;

import java.util.List;

/**
 * 基本功能：右侧Adapter
 * 创建：王杰
 * 创建时间：16/4/14
 * 邮箱：w489657152@gmail.com
 */
public class RightAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private Context mContext;
    private List<BuMengBean.DataBeanX.DataBean> list;

    public RightAdapter(Context context, List<BuMengBean.DataBeanX.DataBean> list) {
        this.mContext = context;
        this.list = list;
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

        RoundRectImageView iv = holder.getItemView().findViewById(R.id.iv_typeofwork);
        ImageLoader.loadRoundImage(mContext, HttpManager.BASE_URL+
                list.get(position).getClassPic(),iv,5);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<BuMengBean.DataBeanX.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
