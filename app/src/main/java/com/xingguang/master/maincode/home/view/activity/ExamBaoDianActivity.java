package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
import com.xingguang.master.http.CommonBean;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.maincode.home.model.ExambaoDianBean;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.SharedPreferencesUtils;

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
    @BindView(R.id.tv_chang)
    TextView tv_chang;
    @BindView(R.id.tv_select)
    TextView tv_select;

    int bumenId;
    int gongzhongId;
    private String count;
    private int currentcount = 0;//当前答题数量
    private int yesjilu = 0;
    private int nojilu = 0;
    private int biaoshi = 0; //对错标示，0是没有保存上回对错的记录，1是保存了对错的记录
    public static ExamBaoDianActivity instance;

    private int selected = 1; // 1常规题库，2精选题库

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exam_bao_dian;
    }

    @Override
    protected void initView() {
        instance = this;
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setToolBarTitle("考试宝典");
        bumenId = getIntent().getIntExtra("bumenId", 0);
        gongzhongId = getIntent().getIntExtra("gongzhongId", 0);
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

    @OnClick({R.id.tv_chang, R.id.tv_select,R.id.tv_exercises})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_chang:
                selected = 1;
                tv_chang.setBackground(ContextCompat.getDrawable(ExamBaoDianActivity.this,R.drawable.bg_view_blue));
                tv_chang.setTextColor(ContextCompat.getColor(ExamBaoDianActivity.this,R.color.home_bule));
                tv_select.setBackground(ContextCompat.getDrawable(ExamBaoDianActivity.this,R.drawable.bg_view_white));
                tv_select.setTextColor(ContextCompat.getColor(ExamBaoDianActivity.this,R.color.textBlack));
                break;
            case R.id.tv_select:
                selected = 2;
                tv_chang.setBackground(ContextCompat.getDrawable(ExamBaoDianActivity.this,R.drawable.bg_view_white));
                tv_chang.setTextColor(ContextCompat.getColor(ExamBaoDianActivity.this,R.color.textBlack));
                tv_select.setBackground(ContextCompat.getDrawable(ExamBaoDianActivity.this,R.drawable.bg_view_blue));
                tv_select.setTextColor(ContextCompat.getColor(ExamBaoDianActivity.this,R.color.home_bule));
                break;
            case R.id.tv_exercises:
                if (AppUtil.isFastDoubleClick(4000)) {
                    return;
                }
                loadcommit();
                break;
        }
    }

    private void loadcommit() {
        OkGo.<String>post(HttpManager.ExamineEntry)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "submit")
                .params("UserName", AppUtil.getUserId(this))
                .params("ExamType", 1) //考试类型：1:练习,2:考试(必填)
                .params("DepartmentID", bumenId) //部门ID
                .params("ProfessionID", gongzhongId) //工种ID
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        CommonBean bean = gson.fromJson(response.body().toString(), CommonBean.class);

                        if (!AppUtil.getYesCount(ExamBaoDianActivity.this).equals("")) {
                            //清除答题数量
                            SharedPreferencesUtils.remove(ExamBaoDianActivity.this, SharedPreferencesUtils.YESCOUNT);
                        }

                        if (!AppUtil.getNoCount(ExamBaoDianActivity.this).equals("")) {
                            //清除答题数量
                            SharedPreferencesUtils.remove(ExamBaoDianActivity.this, SharedPreferencesUtils.NOCOUNT);
                        }


                        if (!AppUtil.getCount(ExamBaoDianActivity.this).equals("")) {
                            currentcount = Integer.parseInt(AppUtil.getCount(ExamBaoDianActivity.this));
                        }

                        if (!AppUtil.getYesJilu(ExamBaoDianActivity.this).equals("")) {
                            yesjilu = Integer.parseInt(AppUtil.getYesJilu(ExamBaoDianActivity.this));
                            biaoshi = 1;
                        } else {
                            biaoshi = 0;
                        }

                        if (!AppUtil.getNoJilu(ExamBaoDianActivity.this).equals("")) {
                            nojilu = Integer.parseInt(AppUtil.getNoJilu(ExamBaoDianActivity.this));
                            biaoshi = 1;
                        } else {
                            biaoshi = 0;
                        }

                        startActivity(new Intent(ExamBaoDianActivity.this, DaTiActivity.class)
                                .putExtra("exam", "1")
                                .putExtra("count", count)//传过去的答题数量
                                .putExtra("exampaperID", bean.getExampaperID())
                                .putExtra("currentcount", currentcount)
                                .putExtra("yesjilu", yesjilu)
                                .putExtra("nojilu", nojilu)
                                .putExtra("biaoshi", biaoshi)
                                .putExtra("kaoshi", 0)
                        );
                    }
                });
    }


}
