package com.xingguang.master.maincode.mine.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xingguang.master.R;
import com.xingguang.master.maincode.mine.model.BaoKaoGuanLiBean;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.view.CommonViewHolder;

import java.util.List;

/**
 * 创建日期：2018/5/30
 * 描述:报考记录适配器
 * 作者:LiuYu
 */
public class MineBaoKaoAdapter extends RecyclerView.Adapter<MineBaoKaoAdapter.ViewHolder> {

    public static final int TYPE_HEAD=0;
    public static final int TYPE_NORMAL=1;
    private View mHeaderView;
    private Context mContext;
    private List<BaoKaoGuanLiBean.DataBean.ListBean> list;
    int classif;//1是报考记录，2是培训记录

    public MineBaoKaoAdapter(Context mContext, List<BaoKaoGuanLiBean.DataBean.ListBean> list, int classif) {
        this.mContext = mContext;
        this.list = list;
        this.classif = classif;
    }

    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void addHeaderView(View headerView){
        mHeaderView = headerView;
        notifyItemInserted(0);  //在itemview中设置第一项
    }
    /*
     * 判断head的位置，是否显示item的一整行
     * **/
    public boolean isHeader(int position) {
        return position == 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEAD && mHeaderView != null){
            return new ViewHolder(mHeaderView);
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.item_mine_baokao,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEAD){
            return;
        }else {
            if (mHeaderView!=null){
                //如果设置了回调，则设置点击事件
                if (mOnItemClickListener != null){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onItemClick(holder.itemView,position);
                        }
                    });
                }
            }
            holder.item_tvname1.setText(list.get(position-1).getDepartmentName());//种类
            holder.item_tvname2.setText(list.get(position-1).getProfessionName());//工种
            holder.item_tvname3.setText(list.get(position-1).getFormatAddDate());//时间
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView==null)
            return TYPE_NORMAL;
        if (position==0){
            return TYPE_HEAD;
        }
        return TYPE_NORMAL;
    }


    @Override
    public int getItemCount() {
        return mHeaderView!=null ? list.size()+1 : list.size();
    }

    public void setList(List<BaoKaoGuanLiBean.DataBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_tvname1,item_tvname2,item_tvname3;//名字
        View view1;
        public ViewHolder(View itemView) {
            super(itemView);
            item_tvname1 = (TextView) itemView.findViewById(R.id.item_tvname1);
            item_tvname2 = (TextView) itemView.findViewById(R.id.item_tvname2);
            item_tvname3 = (TextView) itemView.findViewById(R.id.item_tvname3);
            view1 = itemView.findViewById(R.id.view1);
        }
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
