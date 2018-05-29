package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/26
 * 描述:考试宝典
 * 作者:LiuYu
 */
public class ExamBaoDianActivity extends ToolBarActivity {

    @BindView(R.id.title)
    RelativeLayout title;
    @BindView(R.id.iv_extersices)
    ImageView ivExtersices;
    @BindView(R.id.tv_zhonglei)
    TextView tvZhonglei;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.tv_baodian_jieshao)
    TextView tvBaodianJieshao;
    @BindView(R.id.tv_exercises)
    TextView tvExercises;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exam_bao_dian;
    }

    @Override
    protected void initView() {
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setToolBarTitle("考试宝典");
    }

    @OnClick(R.id.tv_exercises)
    public void onViewClicked() {
        startActivity(new Intent(ExamBaoDianActivity.this,DaTiActivity.class)
                .putExtra("exam","1")
                .putExtra("count",5)//传过去的答题数量
        );

    }




}
