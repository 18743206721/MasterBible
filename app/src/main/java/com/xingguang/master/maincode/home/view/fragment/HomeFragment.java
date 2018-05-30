package com.xingguang.master.maincode.home.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.login.view.LoginActivity;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.view.adapter.OneAdapter;
import com.xingguang.master.maincode.home.view.adapter.ThreeAdapter;
import com.xingguang.master.maincode.home.view.adapter.TwoAdapter;
import com.xingguang.master.util.RoundRectImageView;
import com.xingguang.master.util.ToastUtils;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> onelist = new ArrayList<>();
    private List<String> twolist = new ArrayList<>();
    private List<String> threelist = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {

        initone();
        inittwo();
        initthree();
    }

    private void initone() {
        onelist.add("b");
        onelist.add("c");

        OneAdapter oneAdapter = new OneAdapter(getActivity(),onelist);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rv1.setLayoutManager(manager);
        rv1.setAdapter(oneAdapter);
        rv1.setNestedScrollingEnabled(false);

        oneAdapter.setOnItemClickListener(new OneAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showToast(getActivity(),"click"+position);
            }
        });
        oneAdapter.setmOnItemMoreClickListener(new OneAdapter.OnItemMoreClickListener() {
            @Override
            public void onItemMoreClick(LinearLayout onemore, int position) {
                ToastUtils.showToast(getActivity(),"more"+position);
            }
        });


    }


    private void inittwo() {
        twolist.add("c");
        twolist.add("c");
        twolist.add("d");

        TwoAdapter twoAdapter = new TwoAdapter(getActivity(),twolist);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rv2.setLayoutManager(manager);
        rv2.setAdapter(twoAdapter);
        rv2.setNestedScrollingEnabled(false);

        twoAdapter.setOnItemClickListener(new TwoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showToast(getActivity(),"click"+position);
            }
        });
        twoAdapter.setmOnItemMoreClickListener(new TwoAdapter.OnItemMoreClickListener() {
            @Override
            public void onItemMoreClick(LinearLayout onemore, int position) {
                ToastUtils.showToast(getActivity(),"more"+position);
            }
        });

    }

    private void initthree() {
        threelist.add("d");
        threelist.add("b");
        threelist.add("c");
        threelist.add("b");

        ThreeAdapter threeAdapter = new ThreeAdapter(getActivity(),threelist);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rv3.setLayoutManager(manager);
        rv3.setAdapter(threeAdapter);
        rv3.setNestedScrollingEnabled(false);

        threeAdapter.setOnItemClickListener(new ThreeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.showToast(getActivity(),"click"+position);
            }
        });
        threeAdapter.setmOnItemMoreClickListener(new ThreeAdapter.OnItemMoreClickListener() {
            @Override
            public void onItemMoreClick(LinearLayout onemore, int position) {
                ToastUtils.showToast(getActivity(),"more"+position);
            }
        });


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
