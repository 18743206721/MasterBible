package com.xingguang.master.maincode.home.view.fragment;

import android.view.View;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.main.view.activity.MainActivity;

/**
 * 创建日期：2018/5/29
 * 描述:培训项目
 * 作者:LiuYu
 */
public class ProgramsFragment extends ToolBarFragment {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_programs;
    }

    @Override
    protected void initView() {
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.setBg(1);
                MainActivity.instance.setToNewsFragment();
            }
        });
        setToolBarTitle("培训项目");

        init();
    }


    private void init() {

    }

    @Override
    protected void lazyLoad() {

    }


}
