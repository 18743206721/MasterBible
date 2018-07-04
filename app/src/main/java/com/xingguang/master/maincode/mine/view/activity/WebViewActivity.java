package com.xingguang.master.maincode.mine.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.xingguang.master.maincode.home.model.TwoDetailsBean;
import com.xingguang.master.maincode.mine.model.AboutBean;
import com.xingguang.master.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 创建日期：2018/5/31
 * 描述:关于我们,资讯详情信息
 * 作者:LiuYu
 */
public class WebViewActivity extends ToolBarActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.ll_title)
    LinearLayout llTitle;
    @BindView(R.id.webView1)
    WebView webView1;
    @BindView(R.id.ll_last)
    LinearLayout llLast;
    @BindView(R.id.ll_next)
    LinearLayout llNext;
    @BindView(R.id.ll_bot)
    RelativeLayout llBot;

    private int id;//0是设置，1是资讯 2焊工详情
    private int classid; //资讯 classid
    TwoDetailsBean.DataBean dataBean = new TwoDetailsBean.DataBean();
    int LastID;//上一篇
    int NextId;//下一篇
    private String url;
    public static WebViewActivity instance;
    private int title;//title 1是全国考试机构，0是关于我们

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
        instance = this;

        id = getIntent().getIntExtra("id", 0);
        classid = getIntent().getIntExtra("classid", 0);
        title = getIntent().getIntExtra("title",0);
//        init();

        webView1.setHorizontalScrollBarEnabled(false);//水平不显示
        webView1.setVerticalScrollBarEnabled(false); //垂直不显示


        if (id == 0) { //关于我们
            llBot.setVisibility(View.GONE);
            llTitle.setVisibility(View.GONE);

            if (title == 0){
                setToolBarTitle("关于我们");
            }else{
                setToolBarTitle("全国考试机构");
            }
            loadabout(title);
        } else if (id == 1) { //资讯详情信息
            llBot.setVisibility(View.VISIBLE);
            llTitle.setVisibility(View.VISIBLE);
            setToolBarTitle("项目");
            loadxieyi();
        } else { //焊工，培训项目
            llBot.setVisibility(View.VISIBLE);
            llTitle.setVisibility(View.VISIBLE);
            setToolBarTitle("项目");
            loadhangong();
        }

    }

    private void init() {
        // 打开页面时， 自适应屏幕：
        webView1.getSettings().setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        // //将图片调整到适合webview的大小
        webView1.getSettings().setLoadWithOverviewMode(true);
        // 便页面支持缩放：
        webView1.getSettings().setJavaScriptEnabled(true);
        // 支持js
        webView1.getSettings().setBuiltInZoomControls(true);
        webView1.getSettings().setSupportZoom(true);// 支持缩放
        // 如果webView中需要用户手动输入用户名、密码或其他，则webview必须设置支持获取手势焦点。
//        webView1.requestFocusFromTouch();
        // 优先使用缓存
        webView1.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        webView1.loadUrl(url);
//        webView1.setWebViewClient(new WebViewClient() {
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
//                // view.loadUrl(url);
//                view.loadUrl(url);
//                return true;
//            }
//        });
    }

    /**
     * 焊工
     */
    private void loadhangong() {
        OkGo.<String>post(HttpManager.ProjectTraining)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "info")
                .params("InfoID", classid)
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        TwoDetailsBean bean = gson.fromJson(response.body().toString(), TwoDetailsBean.class);
                        if (bean.getData() != null) {
                            dataBean = bean.getData();

                            LastID = bean.getData().getLastID();
                            NextId = bean.getData().getNextID();

                            String html = bean.getData().getContent();
                            String data = html.replace("%@", html);

                            webView1.loadData(data, "text/html; charset=UTF-8", null);
                            tvTitle.setText(bean.getData().getTitle());
                            tvTime.setText(bean.getData().getFormatAddDate());
                        } else {
                            ToastUtils.showToast(WebViewActivity.this, bean.getMsg());
                        }
                    }
                });
    }

    /**
     * 关于我们和首页广告位2
     * @param title
     */
    private void loadabout(int title) {
        if (title == 0){
            OkGo.<String>post(HttpManager.TemplateForm)
                    .tag(this)
                    .cacheKey("cachePostKey")
                    .cacheMode(CacheMode.DEFAULT)
                    .execute(new DialogCallback<String>(this) {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Gson gson = new Gson();
                            AboutBean bean = gson.fromJson(response.body().toString(), AboutBean.class);
                            if (bean.getData() != null) {
                                String html = bean.getData().getContent();
                                String data = html.replace("%@", html);
                                webView1.loadData(data, "text/html; charset=UTF-8", null);
                            } else {
                                ToastUtils.showToast(WebViewActivity.this, bean.getMsg());
                            }
                        }
                    });
        }else {
            OkGo.<String>post(HttpManager.TemplateForm)
                    .tag(this)
                    .cacheKey("cachePostKey")
                    .cacheMode(CacheMode.DEFAULT)
                    .params("InfoID", 0)
                    .execute(new DialogCallback<String>(this) {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Gson gson = new Gson();
                            AboutBean bean = gson.fromJson(response.body().toString(), AboutBean.class);
                            if (bean.getData() != null) {
                                String html = bean.getData().getContent();
                                String data = html.replace("%@", html);
                                webView1.loadData(data, "text/html; charset=UTF-8", null);
                            } else {
                                ToastUtils.showToast(WebViewActivity.this, bean.getMsg());
                            }
                        }
                    });
        }

    }


    private void loadxieyi() {
        OkGo.<String>post(HttpManager.Information)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "info")
                .params("InfoID", classid)
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        TwoDetailsBean bean = gson.fromJson(response.body().toString(), TwoDetailsBean.class);
                        if (bean.getData() != null) {
                            dataBean = bean.getData();

                            LastID = bean.getData().getLastID();
                            NextId = bean.getData().getNextID();

                            String html = bean.getData().getContent();
                            String data = html.replace("%@", html);

                            webView1.loadUrl("javascript:(function(){" +
                                    "var objs = document.getElementsByTagName('img'); " +
                                    "for(var i=0;i<objs.length;i++)  " +
                                    "{"
                                    + "var img = objs[i];   " +
                                    " img.style.maxWidth = '100%';img.style.height='auto';" +
                                    "}" +
                                    "})()");

                            String CSS_STYPE = "<head><style>img{max-width:320px !important;}</style></head>";
                            webView1.loadDataWithBaseURL(null, CSS_STYPE +html, "text/html", "utf-8",null);



//                            webView1.loadData(data, "text/html; charset=UTF-8", null);
                            tvTitle.setText(bean.getData().getTitle());
                            tvTime.setText(bean.getData().getFormatAddDate());
                        } else {
                            ToastUtils.showToast(WebViewActivity.this, bean.getMsg());
                        }
                    }
                });
    }

    @OnClick({R.id.ll_last, R.id.ll_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_last: //上一篇
                if (LastID != 0) {
                    if (id == 1) {//资讯详情信息
                        url = HttpManager.Information;
                    } else {//焊工
                        url = HttpManager.ProjectTraining;
                    }
                    loadlast(url, LastID);
                } else {
                    ToastUtils.showToast(WebViewActivity.this, "已经是第一篇了!");
                }
                break;
            case R.id.ll_next: //下一篇
                if (NextId != 0) {

                    if (id == 1) {//资讯详情信息
                        url = HttpManager.Information;
                    } else {//焊工
                        url = HttpManager.ProjectTraining;
                    }

                    loadnext(url, NextId);
                } else {
                    ToastUtils.showToast(WebViewActivity.this, "已经是最后一篇了!");
                }
                break;
        }
    }


    private void loadlast(String url, int lastID) {
        OkGo.<String>post(url)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "info")
                .params("InfoID", lastID)
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        TwoDetailsBean bean = gson.fromJson(response.body().toString(), TwoDetailsBean.class);
                        if (bean.getData() != null) {
                            String html = bean.getData().getContent();
                            String data = html.replace("%@", html);
                            webView1.loadData(data, "text/html; charset=UTF-8", null);
                            LastID = bean.getData().getLastID();
                            NextId = bean.getData().getNextID();
                            tvTitle.setText(bean.getData().getTitle());
                            tvTime.setText(bean.getData().getFormatAddDate());
                        } else {
                            ToastUtils.showToast(WebViewActivity.this, bean.getMsg());
                        }
                    }
                });
    }


    private void loadnext(String url, int nextId) {
        OkGo.<String>post(url)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "info")
                .params("InfoID", nextId)
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        TwoDetailsBean bean = gson.fromJson(response.body().toString(), TwoDetailsBean.class);
                        if (bean.getData() != null) {
                            String html = bean.getData().getContent();
                            String data = html.replace("%@", html);
                            webView1.loadData(data, "text/html; charset=UTF-8", null);
                            LastID = bean.getData().getLastID();
                            NextId = bean.getData().getNextID();
                            tvTitle.setText(bean.getData().getTitle());
                            tvTime.setText(bean.getData().getFormatAddDate());
                        } else {
                            ToastUtils.showToast(WebViewActivity.this, bean.getMsg());
                        }
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
