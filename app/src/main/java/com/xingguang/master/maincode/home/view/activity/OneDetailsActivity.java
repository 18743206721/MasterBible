package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.view.adapter.OneZhizeAdapter;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 创建日期：2018/5/31
 * 描述:招工详情
 * 作者:LiuYu
 */
public class OneDetailsActivity extends ToolBarActivity {

    @BindView(R.id.tv_gongsi)
    TextView tvGongsi;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_zhize)
    TextView tvZhize;
    @BindView(R.id.rv_zhize)
    RecyclerView rvZhize;
    @BindView(R.id.tv_zige)
    TextView tvZige;
    @BindView(R.id.rv_zige)
    RecyclerView rvZige;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_jianjie)
    TextView tvJianjie;
    @BindView(R.id.et_jianjie)
    EditText etJianjie;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.tab_one_img)
    ImageView tabOneImg;
    @BindView(R.id.tab_one_txt)
    TextView tabOneTxt;
    @BindView(R.id.tab_one)
    LinearLayout tabOne;
    @BindView(R.id.tab_two_img)
    ImageView tabTwoImg;
    @BindView(R.id.tab_two_txt)
    TextView tabTwoTxt;
    @BindView(R.id.tab_two)
    LinearLayout tabTwo;
    @BindView(R.id.tab_three_img)
    ImageView tabThreeImg;
    @BindView(R.id.tab_three_txt)
    TextView tabThreeTxt;
    @BindView(R.id.tab_three)
    LinearLayout tabThree;
    @BindView(R.id.tab_four_img)
    ImageView tabFourImg;
    @BindView(R.id.tab_four_txt)
    TextView tabFourTxt;
    @BindView(R.id.tab_four)
    LinearLayout tabFour;
    @BindView(R.id.tabs)
    LinearLayout tabs;
    Intent intent  = new Intent();
    private List<String> onelist = new ArrayList<>();
    private List<String> twolist = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_one_details;
    }

    @Override
    protected void initView() {
        setToolBarTitle("招工详情");
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        int id = getIntent().getIntExtra("id",0); //招工id

        //设置首页按钮颜色
        AppUtil.setThemeColor(tabOneImg,OneDetailsActivity.this, R.drawable.home_icon);
        tabOneTxt.setTextColor(getResources().getColor(R.color.text_color_red));

        init();

    }

    private void init() {
        OneZhizeAdapter oneadapter = new OneZhizeAdapter(OneDetailsActivity.this,onelist,"1");
        LinearLayoutManager manager = new LinearLayoutManager(OneDetailsActivity.this);
        rvZhize.setLayoutManager(manager);
        rvZhize.setAdapter(oneadapter);
        rvZhize.setNestedScrollingEnabled(false);


        OneZhizeAdapter twoadapter = new OneZhizeAdapter(OneDetailsActivity.this,twolist,"2");
        LinearLayoutManager manager1 = new LinearLayoutManager(OneDetailsActivity.this);
        rvZige.setLayoutManager(manager1);
        rvZige.setAdapter(twoadapter);
        rvZige.setNestedScrollingEnabled(false);

        AppUtil.addForeColorSpan(tvName,"姓名*");
        AppUtil.addForeColorSpan(tvPhone,"电话*");
        AppUtil.addForeColorSpan(tvJianjie,"个人简介*");

    }

    @OnClick({R.id.btn_commit, R.id.tab_one, R.id.tab_two, R.id.tab_three, R.id.tab_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                if (validate()) {
                    load();
                }
                break;
            case R.id.tab_one:
                intent.setClass(OneDetailsActivity.this,MainActivity.class);
                intent.putExtra("id",1);
                startActivity(intent);
                MainActivity.instance.finish();
                OneDetailsActivity.this.finish();
                break;
            case R.id.tab_two:
                intent.setClass(OneDetailsActivity.this,MainActivity.class);
                intent.putExtra("id",2);
                startActivity(intent);
                MainActivity.instance.finish();
                OneDetailsActivity.this.finish();
                break;
            case R.id.tab_three:
                intent.setClass(OneDetailsActivity.this,MainActivity.class);
                intent.putExtra("id",3);
                startActivity(intent);
                MainActivity.instance.finish();
                OneDetailsActivity.this.finish();
                break;
            case R.id.tab_four:
                intent.setClass(OneDetailsActivity.this,MainActivity.class);
                intent.putExtra("id",4);
                startActivity(intent);
                MainActivity.instance.finish();
                OneDetailsActivity.this.finish();
                break;
        }
    }


    public Boolean validate() {
        if (TextUtils.isEmpty(etName.getText().toString())) {
            ToastUtils.showToast(OneDetailsActivity.this, "请输入您的姓名!");
            return false;
        } else if (etPhone.getText().length() == 0 ) {
            ToastUtils.showToast(OneDetailsActivity.this, "请填写联系方式!");
            return false;
        } else if (etPhone.getText().length() != 11){
            ToastUtils.showToast(OneDetailsActivity.this, "请填写11位手机号!");
            return false;
        }else if (etJianjie.getText().length() == 0){
            ToastUtils.showToast(OneDetailsActivity.this, "请输入介绍!");
            return false;
        } else {
            return true;
        }
    }


    private void load() {
        finish();
    }


}
