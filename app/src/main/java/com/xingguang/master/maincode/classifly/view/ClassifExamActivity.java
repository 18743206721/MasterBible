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
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.maincode.home.view.activity.DaTiActivity;
import com.xingguang.master.util.AppUtil;

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
        String classifid = getIntent().getStringExtra("classifid");

        //设置首页按钮颜色
        AppUtil.setThemeColor(tabOneImg, ClassifExamActivity.this, R.drawable.home_icon);
        tabOneTxt.setTextColor(getResources().getColor(R.color.text_color_red));
    }
    Intent intent = new Intent();

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
                startActivity(new Intent(ClassifExamActivity.this, DaTiActivity.class)
                        .putExtra("exam", "1")
                        .putExtra("count", 4));//传过去的答题数量
                break;
            case R.id.iv_kaoshi://正规考试
                startActivity(new Intent(ClassifExamActivity.this, DaTiActivity.class)
                        .putExtra("exam", "2")
                        .putExtra("count", 3));//传过去的答题数量
                break;
        }
    }


}
