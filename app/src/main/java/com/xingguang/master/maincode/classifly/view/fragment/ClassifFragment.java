package com.xingguang.master.maincode.classifly.view.fragment;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;

/**
 * 创建日期：2018/5/25
 * 描述:
 * 作者:LiuYu
 */
public class ClassifFragment extends ToolBarFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classif;
    }

    @Override
    protected void initView() {
        setToolBarTitle("分类");
    }

    @Override
    protected void lazyLoad() {

    }


}
