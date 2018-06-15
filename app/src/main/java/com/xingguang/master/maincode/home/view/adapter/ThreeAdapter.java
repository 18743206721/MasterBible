package com.xingguang.master.maincode.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
public class ThreeAdapter extends RecyclerView.Adapter<CommonViewHolder> {


    private Context mContext;
    private List<HomeBean.DataBean.CultivateBean> list;

    public ThreeAdapter(Context mContext, List<HomeBean.DataBean.CultivateBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    //更多
    private OnItemMoreClickListener mOnItemMoreClickListener = null;

    public void setmOnItemMoreClickListener(OnItemMoreClickListener mOnItemMoreClickListener) {
        this.mOnItemMoreClickListener = mOnItemMoreClickListener;
    }
    //列表点击
    private OnItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder commonViewHolder = null;
        switch (viewType) {
            case 1:
                commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_homeone_header);
                break;
            case 2:
                commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_home_welder);
                break;
        }
        return commonViewHolder;
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {

        int type = getItemViewType(position);
        switch (type){
            case 1:
                final LinearLayout ll_hworks = holder.getItemView().findViewById(R.id.ll_hworks);
                holder.getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemMoreClickListener.onItemMoreClick(ll_hworks, position);
                    }
                });

                TextView itemtv_info = holder.getItemView().findViewById(R.id.itemtv_info);
                AppUtil.addForeSizeSpan(itemtv_info,"焊工培训",mContext);

                break;
            case 2:
                TextView itemtv_three = holder.getItemView().findViewById(R.id.itemtv_three);
                if (position == 1){
                    itemtv_three.setVisibility(View.VISIBLE);
                }else {
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

                holder.setText(R.id.item_tv_weldertitle1,list.get(position).getTitle());
                holder.setText(R.id.item_tv_welder_content1,list.get(position).getContent());

                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (list != null && position ==0) {
            return 1;
        } else {
            return 2;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //define interface
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //更多
    //define interface
    public interface OnItemMoreClickListener {
        void onItemMoreClick(LinearLayout onemore, int position);
    }

}
