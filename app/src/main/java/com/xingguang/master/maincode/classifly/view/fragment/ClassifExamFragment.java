package com.xingguang.master.maincode.classifly.view.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.util.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/30
 * 描述:分类下的考试宝典
 * 作者:LiuYu
 */
public class ClassifExamFragment extends ToolBarFragment {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_baodian)
    ImageView ivBaodian;
    @BindView(R.id.iv_kaoshi)
    ImageView ivKaoshi;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classifexam;
    }

    @Override
    protected void initView() {
        setToolBarTitle("考试宝典");
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.setBg(2);
                MainActivity.instance.setToProjectFragment();
            }
        });

    }


    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.iv_baodian, R.id.iv_kaoshi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_baodian:
                break;
            case R.id.iv_kaoshi:
                break;
        }
    }


}
