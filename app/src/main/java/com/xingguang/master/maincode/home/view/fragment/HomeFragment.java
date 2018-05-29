package com.xingguang.master.maincode.home.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.login.view.LoginActivity;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.util.RoundRectImageView;
import com.youth.banner.Banner;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 创建日期：2018/5/25
 * 描述:首页
 * 作者:LiuYu
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_play_serch)
    TextView tvPlaySerch;
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
    @BindView(R.id.ll_hworks)
    LinearLayout llHworks;
    @BindView(R.id.rl_home_workers)
    RelativeLayout rlHomeWorkers;
    @BindView(R.id.item_tv_city_title)
    TextView itemTvCityTitle;
    @BindView(R.id.tv_home_ads)
    TextView tvHomeAds;
    @BindView(R.id.tv_home_payment)
    TextView tvHomePayment;
    @BindView(R.id.tv_apply)
    TextView tvApply;
    @BindView(R.id.item_layout)
    LinearLayout itemLayout;
    @BindView(R.id.item_tv_city_title2)
    TextView itemTvCityTitle2;
    @BindView(R.id.tv_home_ads2)
    TextView tvHomeAds2;
    @BindView(R.id.tv_home_payment2)
    TextView tvHomePayment2;
    @BindView(R.id.tv_apply2)
    TextView tvApply1;
    @BindView(R.id.item_layout2)
    LinearLayout itemLayout2;
    @BindView(R.id.ll_hinformation)
    LinearLayout llHinformation;
    @BindView(R.id.rl_home_information)
    RelativeLayout rlHomeInformation;
    @BindView(R.id.item_tv_infotitle)
    TextView itemTvInfotitle;
    @BindView(R.id.item_tv_infortime)
    TextView itemTvInfortime;
    @BindView(R.id.item_tv_info_content)
    TextView itemTvInfoContent;
    @BindView(R.id.item_tv_infotitle2)
    TextView itemTvInfotitle2;
    @BindView(R.id.item_tv_infortime2)
    TextView itemTvInfortime2;
    @BindView(R.id.item_tv_info_content2)
    TextView itemTvInfoContent2;
    @BindView(R.id.ll_welder)
    LinearLayout llWelder;
    @BindView(R.id.rl_home_welder)
    RelativeLayout rlHomeWelder;
    @BindView(R.id.item_tv_weldertitle)
    TextView itemTvWeldertitle;
    @BindView(R.id.item_tv_welder_content)
    TextView itemTvWelderContent;
    @BindView(R.id.item_tv_weldertitle1)
    TextView itemTvWeldertitle1;
    @BindView(R.id.item_tv_welder_content1)
    TextView itemTvWelderContent1;
    @BindView(R.id.iv_home_botm1)
    ImageView ivHomeBotm1;
    @BindView(R.id.iv_home_botm2)
    RoundRectImageView ivHomeBotm2;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        BaodianFragment  baodianFragment = (BaodianFragment) getActivity() .getSupportFragmentManager() .
                findFragmentByTag("baodianFragment");

    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.ll_sousuo_serch, R.id.ll_login, R.id.ll_main_baodian, R.id.ll_main_database, R.id.ll_home_programs, R.id.ll_online, R.id.iv_home_helpse, R.id.iv_home_botm1, R.id.iv_home_botm2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_sousuo_serch: //搜索
                break;
            case R.id.ll_login: //登录
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_main_baodian: //考试宝典
                MainActivity.instance.setBg(1);
                MainActivity.instance.setToBaodianFragment();
                break;
            case R.id.ll_main_database://考试题库
                MainActivity.instance.setBg(1);
                MainActivity.instance.setToExamChapterFragment();
                break;
            case R.id.ll_home_programs://培训项目
                MainActivity.instance.setBg(1);
                MainActivity.instance.setOnProgramsFragment();
                break;
            case R.id.ll_online://在线留言
                MainActivity.instance.setBg(1);
                MainActivity.instance.setOnLineFragment();
                break;
            case R.id.iv_home_helpse://帮我选考点
                break;
            case R.id.iv_home_botm1://底部图片1
                break;
            case R.id.iv_home_botm2://底部图片2
                break;
        }
    }





}
