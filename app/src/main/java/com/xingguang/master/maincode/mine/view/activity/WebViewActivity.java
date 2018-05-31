package com.xingguang.master.maincode.mine.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;

import butterknife.BindView;


/**
 * 创建日期：2018/5/31
 * 描述:关于我们
 * 作者:LiuYu
 */
public class WebViewActivity extends ToolBarActivity {

    @BindView(R.id.webView1)
    WebView webView1;
    @BindView(R.id.ll_bot)
    RelativeLayout ll_bot;

    private int id;//0是设置，1是资讯和焊工详情
    private String classid; //资讯或者是焊工classid

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setToolBarTitle("关于我们");

        id = getIntent().getIntExtra("id",0);
        classid = getIntent().getStringExtra("classid");

        if (id == 0){
            ll_bot.setVisibility(View.GONE);
        }else if (id == 1){
            ll_bot.setVisibility(View.VISIBLE);
        }

        loadxieyi();
    }

    private void loadxieyi() {
//        String html = state.getUserAgreement();
//        String data = html.replace("%@", html);
//        webView1.loadData(data, "text/html; charset=UTF-8", null);
    }


}
