package com.xingguang.master.maincode.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.xingguang.master.R;
import com.xingguang.master.maincode.home.model.ProgramsBean;
import com.xingguang.master.view.CommonViewHolder;

import java.util.List;

/**
 * 创建日期：2018/5/30
 * 描述:项目列表适配器
 * 作者:LiuYu
 */
public class ProjectAdapter extends RecyclerView.Adapter<CommonViewHolder> {

    private Context mContext;
    private List<ProgramsBean.DataBeanX.DataBean> list;

    public ProjectAdapter(Context mContext, List<ProgramsBean.DataBeanX.DataBean> list) {
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
        commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_project);
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
        holder.setText(R.id.item_tvname,list.get(position).getTitle());
        holder.setText(R.id.item_tvcontent,list.get(position).getContent());
        holder.setText(R.id.item_tvtime,list.get(position).getFormatAddDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<ProgramsBean.DataBeanX.DataBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


}
