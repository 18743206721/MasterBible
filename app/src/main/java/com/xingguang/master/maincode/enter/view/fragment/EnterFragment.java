package com.xingguang.master.maincode.enter.view.fragment;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;

/**
 * 创建日期：2018/5/25
 * 描述:报考
 * 作者:LiuYu
 */
public class EnterFragment extends ToolBarFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_enter;
    }

    @Override
    protected void initView() {
        setToolBarTitle("报考");
    }

    @Override
    protected void lazyLoad() {

    }
}
