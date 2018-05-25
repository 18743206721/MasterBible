package com.xingguang.master.maincode.mine.view.fragment;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;

/**
 * 创建日期：2018/5/25
 * 描述:
 * 作者:LiuYu
 */
public class MineFragment extends ToolBarFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        setToolBarTitle("我的");
    }

    @Override
    protected void lazyLoad() {

    }
}
