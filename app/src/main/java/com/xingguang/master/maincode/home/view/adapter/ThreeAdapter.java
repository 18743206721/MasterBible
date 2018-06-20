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
 * 描述:招工信息适配器
 * 作者:LiuYu
 */
public class ThreeAdapter extends RecyclerView.Adapter<ThreeAdapter.ViewHolder> {

    public static final int TYPE_HEAD = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;
    private Context mContext;
    private List<HomeBean.DataBean.CultivateBean> list;

    public ThreeAdapter(Context mContext, List<HomeBean.DataBean.CultivateBean> list) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_home_welder, parent, false);
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
            holder.item_tv_weldertitle1.setText(list.get(position - 1).getTitle());
            holder.item_tv_welder_content1.setText(list.get(position - 1).getContent());
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
        TextView item_tv_weldertitle1, item_tv_welder_content1, itemtv_three;//名字

        public ViewHolder(View itemView) {
            super(itemView);
            item_tv_weldertitle1 = (TextView) itemView.findViewById(R.id.item_tv_weldertitle1);
            item_tv_welder_content1 = (TextView) itemView.findViewById(R.id.item_tv_welder_content1);
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
