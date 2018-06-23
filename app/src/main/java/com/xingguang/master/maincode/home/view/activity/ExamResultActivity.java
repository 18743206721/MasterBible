package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.util.AppUtil;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import cn.iwgang.countdownview.CustomCountDownTimer;

/**
 * 创建日期：2018/5/29
 * 描述:全真模拟考试结果
 * 作者:LiuYu
 */
public class ExamResultActivity extends ToolBarActivity {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.tv_alltime)
    CountdownView tvAlltime;
    @BindView(R.id.tv_fenshu)
    TextView tvFenshu;
    @BindView(R.id.tv_fail)
    TextView tvFail;
    @BindView(R.id.ll_restart)
    LinearLayout llRestart;
    @BindView(R.id.tv_back)
    TextView tvBack;


    String kaoshifenshu; //考试分数
    private int b;
    private String tvTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exam_result;
    }

    @Override
    protected void initView() {
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setToolBarTitle("考试成绩");

        init();
    }

    private void init() {
        kaoshifenshu = getIntent().getStringExtra("kaoshifenshu");
//        tvTitle = getIntent().getStringExtra("tvTitle");
//
//        if (tvTitle!=null){
//            tvAlltime.stop();
//        }


        int a = Integer.parseInt(kaoshifenshu); //及格分数
        if (AppUtil.getYesCount(ExamResultActivity.this).equals("")){
            b = 0;
        }else {
            b = Integer.parseInt(AppUtil.getYesCount(ExamResultActivity.this));//我的分数
        }

        if (b >= a){ //及格
            ivBg.setImageResource(R.mipmap.result_green);
            tvFail.setText("通 过");
            tvFenshu.setText(AppUtil.getYesCount(ExamResultActivity.this));
        }else{
            ivBg.setImageResource(R.mipmap.result_blueflunk);
            tvFail.setText("不 及 格");
            tvFenshu.setText(b+"");
        }

    }

    @OnClick({R.id.ll_restart, R.id.tv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_restart:
                ExamResultActivity.this.finish();
                break;
            case R.id.tv_back:
                Intent intent = new Intent();
                intent.setClass(ExamResultActivity.this, MainActivity.class);
                ExamResultActivity.this.finish();
                break;
        }
    }


}
