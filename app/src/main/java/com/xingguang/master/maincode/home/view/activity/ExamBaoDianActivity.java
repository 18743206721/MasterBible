package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.maincode.home.model.ExambaoDianBean;
import com.xingguang.master.util.AppUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建日期：2018/5/26
 * 描述:练习入口
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
    int bumenId;
    int gongzhongId;
    private String count;

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
        bumenId = getIntent().getIntExtra("bumenId",0);
        gongzhongId = getIntent().getIntExtra("gongzhongId",0);
        load();
    }

    private void load() {
        OkGo.<String>post(HttpManager.ExamineEntry)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "info")
                .params("UserName", AppUtil.getUserId(this))
                .params("ExamType", 1) //考试类型：1:练习,2:考试(必填)
                .params("DepartmentID", bumenId) //部门ID
                .params("ProfessionID", gongzhongId) //工种ID
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        ExambaoDianBean bean = gson.fromJson(response.body().toString(), ExambaoDianBean.class);
                        if (bean.getData() != null) {
                            tvZhonglei.setText(bean.getData().getQuestionBank()); //名字
                            tvCount.setText(bean.getData().getSubjectAmount());//题目总数
                            tvBaodianJieshao.setText(bean.getData().getPracticeExplain()); //题库说明
                            count = bean.getData().getSubjectAmount();
                        }
                    }
                });
    }

    @OnClick(R.id.tv_exercises)
    public void onViewClicked() {
        startActivity(new Intent(ExamBaoDianActivity.this,DaTiActivity.class)
                .putExtra("exam","1")
                .putExtra("count",count)//传过去的答题数量
        );

    }




}
