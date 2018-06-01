package com.xingguang.master.login.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 创建日期：2018/6/1
 * 描述:找回密码第三步
 * 作者:LiuYu
 */
public class ForgetThreeActivity extends ToolBarActivity {

    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_three;
    }

    @Override
    protected void initView() {
        setToolBarTitle("找回密码");
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        finish();
    }


}
