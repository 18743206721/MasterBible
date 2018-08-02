package com.xingguang.master.maincode.classifly.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xingguang.master.R;
import com.xingguang.master.maincode.classifly.model.KaoshiJIgouBean;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.view.CommonViewHolder;

import java.util.List;

/**
 * 创建日期：2018/7/21
 * 描述:考试机构
 * 作者:LiuYu
 */
public class KaoShiAdapter extends RecyclerView.Adapter<CommonViewHolder>  {
    private Context mContext;
    private List<KaoshiJIgouBean.DataBean.ListBean> list;
    private int type;

    public KaoShiAdapter(Context mContext, List<KaoshiJIgouBean.DataBean.ListBean> list, int type) {
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
        commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_kaoshi);
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
            holder.setText(R.id.tv_selected,list.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<KaoshiJIgouBean.DataBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


}
