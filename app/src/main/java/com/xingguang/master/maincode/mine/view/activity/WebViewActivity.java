package com.xingguang.master.maincode.mine.view.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
import com.xingguang.master.maincode.home.model.TwoDetailsBean;
import com.xingguang.master.maincode.mine.model.AboutBean;
import com.xingguang.master.maincode.mine.model.KaoShiInfoBean;
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
        } else if (id == 2){ //焊工，培训项目
            llBot.setVisibility(View.VISIBLE);
            llTitle.setVisibility(View.VISIBLE);
            setToolBarTitle("项目");
            loadhangong();
        }else if (id == 3){
            llBot.setVisibility(View.VISIBLE);
            llTitle.setVisibility(View.VISIBLE);
            setToolBarTitle("考试机构");
            loadkaoshi();
        }

//        init();

    }

    /**
     * 考试机构详情页面
     * */
    private void loadkaoshi() {
        OkGo.<String>post(HttpManager.ExaminingBody)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "info")
                .params("InfoID", classid)
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        KaoShiInfoBean bean = gson.fromJson(response.body(), KaoShiInfoBean.class);

                        if (bean.getData() != null) {

                            LastID = bean.getData().getLastID();
                            NextId = bean.getData().getNextID();

                            String html = bean.getData().getContent();
                            String data = html.replace("%@", html);
                            webView1.loadData(data, "text/html; charset=UTF-8", null);
                        } else {
                            ToastUtils.showToast(WebViewActivity.this, bean.getMsg());
                        }
                    }
                });


    }

    private void init() {
        WebSettings webSettings = webView1.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //防止中文乱码
        webView1.getSettings().setDefaultTextEncodingName("UTF-8");
        // 先载入JS代码
        // 格式规定为:file:///android_asset/文件名.html
//        mWebView.loadUrl("file:///android_asset/image.html");
        webView1.loadUrl("http://www.toutiao.com/a6401738581286682881/#p=1");
        //载入js
//        webView1.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
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
                    } else if (id == 2){//焊工
                        url = HttpManager.ProjectTraining;
                    }else if (id == 3){
                        url = HttpManager.ExaminingBody;
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
                    } else if (id == 2){//焊工
                        url = HttpManager.ProjectTraining;
                    }else if (id == 3){
                        url = HttpManager.ExaminingBody;
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
