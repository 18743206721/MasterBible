package com.xingguang.master.maincode.home.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.BaseActivity;
import com.xingguang.master.base.FragAdapter;
import com.xingguang.master.maincode.home.view.fragment.ExamBanFragment;
import com.xingguang.master.util.CountDownTimerUtil;
import com.xingguang.master.util.CountDownView;
import com.xingguang.master.view.NoScrollViewpager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 创建日期：2018/5/26
 * 描述:答题界面
 * 作者:LiuYu
 */
public class DaTiActivity extends BaseActivity implements CountDownTimerUtil.CountDownTimerListener {

    public static DaTiActivity instance;
    ArrayList<Fragment> mFragments;
    @BindView(R.id.vp_exters)
    NoScrollViewpager vp_exters;
    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.csv2)
    CountDownView tvTitle;

    private ExamBanFragment listFragment;
    private int sum = 0;

    String exam = ""; //上个界面传来的，判断是练习1还是考试2
    private CountDownTimerUtil util;
    String date = "";


    @Override
    protected int getLayoutId() {
        return R.layout.activity_da_ti;
    }

    @Override
    protected void initView() {
        instance = this;
        exam = getIntent().getStringExtra("exam");
        sum = getIntent().getIntExtra("count",0);
//        getToolbarBack().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });

        tvTitle.setStopTime(Long.valueOf("3600000"));

        if (exam.equals("1")) {
//            setToolBarTitle("题库练习");
//            tvTitle.sette
            tvTitle.setStopTime(Long.valueOf("3600000"));
        }else {
//            setToolBarTitle("倒计时"+date);
            tvTitle.setStopTime(Long.valueOf("3600000"));
        }

        initViewPage();
        vp_exters.setScanScroll(false); //设置Viewpager禁止滑动
    }


    private void initViewPage() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < sum; i++) {
            listFragment = new ExamBanFragment(i+1, sum, exam, vp_exters);
            mFragments.add(listFragment);
        }
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), mFragments);
        vp_exters.setAdapter(adapter);
        vp_exters.setOffscreenPageLimit(0);
    }


    @Override
    public void countDownTimerListener(String time) {
        date = time;

    }

    @Override
    public void countDownTimerFinish() {

    }


    @OnClick(R.id.ivback)
    public void onViewClicked() {
        finish();
    }


}
