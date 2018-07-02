package com.xingguang.master.refresh;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

/**
 * @auathor LiuYu
 * @date 2018/06/08
 */

public class RefreshUtil {

    private OnRefreshListener onRefreshListener;

    public RefreshUtil(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    public RefreshListenerAdapter refreshListenerAdapter() {
        RefreshListenerAdapter refreshListenerAdapter = new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                onRefreshListener.onRefresh();
            }
            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                onRefreshListener.onLoad();
            }
        };
        return refreshListenerAdapter;
    }

    public interface OnRefreshListener {
        void onRefresh();

        void onLoad();
    }
}
