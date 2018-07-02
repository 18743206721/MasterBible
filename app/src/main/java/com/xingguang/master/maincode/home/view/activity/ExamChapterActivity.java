package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
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
 * 创建日期：2018/5/28
 * 描述:模拟考试 考试入口
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
    int bumenId;
    int gongzhongId;
    String count; //答题数量
    private String kaoshiTime;//考试时间
    private String kaoshifenshu = ""; //考试分数


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
                .params("ExamType", 2) //考试类型：1:练习,2:考试(必填)
                .params("DepartmentID", bumenId) //部门ID
                .params("ProfessionID", gongzhongId) //工种ID
                .execute(new DialogCallback<String>(this){
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        ExambaoDianBean bean = gson.fromJson(response.body().toString(), ExambaoDianBean.class);
                        if (bean.getData() != null) {
                            tvZhonglei.setText(bean.getData().getQuestionBank()); //名字
                            tvTime.setText(bean.getData().getExamDuration()+"分钟");//考试时间
                            tvFenshu.setText(bean.getData().getQualifiedStandard()+"分");//考试分数
                            tvBaodianJieshao.setText(bean.getData().getExamExplain()); //考试说明
                            count = bean.getData().getSubjectAmount();
                            kaoshiTime = bean.getData().getExamDuration();
                            kaoshifenshu = bean.getData().getQualifiedStandard();
                        }
                    }
                });
    }


    @OnClick(R.id.iv_start)
    public void onViewClicked() {
        if (AppUtil.isFastDoubleClick(3000)) {
            return;
        }else {
            loadcommit();
        }
    }


    private void loadcommit() {
        OkGo.<String>post(HttpManager.ExamineEntry)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "submit")
                .params("UserName", AppUtil.getUserId(this))
                .params("ExamType", 2) //考试类型：1:练习,2:考试(必填)
                .params("DepartmentID", bumenId) //部门ID
                .params("ProfessionID", gongzhongId) //工种ID
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        CommonBean bean = gson.fromJson(response.body().toString(), CommonBean.class);

                        if (!AppUtil.getYesCount(ExamChapterActivity.this).equals("")){
                            //清除答题数量
                            SharedPreferencesUtils.remove(ExamChapterActivity.this,SharedPreferencesUtils.YESCOUNT);
                        }

                        if (!AppUtil.getNoCount(ExamChapterActivity.this).equals("")){
                            //清除答题数量
                            SharedPreferencesUtils.remove(ExamChapterActivity.this,SharedPreferencesUtils.NOCOUNT);
                        }

                        startActivity(new Intent(ExamChapterActivity.this, DaTiActivity.class)
                                .putExtra("exam", "2")
                                .putExtra("count", count)//传过去的答题数量
                                .putExtra("exampaperID",bean.getExampaperID())
                                .putExtra("kaoshiTime",kaoshiTime)
                                .putExtra("kaoshifenshu",kaoshifenshu)
                        );
                    }
                });
    }



}
