package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.main.view.activity.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Single;


/**
 * 创建日期：2018/5/28
 * 描述:考试宝典练习答题完成页面
 * 作者:LiuYu
 */
public class FiBaodianActivity extends ToolBarActivity {

    @BindView(R.id.tv_yescount)
    TextView tvYescount;
    @BindView(R.id.tv_nocount)
    TextView tvNocount;
    @BindView(R.id.tv_weizuo)
    TextView tvWeizuo;
    @BindView(R.id.ll_restart)
    LinearLayout llRestart;
    @BindView(R.id.tv_back)
    TextView tvBack;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fi_baodian;
    }

    @Override
    protected void initView() {
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setToolBarTitle("考试宝典");
    }

    @OnClick({R.id.ll_restart, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_restart:
                startActivity(new Intent(FiBaodianActivity.this, DaTiActivity.class)
                        .putExtra("exam", "2")
                        .putExtra("count", 4));//传过去的答题数量
                        FiBaodianActivity.this.finish();
                break;
            case R.id.tv_back:
                Intent intent = new Intent();
                intent.setClass(FiBaodianActivity.this, MainActivity.class);
                FiBaodianActivity.this.finish();
                break;
        }
    }




}
