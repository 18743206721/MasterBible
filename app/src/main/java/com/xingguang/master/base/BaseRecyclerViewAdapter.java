package com.xingguang.master.base;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.xingguang.master.view.CommonViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wh
 * @date 17/10/23
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    /**
     * 带有header的
     */
    private final int TYPE_HEADER = 0;
    /**
     * 带有footer的
     */
    public static final int TYPE_FOOTER = 1;
    /**
     * 没有header和footer的
     */
    public static final int TYPE_NORMAL = 2;

    public View headerView, footerView;

    public List<T> mDatas;
    public Context context;


    /**
     * header的holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract CommonViewHolder createHeaderHolder(ViewGroup parent, int viewType);

    /**
     * footer的holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract CommonViewHolder createFooterHolder(ViewGroup parent, int viewType);
    /**
     * 非header和footer的holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract CommonViewHolder createNormalHolder(ViewGroup parent, int viewType);

    /**
     * 绑定数据
     *
     * @param holder
     * @param position
     * @return
     */
    public abstract void onBindView(CommonViewHolder holder, int position);

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FOOTER:
                return createFooterHolder(parent, viewType);
            case TYPE_HEADER:
                return createHeaderHolder(parent, viewType);
            default:
                return createNormalHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        if (headerView != null && getItemViewType(position) == TYPE_NORMAL) {
            onBindView(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView == null && footerView == null) {
            return TYPE_NORMAL;
        }
        //如果有header，则第一个加载header
        if (headerView != null && position == 0) {
            return TYPE_HEADER;
        }
        //如果有footer，则最后一个加载footer
        if (footerView != null && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public int getItemCount() {
        if (headerView == null && footerView == null) {
            return mDatas.size();
        } else if (headerView != null && footerView != null) {
            return mDatas.size() + 2;
        } else {
            return mDatas.size() + 1;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) == TYPE_NORMAL
                            ? 1 : gridManager.getSpanCount();
                }
            });
        }
    }

    @Override
    public void onViewAttachedToWindow(CommonViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.getItemView().getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            boolean isHeader = headerView != null && holder.getLayoutPosition() == 0;
            boolean isFooter = footerView != null && holder.getLayoutPosition() == getItemCount() - 1;
            if (isHeader || isFooter) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    public void addHeaderView(View headerView) {
        this.headerView = headerView;
        notifyItemInserted(0);
    }

    public void addFooterView(View footerView) {
        this.footerView = footerView;
        notifyItemInserted(getItemCount() - 1);
    }
}
