package com.xingguang.master.maincode.home.view.fragment;

import android.content.Intent;
import android.content.Loader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
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
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.http.TypeClassif;
import com.xingguang.master.login.model.SmsBean;
import com.xingguang.master.login.view.LoginActivity;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.model.GlideImageLoader;
import com.xingguang.master.maincode.home.model.HomeBean;
import com.xingguang.master.maincode.home.model.SearchBean;
import com.xingguang.master.maincode.home.model.TextQuestionsBean;
import com.xingguang.master.maincode.home.view.activity.OneDetailsActivity;
import com.xingguang.master.maincode.home.view.activity.ProgramsActivity;
import com.xingguang.master.maincode.home.view.activity.SearchActivity;
import com.xingguang.master.maincode.home.view.adapter.OneAdapter;
import com.xingguang.master.maincode.home.view.adapter.ThreeAdapter;
import com.xingguang.master.maincode.home.view.adapter.TwoAdapter;
import com.xingguang.master.maincode.mine.view.activity.WebViewActivity;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.RoundRectImageView;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.ImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 创建日期：2018/5/25
 * 描述:首页
 * 作者:LiuYu
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_play_serch)
    EditText tvPlaySerch;
    @BindView(R.id.ll_sousuo_serch)
    LinearLayout llSousuoSerch;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ll_main_baodian)
    LinearLayout llMainBaodian;
    @BindView(R.id.ll_main_database)
    LinearLayout llMainDatabase;
    @BindView(R.id.ll_home_programs)
    LinearLayout llHomePrograms;
    @BindView(R.id.ll_online)
    LinearLayout llOnline;
    @BindView(R.id.iv_home_helpse)
    ImageView ivHomeHelpse;
    @BindView(R.id.iv_home_botm1)
    ImageView ivHomeBotm1;
    @BindView(R.id.iv_home_botm2)
    RoundRectImageView ivHomeBotm2;
    @BindView(R.id.rv1)
    RecyclerView rv1;
    @BindView(R.id.rv2)
    RecyclerView rv2;
    @BindView(R.id.rv3)
    RecyclerView rv3;
    @BindView(R.id.tvlogined)
    TextView tvlogined;

    private List<HomeBean.DataBean.RecruitinfoBean> onelist = new ArrayList<>();
    private List<HomeBean.DataBean.InformationBean> twolist = new ArrayList<>();
    private List<HomeBean.DataBean.CultivateBean> threelist = new ArrayList<>();
    private List<String> networkImages = new ArrayList<>();

    //广告位
    List<HomeBean.DataBean.Adsense1Bean> adsense1BeanList = new ArrayList<>();
    List<HomeBean.DataBean.Adsense2Bean> adsense2BeanList = new ArrayList<>();
    List<HomeBean.DataBean.Adsense3Bean> adsense3BeanList = new ArrayList<>();

    //搜索相关
    @BindView(R.id.scroll_home)
    NestedScrollView scrollHome;
    @BindView(R.id.tv_sousuo)
    TextView tv_sousuo;

    private View headview;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        load();
    }


    private void load() {
        OkGo.<String>get(HttpManager.index)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        HomeBean bean = gson.fromJson(response.body().toString(), HomeBean.class);

                        //设置轮播图数据
                        for (int i = 0; i < bean.getData().getBannerimg().size(); i++) {
                            networkImages.add(HttpManager.BASE_URL + bean.getData().getBannerimg().get(i).getImgpath());
                        }
                        initpage();

                        //设置广告位数据1
                        ImageLoader.getInstance().initGlide(getActivity())
                                .loadImage(HttpManager.BASE_URL + bean.getData().getAdsense1().get(0).getColumn1(), ivHomeHelpse);
                        //设置广告位数据2
                        ImageLoader.getInstance().initGlide(getActivity())
                                .loadImage(HttpManager.BASE_URL + bean.getData().getAdsense2().get(0).getColumn1(), ivHomeBotm1);
                        //设置广告位数据3
                        ImageLoader.loadRoundImage(getActivity(),
                                HttpManager.BASE_URL + bean.getData().getAdsense3().get(0).getColumn1(), ivHomeBotm2, 5);
                        adsense1BeanList.addAll(bean.getData().getAdsense1());
                        adsense2BeanList.addAll(bean.getData().getAdsense2());
                        adsense3BeanList.addAll(bean.getData().getAdsense3());

                        //招工信息数据
                        onelist.addAll(bean.getData().getRecruitinfo());
                        initone();

                        //行业资讯
                        twolist.addAll(bean.getData().getInformation());
                        inittwo();

                        //焊工培训
                        threelist.addAll(bean.getData().getCultivate());
                        initthree();
                    }
                });
    }

    private void initpage() {
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(networkImages);
        banner.setDelayTime(3000);
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

    private void initone() {
        OneAdapter oneAdapter = new OneAdapter(getActivity(), onelist);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rv1.setLayoutManager(manager);
        rv1.setAdapter(oneAdapter);
        rv1.setNestedScrollingEnabled(false);

        //头布局的xml
        headview = LayoutInflater.from(getActivity()).inflate(R.layout.item_homeone_header, null);
        LinearLayout ll_hworks = headview.findViewById(R.id.ll_hworks);
        TextView itemtv_info = headview.findViewById(R.id.itemtv_info);
        AppUtil.addForeSizeSpan(itemtv_info, "招工信息", getActivity());
        oneAdapter.addHeaderView(headview);//添加头布局到列表中；
        ll_hworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.setBg(1);
                MainActivity.instance.setOnOneFragment();
            }
        });
        oneAdapter.setOnItemClickListener(new OneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //跳转到招工详情
                startActivity(new Intent(getActivity(), OneDetailsActivity.class)
                        .putExtra("id", onelist.get(position - 1).getID()));
            }
        });
    }

    private void inittwo() {
        TwoAdapter twoAdapter = new TwoAdapter(getActivity(), twolist);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rv2.setLayoutManager(manager);
        rv2.setAdapter(twoAdapter);
        rv2.setNestedScrollingEnabled(false);

        //头布局的xml
        headview = LayoutInflater.from(getActivity()).inflate(R.layout.item_homeone_header, null);
        LinearLayout ll_hworks = headview.findViewById(R.id.ll_hworks);
        TextView itemtv_info = headview.findViewById(R.id.itemtv_info);
        AppUtil.addForeSizeSpan(itemtv_info, "行业资讯", getActivity());
        twoAdapter.addHeaderView(headview);//添加头布局到列表中；
        ll_hworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.setBg(1);
                MainActivity.instance.setOnTwoFragment();
            }
        });

        //行业资讯
        twoAdapter.setOnItemClickListener(new TwoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), WebViewActivity.class)
                        .putExtra("id", 1)
                        .putExtra("classid", twolist.get(position - 1).getID())
                );
            }
        });
    }

    private void initthree() {
        ThreeAdapter threeAdapter = new ThreeAdapter(getActivity(), threelist);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rv3.setLayoutManager(manager);
        rv3.setAdapter(threeAdapter);
        rv3.setNestedScrollingEnabled(false);

        //头布局的xml
        headview = LayoutInflater.from(getActivity()).inflate(R.layout.item_homeone_header, null);
        LinearLayout ll_hworks = headview.findViewById(R.id.ll_hworks);
        TextView itemtv_info = headview.findViewById(R.id.itemtv_info);
        AppUtil.addForeSizeSpan(itemtv_info, "焊工培训", getActivity());
        threeAdapter.addHeaderView(headview);//添加头布局到列表中；
        ll_hworks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ProgramsActivity.class)
                        .putExtra("classtype", 2)
                );
            }
        });
        threeAdapter.setOnItemClickListener(new ThreeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(getActivity(), WebViewActivity.class)
                        .putExtra("id", 2)
                        .putExtra("classid", threelist.get(position - 1).getID())
                );
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppUtil.getUserId(getActivity()).equals("")) { //为空
            tvlogined.setText("登录/注册");
        } else { //不为空
            tvlogined.setText("已登录");
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.tv_sousuo, R.id.ll_login, R.id.ll_main_baodian, R.id.ll_main_database, R.id.ll_home_programs, R.id.ll_online, R.id.iv_home_helpse, R.id.iv_home_botm1, R.id.iv_home_botm2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sousuo://搜索按钮
                if (AppUtil.isExamined(getActivity())) {
                    if (TextUtils.isEmpty(tvPlaySerch.getText().toString())) {
                        ToastUtils.showToast(getActivity(), "请输入搜索内容！");
                    } else {
                        startActivity(new Intent(getActivity(), SearchActivity.class)
                                .putExtra("content", tvPlaySerch.getText().toString()));
                    }
                }
                break;
            case R.id.ll_login: //登录
                if (AppUtil.getUserId(getActivity()).equals("")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            case R.id.ll_main_baodian: //考试宝典,练习
                if (AppUtil.isExamined(getActivity())){
                    MainActivity.instance.setBg(1);
                    MainActivity.instance.setToBaodianFragment();
                }
                break;
            case R.id.ll_main_database://考试题库，考试
                if (AppUtil.isExamined(getActivity())) {
                    MainActivity.instance.setBg(1);
                    MainActivity.instance.setToExamChapterFragment();
                }
                break;
            case R.id.ll_home_programs://培训项目
                if (AppUtil.isExamined(getActivity())) {
                    startActivity(new Intent(getActivity(), ProgramsActivity.class)
                            .putExtra("classtype", 1));
                }
                break;
            case R.id.ll_online://在线留言
                if (AppUtil.isExamined(getActivity())) {
                    MainActivity.instance.setBg(1);
                    MainActivity.instance.setOnLineFragment();
                }
                break;
            case R.id.iv_home_helpse://帮我选考点
                if (AppUtil.isExamined(getActivity())) {
                    if (adsense1BeanList.size() != 0) {
                        intentclassif(adsense1BeanList.get(0).getUrl(), adsense1BeanList.get(0).getTitle());
                    }
                }
                break;
            case R.id.iv_home_botm1://底部图片1
                if (AppUtil.isExamined(getActivity())) {
                    if (adsense2BeanList.size() != 0) {
                        intentclassif(adsense2BeanList.get(0).getUrl(), adsense2BeanList.get(0).getTitle());
                    }
                }
                break;
            case R.id.iv_home_botm2://底部图片2
                if (AppUtil.isExamined(getActivity())) {
                    if (adsense3BeanList.size() != 0) {
                        intentclassif(adsense3BeanList.get(0).getUrl(), adsense3BeanList.get(0).getTitle());
                    }
                }
                break;
        }
    }

    /**
     * 广告位跳转界面
     */
    private void intentclassif(String url, String title) {
        if (url.equals("1")) { //1招工信息
            MainActivity.instance.setBg(1);
            MainActivity.instance.setOnOneFragment();
        } else if (url.equals("2")) { //2行业资讯
            MainActivity.instance.setBg(1);
            MainActivity.instance.setOnTwoFragment();
        } else if ("3".equals(url)) { //3在线留言
            MainActivity.instance.setBg(1);
            MainActivity.instance.setOnLineFragment();
        } else if ("4".equals(url)) {//4培训报名
            startActivity(new Intent(getActivity(), ProgramsActivity.class)
                    .putExtra("title", "10")
                    .putExtra("classtype", 3)
            );
        } else if ("5".equals(url)) { //5考试报名
            MainActivity.instance.setBg(3);
            MainActivity.instance.setToActivityFragment();
        } else if ("6".equals(url)) { //6培训项目
            startActivity(new Intent(getActivity(), ProgramsActivity.class)
                    .putExtra("title", title)
                    .putExtra("classtype", 3)
            );
        }
    }

}
