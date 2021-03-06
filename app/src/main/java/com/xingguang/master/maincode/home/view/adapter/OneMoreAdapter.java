package com.xingguang.master.maincode.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.maincode.home.model.OneBean;
import com.xingguang.master.view.CommonViewHolder;

import java.util.List;

/**
 * 创建日期：2018/5/30
 * 描述:招工信息适配器
 * 作者:LiuYu
 */
public class OneMoreAdapter extends RecyclerView.Adapter<CommonViewHolder> {


    private Context mContext;
    private List<OneBean.DataBean.ListBean> list;

    public OneMoreAdapter(Context mContext, List<OneBean.DataBean.ListBean> list) {
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
        commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_home_works);
        return commonViewHolder;
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {
        TextView itemtv_three = holder.getItemView().findViewById(R.id.itemtv_three);
        if (position == 0) {
            itemtv_three.setVisibility(View.VISIBLE);
        } else {
            itemtv_three.setVisibility(View.GONE);
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

        holder.setText(R.id.item_tv_city_title,list.get(position).getJobName());
        holder.setText(R.id.tv_home_ads,list.get(position).getDiDian());
        if (list.get(position).getDaiYu().equals("面议")){
            holder.setText(R.id.tv_home_payment,list.get(position).getDaiYu());
        }else{
            holder.setText(R.id.tv_home_payment,list.get(position).getDaiYu()+"元/月");
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<OneBean.DataBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
