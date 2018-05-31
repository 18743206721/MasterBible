package com.xingguang.master.maincode.home.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.view.CommonViewHolder;

import java.util.List;

/**
 * 创建日期：2018/5/30
 * 描述:招工信息适配器
 * 作者:LiuYu
 */
public class OneZhizeAdapter extends RecyclerView.Adapter<CommonViewHolder> {


    private Context mContext;
    private List<String> list;
    private String type;

    public OneZhizeAdapter(Context mContext, List<String> list,String type) {
        this.mContext = mContext;
        this.list = list;
        this.type = type;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommonViewHolder commonViewHolder = null;
        commonViewHolder = CommonViewHolder.getViewHolder(parent, R.layout.item_works);
        return commonViewHolder;
    }

    @Override
    public void onBindViewHolder(final CommonViewHolder holder, final int position) {

        if (type.equals("1")){
//            holder.setText(R.id.item_tv,list.get(position));

        }else if ("2".equals(type)){

        }


    }

    @Override
    public int getItemCount() {
        return 5;
    }

}
