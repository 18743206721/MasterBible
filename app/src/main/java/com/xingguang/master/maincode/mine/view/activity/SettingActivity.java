package com.xingguang.master.maincode.mine.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 创建日期：2018/5/31
 * 描述:设置
 * 作者:LiuYu
 */
public class SettingActivity extends ToolBarActivity {

    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setToolBarTitle("设置");
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick({R.id.ll_update, R.id.ll_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_update:
                ToastUtils.showToast(SettingActivity.this,"当前是最新版本");
                break;
            case R.id.ll_about:
                startActivity(new Intent(SettingActivity.this,WebViewActivity.class)
                        .putExtra("id",0));
                break;
        }
    }


}
