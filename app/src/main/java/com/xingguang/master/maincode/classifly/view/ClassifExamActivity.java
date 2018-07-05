package com.xingguang.master.maincode.classifly.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.maincode.home.model.ExambaoDianBean;
import com.xingguang.master.maincode.home.view.activity.DaTiActivity;
import com.xingguang.master.maincode.home.view.activity.ExamBaoDianActivity;
import com.xingguang.master.maincode.home.view.activity.ExamChapterActivity;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 创建日期：2018/5/30
 * 描述:考试宝典
 * 作者:LiuYu
 */
public class ClassifExamActivity extends ToolBarActivity {
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_baodian)
    ImageView ivBaodian;
    @BindView(R.id.iv_kaoshi)
    ImageView ivKaoshi;
    @BindView(R.id.tab_one)
    LinearLayout tabOne;
    @BindView(R.id.tab_two)
    LinearLayout tabTwo;
    @BindView(R.id.tab_three)
    LinearLayout tabThree;
    @BindView(R.id.tab_four)
    LinearLayout tabFour;
    @BindView(R.id.tabs)
    LinearLayout tabs;
    @BindView(R.id.tab_one_img)
    ImageView tabOneImg;
    @BindView(R.id.tab_one_txt)
    TextView tabOneTxt;
    @BindView(R.id.tab_two_txt)
    TextView tab_two_txt;
    @BindView(R.id.tab_two_img)
    ImageView tab_two_img;

    String name;//名字
    int bumenId;
    int gongzhongId;
    private String count;
    private String kaoshiTime;
    private String kaoshifenshu;
    Intent intent = new Intent();
    private int currentcount = 0;
    private int yesjilu = 0;
    private int nojilu = 0;
    private int biaoshi = 0; //对错标示，0是没有保存上回对错的记录，1是保存了对错的记录

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classifexam;
    }

    @Override
    protected void initView() {
        setToolBarTitle("考试宝典");
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        name = getIntent().getStringExtra("name");
        bumenId = getIntent().getIntExtra("bumenId", 0);
        gongzhongId = getIntent().getIntExtra("gongzhongId", 0);

        //设置分类页按钮颜色
        AppUtil.setThemeColor(tab_two_img, ClassifExamActivity.this, R.drawable.classif_icon);
        tab_two_txt.setTextColor(getResources().getColor(R.color.text_color_red));
        tvName.setText(name);
    }

    @OnClick({R.id.tab_one, R.id.tab_two, R.id.tab_three, R.id.tab_four,
            R.id.iv_baodian, R.id.iv_kaoshi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_one:
                intent.setClass(ClassifExamActivity.this, MainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                MainActivity.instance.finish();
                ClassifExamActivity.this.finish();
                break;
            case R.id.tab_two:
                intent.setClass(ClassifExamActivity.this, MainActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
                MainActivity.instance.finish();
                ClassifExamActivity.this.finish();
                break;
            case R.id.tab_three:
                intent.setClass(ClassifExamActivity.this, MainActivity.class);
                intent.putExtra("id", 3);
                startActivity(intent);
                MainActivity.instance.finish();
                ClassifExamActivity.this.finish();
                break;
            case R.id.tab_four:
                intent.setClass(ClassifExamActivity.this, MainActivity.class);
                intent.putExtra("id", 4);
                startActivity(intent);
                MainActivity.instance.finish();
                ClassifExamActivity.this.finish();
                break;
            case R.id.iv_baodian: //平常练习
                if (AppUtil.isFastDoubleClick(5000)) {
                    return;
                }
                loadinfo(1);
                break;
            case R.id.iv_kaoshi://正规考试
                if (AppUtil.isFastDoubleClick(5000)) {
                    return;
                }
                loadinfo(2);
                break;
        }
    }
    /**先走详情接口，获取时间，题数*/
    private void loadinfo(final int type) {
        OkGo.<String>post(HttpManager.ExamineEntry)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "info")
                .params("UserName", AppUtil.getUserId(this))
                .params("ExamType", type) //考试类型：1:练习,2:考试(必填)
                .params("DepartmentID", bumenId) //部门ID
                .params("ProfessionID", gongzhongId) //工种ID
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        ExambaoDianBean bean = gson.fromJson(response.body().toString(), ExambaoDianBean.class);
                        if (bean.getData() != null) {
                            count = bean.getData().getSubjectAmount();
                            kaoshiTime = bean.getData().getExamDuration();
                            kaoshifenshu = bean.getData().getQualifiedStandard();
                            if (type == 1) { //练习
                                loadsubmit(type);
                            } else { //考试
                                loadsubmit(type);
                            }
                        }
                    }
                });
    }

    /**再走提交接口，传过去exampaperID*/
    private void loadsubmit(final int classif) {
        OkGo.<String>post(HttpManager.ExamineEntry)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "submit")
                .params("UserName", AppUtil.getUserId(this))
                .params("ExamType", classif) //考试类型：1:练习,2:考试(必填)
                .params("DepartmentID", bumenId) //部门ID
                .params("ProfessionID", gongzhongId) //工种ID
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        CommonBean bean = gson.fromJson(response.body().toString(), CommonBean.class);
                        if (!AppUtil.getYesCount(ClassifExamActivity.this).equals("")) {
//                            if (AppUtil.getClassType(ClassifExamActivity.this).equals("")) {
                                //为空，就是返回键

                                //清除答题数量
                                SharedPreferencesUtils.remove(ClassifExamActivity.this, SharedPreferencesUtils.YESCOUNT);
//                            }else { //不为空，就是系统建


//                            }

                        }
                        if (!AppUtil.getNoCount(ClassifExamActivity.this).equals("")) {
//                          if (AppUtil.getClassType(ClassifExamActivity.this).equals("")) {
                                //为空，就是返回键
                              //清除答题数量
                              SharedPreferencesUtils.remove(ClassifExamActivity.this, SharedPreferencesUtils.NOCOUNT);

//                            }else { //不为空，就是系统建

//                            }
                        }


                        if (!AppUtil.getCount(ClassifExamActivity.this).equals("")) {
                            currentcount = Integer.parseInt(AppUtil.getCount(ClassifExamActivity.this));
                        }

                        if (!AppUtil.getYesJilu(ClassifExamActivity.this).equals("")) {
                            yesjilu = Integer.parseInt(AppUtil.getYesJilu(ClassifExamActivity.this));
                            biaoshi = 1;
                        }else {
                            biaoshi = 0;
                        }

                        if (!AppUtil.getNoJilu(ClassifExamActivity.this).equals("")) {
                            nojilu = Integer.parseInt(AppUtil.getNoJilu(ClassifExamActivity.this));
                            biaoshi = 1;
                        }else {
                            biaoshi = 0;
                        }


                        if (classif == 1) { //练习提交
                            startActivity(new Intent(ClassifExamActivity.this, DaTiActivity.class)
                                    .putExtra("exam", "1")
                                    .putExtra("count", count)//传过去的答题数量
                                    .putExtra("currentcount",currentcount)
                                    .putExtra("exampaperID", bean.getExampaperID())
                                    .putExtra("yesjilu",yesjilu)
                                    .putExtra("nojilu",nojilu)
                                    .putExtra("biaoshi",biaoshi)
                                    .putExtra("kaoshi",0)
                            );
                        } else { //考试提交
                            if (AppUtil.isExamined(ClassifExamActivity.this)) {
                                startActivity(new Intent(ClassifExamActivity.this, DaTiActivity.class)
                                        .putExtra("exam", "2")
                                        .putExtra("count", count)//传过去的答题数量
                                        .putExtra("exampaperID", bean.getExampaperID())
                                        .putExtra("kaoshiTime", kaoshiTime)
                                        .putExtra("kaoshifenshu", kaoshifenshu)
                                        .putExtra("kaoshi", 1));
                            }
                        }
                    }
                });
    }


}
