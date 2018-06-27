package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.BaseActivity;
import com.xingguang.master.base.FragAdapter;
import com.xingguang.master.maincode.home.view.fragment.ExamBanFragment;
import com.xingguang.master.util.CountDownTimerUtil;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.NoScrollViewpager;
import com.xingguang.master.view.TimerTextView;

import java.io.DataInput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;

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
    TimerTextView tvTitle;
    @BindView(R.id.tvti2)
    TextView tvti2;

    private ExamBanFragment listFragment;
    private String count = "";
    String exam = ""; //上个界面传来的，判断是练习1还是考试2
    private CountDownTimerUtil util;
    String date = "";
    String exampaperID;
    private long timea;
    private String kaoshifenshu; //考试分数

    @Override
    protected int getLayoutId() {
        return R.layout.activity_da_ti;
    }

    @Override
    protected void initView() {
        instance = this;
        exam = getIntent().getStringExtra("exam");
        count = getIntent().getStringExtra("count");
        exampaperID = getIntent().getStringExtra("exampaperID");
        if (exam.equals("2")) {
            tvti2.setVisibility(View.GONE);
            kaoshifenshu = getIntent().getStringExtra("kaoshifenshu");
            timea = Long.parseLong(getIntent().getStringExtra("kaoshiTime")) * 60000;
            //设置时间
            tvTitle.setTimes(timea);
            /**
             * 开始倒计时
             */
            if (!tvTitle.isRun()) {
                tvTitle.start();
            }
        }else {
            tvTitle.setVisibility(View.GONE);
            tvti2.setVisibility(View.VISIBLE);
            tvti2.setText("题库练习");
        }
        initViewPage();
    }

    private void initViewPage() {
        mFragments = new ArrayList<>();
        if (count.equals("")){
            ToastUtils.showToast(DaTiActivity.this,"暂无题目!");
        }else {

            for (int i = 0; i < Integer.parseInt(count); i++) {
                listFragment = new ExamBanFragment(i+1, count, exam, vp_exters,exampaperID,kaoshifenshu,tvTitle);
                mFragments.add(listFragment);
            }
            FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), mFragments);
            vp_exters.setAdapter(adapter);
            vp_exters.setOffscreenPageLimit(0);
            vp_exters.setScanScroll(false); //设置Viewpager禁止滑动
        }
    }


    @Override
    public void countDownTimerListener(String time) {date = time;}

    @Override
    public void countDownTimerFinish() {}


    @OnClick(R.id.ivback)
    public void onViewClicked() {
        finish();
    }


}
