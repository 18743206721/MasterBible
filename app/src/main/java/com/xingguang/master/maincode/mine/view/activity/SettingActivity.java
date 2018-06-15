package com.xingguang.master.maincode.mine.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.http.CommonBean;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.login.model.LoginBean;
import com.xingguang.master.login.view.LoginActivity;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.ImageLoader;

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
    @BindView(R.id.ll_backlogin)
    LinearLayout ll_backlogin;


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

    @OnClick({R.id.ll_update, R.id.ll_about, R.id.ll_backlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_update:
                ToastUtils.showToast(SettingActivity.this, "当前是最新版本");
                break;
            case R.id.ll_about:
                startActivity(new Intent(SettingActivity.this, WebViewActivity.class)
                        .putExtra("id", 0));
                break;
            case R.id.ll_backlogin: //退出登录
                loadback();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (AppUtil.getUserId(SettingActivity.this).equals("")) {
            ll_backlogin.setVisibility(View.GONE);
        } else {
            ll_backlogin.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 退出界面
     */
    private void loadback() {
        OkGo.<String>get(HttpManager.NonLogin)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("UserName", AppUtil.getUserId(SettingActivity.this))
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        CommonBean loginBean = gson.fromJson(response.body().toString(), CommonBean.class);
                        SharedPreferencesUtils.clear(SettingActivity.this);
                        ToastUtils.showToast(SettingActivity.this, loginBean.getResult());
                        finish();
                    }
                });
    }


}
