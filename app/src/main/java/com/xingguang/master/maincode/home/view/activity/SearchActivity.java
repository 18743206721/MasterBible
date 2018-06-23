package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseActivity;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.model.SearchBean;
import com.xingguang.master.maincode.home.view.adapter.SearchAdapter;
import com.xingguang.master.maincode.mine.view.activity.WebViewActivity;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 搜索页面
 */
public class SearchActivity extends BaseActivity {

    @BindView(R.id.iv_finish)
    ImageView ivFinish;
    @BindView(R.id.tv_play_serch)
    EditText tvPlaySerch;
    @BindView(R.id.tv_sousuo)
    TextView tvSousuo;
    @BindView(R.id.ll_sousuo_serch)
    LinearLayout llSousuoSerch;
    @BindView(R.id.tv_nicheng)
    TextView tvNicheng;
    @BindView(R.id.tab_one_img)
    ImageView tabOneImg;
    @BindView(R.id.tab_one_txt)
    TextView tabOneTxt;
    @BindView(R.id.tab_one)
    LinearLayout tabOne;
    @BindView(R.id.tab_two_img)
    ImageView tabTwoImg;
    @BindView(R.id.tab_two_txt)
    TextView tabTwoTxt;
    @BindView(R.id.tab_two)
    LinearLayout tabTwo;
    @BindView(R.id.tab_three_img)
    ImageView tabThreeImg;
    @BindView(R.id.tab_three_txt)
    TextView tabThreeTxt;
    @BindView(R.id.tab_three)
    LinearLayout tabThree;
    @BindView(R.id.tab_four_img)
    ImageView tabFourImg;
    @BindView(R.id.tab_four_txt)
    TextView tabFourTxt;
    @BindView(R.id.tab_four)
    LinearLayout tabFour;
    @BindView(R.id.tabs)
    LinearLayout tabs;
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.empty)
    ImageView empty;
    @BindView(R.id.rl_baokao)
    RelativeLayout rl_baokao;

    private Intent intent = new Intent();
    private List<SearchBean.DataBean> list = new ArrayList<>();
    private SearchAdapter searchAdapter;
    private String content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        //设置首页按钮颜色
        AppUtil.setThemeColor(tabOneImg, SearchActivity.this, R.drawable.home_icon);
        tabOneTxt.setTextColor(getResources().getColor(R.color.text_color_red));
        content = getIntent().getStringExtra("content");
        searchAdapter = new SearchAdapter(SearchActivity.this, list);
        LinearLayoutManager manager = new LinearLayoutManager(SearchActivity.this);
        rvHome.setLayoutManager(manager);
        rvHome.setAdapter(searchAdapter);
        loadlist();
        initListener();
    }

    private void initListener() {
        searchAdapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //根据后台状态，区分跳转到招工，或者webview页面
                intentclassif(list.get(position).getType(), list.get(position).getID());
            }
        });
    }

    /**
     * 广告位跳转界面
     */
    private void intentclassif(String type, String searchid) {
        if (type.equals("1")) { //1招工信息
            startActivity(new Intent(SearchActivity.this, OneDetailsActivity.class)
                    .putExtra("id", Integer.parseInt(searchid)));
            Log.e("zhaogongid", "intentclassif: " + searchid);
        } else if (type.equals("2")) { //2行业资讯
            startActivity(new Intent(SearchActivity.this, WebViewActivity.class)
                    .putExtra("id", 1)
                    .putExtra("classid", Integer.parseInt(searchid))
            );
        } else if ("3".equals(type)) { //3在线留言
            MainActivity.instance.setBg(1);
            MainActivity.instance.setOnLineFragment();
        } else if ("4".equals(type)) {//4培训报名
            startActivity(new Intent(SearchActivity.this, ProgramsActivity.class)
                    .putExtra("title", "10")
                    .putExtra("classtype", 3));
        } else if ("5".equals(type)) { //5考试报名
            MainActivity.instance.setBg(3);
            MainActivity.instance.setToActivityFragment();
        } else if ("6".equals(type)) { //6培训项目
            startActivity(new Intent(SearchActivity.this, ProgramsActivity.class)
                    .putExtra("title", "0")
                    .putExtra("classtype", 3)
            );
        }
    }

    /**
     * 搜索列表
     */
    private void loadlist() {
        OkGo.<String>post(HttpManager.Search)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("SearchKeyword", content)
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        SearchBean bean = gson.fromJson(response.body().toString(), SearchBean.class);
                        if (bean.getData() != null) {
                            list.clear();
                            list.addAll(bean.getData());

                            if (list.size() == 0) {
                                rl_baokao.setVisibility(View.GONE);
                                empty.setVisibility(View.VISIBLE);
                                ToastUtils.showToast(SearchActivity.this, bean.getMsg());
                            } else {
                                empty.setVisibility(View.GONE);
                                rl_baokao.setVisibility(View.VISIBLE);
                            }

                            searchAdapter.setList(list);


                        } else {
//                            ToastUtils.showToast(SearchActivity.this, bean.getMsg());

                        }
                    }
                });
    }

    @OnClick({R.id.tab_one, R.id.tab_two, R.id.tab_three, R.id.tab_four, R.id.iv_finish, R.id.tv_sousuo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_one:
                intent.setClass(SearchActivity.this, MainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                MainActivity.instance.finish();
                SearchActivity.this.finish();
                break;
            case R.id.tab_two:
                intent.setClass(SearchActivity.this, MainActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
                MainActivity.instance.finish();
                SearchActivity.this.finish();
                break;
            case R.id.tab_three:
                intent.setClass(SearchActivity.this, MainActivity.class);
                intent.putExtra("id", 3);
                startActivity(intent);
                MainActivity.instance.finish();
                SearchActivity.this.finish();
                break;
            case R.id.tab_four:
                intent.setClass(SearchActivity.this, MainActivity.class);
                intent.putExtra("id", 4);
                startActivity(intent);
                MainActivity.instance.finish();
                SearchActivity.this.finish();
                break;
            case R.id.iv_finish:
                finish();
                break;
            case R.id.tv_sousuo:
                if (TextUtils.isEmpty(tvPlaySerch.getText().toString())) {
                    ToastUtils.showToast(SearchActivity.this, "请输入搜索内容！");
                } else {
                    serarchload();
                }
                break;
        }
    }


    private void serarchload() {
        OkGo.<String>post(HttpManager.Search)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("SearchKeyword", tvPlaySerch.getText().toString())
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        SearchBean bean = gson.fromJson(response.body().toString(), SearchBean.class);
                        if (bean.getData() != null) {
                            list.clear();
                            list.addAll(bean.getData());
                            searchAdapter.setList(list);
                        } else {
                            ToastUtils.showToast(SearchActivity.this, bean.getMsg());
                        }
                    }
                });
    }


}
