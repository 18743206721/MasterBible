package com.xingguang.master.maincode.classifly.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xingguang.master.R;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.util.RoundRectImageView;
import com.xingguang.master.view.CommonViewHolder;
import com.xingguang.master.view.ImageLoader;

import java.util.List;

/**
 * 创建日期：2018/7/21
 * 描述:
 * 作者:LiuYu
 */
public class ItemAdapter extends RecyclerView.Adapter<CommonViewHolder>  {
    private Context mContext;
    private List<BuMengBean.DataBeanX.DataBean> list;
    private int type;

    public ItemAdapter(Context mContext, List<BuMengBean.DataBeanX.DataBean> list, int type) {
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
        commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_classif);
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
            holder.setText(R.id.tv_select,list.get(position).getName());

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
