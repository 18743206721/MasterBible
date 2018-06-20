package com.xingguang.master.maincode.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.maincode.home.model.HomeBean;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.view.CommonViewHolder;

import java.util.List;

/**
 * 创建日期：2018/5/30
 * 描述:行业资讯适配器
 * 作者:LiuYu
 */
public class TwoAdapter extends RecyclerView.Adapter<TwoAdapter.ViewHolder> {

    public static final int TYPE_HEAD = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private Context mContext;
    private List<HomeBean.DataBean.InformationBean> list;

    public TwoAdapter(Context mContext, List<HomeBean.DataBean.InformationBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    //列表点击
    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void addHeaderView(View headerView) {
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
        if (viewType == TYPE_HEAD && mHeaderView != null) {
            return new ViewHolder(mHeaderView);
        }
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_information, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEAD) {
            return;
        } else {
            if (mHeaderView != null) {
                //如果设置了回调，则设置点击事件
                if (mOnItemClickListener != null) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onItemClick(holder.itemView, position);
                        }
                    });
                }
            }
            if (position == 1) {
                holder.itemtv_three.setVisibility(View.VISIBLE);
            } else {
                holder.itemtv_three.setVisibility(View.GONE);
            }
            holder.item_tv_infotitle.setText(list.get(position - 1).getTitle());
            holder.item_tv_info_content.setText(list.get(position - 1).getContent());
            holder.item_tv_infortime.setText(list.get(position - 1).getAddDate().substring(0,10));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null)
            return TYPE_NORMAL;
        if (position == 0) {
            return TYPE_HEAD;
        }
        return TYPE_NORMAL;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item_tv_infotitle, item_tv_info_content, item_tv_infortime, itemtv_three;//名字

        public ViewHolder(View itemView) {
            super(itemView);
            item_tv_infotitle = (TextView) itemView.findViewById(R.id.item_tv_infotitle);
            item_tv_info_content = (TextView) itemView.findViewById(R.id.item_tv_info_content);
            item_tv_infortime = (TextView) itemView.findViewById(R.id.item_tv_infortime);
            itemtv_three = (TextView) itemView.findViewById(R.id.itemtv_three);
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderView != null ? list.size() + 1 : list.size();
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }


}
