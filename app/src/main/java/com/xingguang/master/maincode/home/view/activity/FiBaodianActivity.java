package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.model.ExambaoDianBean;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.OnClick;

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
    int yescount = 0;

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

        if (AppUtil.getYesCount(FiBaodianActivity.this).equals("")){
            tvYescount.setText(yescount+"");
        }else {
            tvYescount.setText(AppUtil.getYesCount(FiBaodianActivity.this));
        }

        if (AppUtil.getNoCount(FiBaodianActivity.this).equals("")){
            tvNocount.setText(yescount+"");
        }else {
            tvNocount.setText(AppUtil.getNoCount(FiBaodianActivity.this));
        }
    }

    @OnClick({R.id.ll_restart, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_restart:
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
