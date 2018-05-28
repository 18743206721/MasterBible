package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 创建日期：2018/5/28
 * 描述:模拟考试
 * 作者:LiuYu
 */
public class ExamChapterActivity extends ToolBarActivity {

    @BindView(R.id.iv_extersices)
    ImageView ivExtersices;
    @BindView(R.id.tv_zhonglei)
    TextView tvZhonglei;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_fenshu)
    TextView tvFenshu;
    @BindView(R.id.tv_baodian_jieshao)
    TextView tvBaodianJieshao;
    @BindView(R.id.iv_start)
    ImageView ivStart;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exam_chapter;
    }

    @Override
    protected void initView() {
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setToolBarTitle("模拟考试");
    }

    @OnClick(R.id.iv_start)
    public void onViewClicked() {
        startActivity(new Intent(ExamChapterActivity.this, DaTiActivity.class)
                .putExtra("exam", "2")
                .putExtra("count", 3)//传过去的答题数量
        );
    }


}
