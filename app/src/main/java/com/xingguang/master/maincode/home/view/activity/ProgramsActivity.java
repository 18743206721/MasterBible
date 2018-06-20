package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragmentAdapter;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.classifly.view.ClassifExamActivity;
import com.xingguang.master.maincode.home.model.HomeBean;
import com.xingguang.master.maincode.home.model.ProgramsBean;
import com.xingguang.master.maincode.home.model.TwoDetailsBean;
import com.xingguang.master.maincode.home.view.fragment.ListProgramsFragment;
import com.xingguang.master.maincode.home.view.fragment.ProgramsFragment;
import com.xingguang.master.maincode.mine.view.activity.WebViewActivity;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.ImageLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 创建日期：2018/6/15
 * 描述:培训项目，培训报名
 * 作者:LiuYu
 */
public class ProgramsActivity extends ToolBarActivity {

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
    @BindView(R.id.xtab_pro)
    XTabLayout xtabPro;
    @BindView(R.id.mPager)
    ViewPager mPager;
    @BindView(R.id.ll_progroms)
    LinearLayout llProgroms;
    private Intent intent = new Intent();

    List<Fragment> mFragments;
    String[] mTitles = new String[]{};
    private ArrayList<String> list = new ArrayList<>();
    ListProgramsFragment listFragment;
    private String title; //上个界面传过来的跳转到哪个页的
    private int classtype;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_programs;
    }

    @Override
    protected void initView() {
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setToolBarTitle("培训项目");

        title = getIntent().getStringExtra("title");
        //2是 焊工培训更多, 1 是分类按钮培训项目,3是广告位跳转
        classtype = getIntent().getIntExtra("classtype", 0);

//        if (classtype == 1) {
//
//        } else if (classtype == 2) {
//
//        }


        //设置首页按钮颜色
        AppUtil.setThemeColor(tabOneImg, ProgramsActivity.this, R.drawable.home_icon);
        tabOneTxt.setTextColor(getResources().getColor(R.color.text_color_red));

        loadlist();


    }


    private void loadlist() {
        OkGo.<String>post(HttpManager.ProjectTraining)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "list")
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        ProgramsBean bean = gson.fromJson(response.body().toString(), ProgramsBean.class);


                        if (bean.getData()!=null) {
                            for (int i = 0; i < bean.getData().size(); i++) {
                                list.add(bean.getData().get(i).getClassName());
                            }

                            initViewPage(list);

                        }

                    }
                });
    }

    private void initViewPage(ArrayList<String> list) {
        mFragments = new ArrayList<>();
        //list转换成数组
        mTitles = list.toArray(new String[list.size()]);

        for (int i = 0; i < mTitles.length; i++) {
            listFragment = ListProgramsFragment.newInstance(i + 1, mTitles.length);
            mFragments.add(listFragment);
        }
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), mFragments, mTitles);
        mPager.setAdapter(adapter);
        xtabPro.setxTabDisplayNum(mTitles.length);//需要写在setupWithViewPager前
        xtabPro.setupWithViewPager(mPager);
        xtabPro.setupWithViewPager(mPager);
        mPager.setOffscreenPageLimit(0);

        //具体跳转到哪个页面
        if (classtype == 3) {
            if (title.equals("10")) {
                mPager.setCurrentItem(mTitles.length);
            } else {
                mPager.setCurrentItem(Integer.parseInt(title));
            }
        }

    }

    @OnClick({R.id.tab_one, R.id.tab_two, R.id.tab_three, R.id.tab_four})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_one:
                intent.setClass(ProgramsActivity.this, MainActivity.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                MainActivity.instance.finish();
                ProgramsActivity.this.finish();
                break;
            case R.id.tab_two:
                intent.setClass(ProgramsActivity.this, MainActivity.class);
                intent.putExtra("id", 2);
                startActivity(intent);
                MainActivity.instance.finish();
                ProgramsActivity.this.finish();
                break;
            case R.id.tab_three:
                intent.setClass(ProgramsActivity.this, MainActivity.class);
                intent.putExtra("id", 3);
                startActivity(intent);
                MainActivity.instance.finish();
                ProgramsActivity.this.finish();
                break;
            case R.id.tab_four:
                intent.setClass(ProgramsActivity.this, MainActivity.class);
                intent.putExtra("id", 4);
                startActivity(intent);
                MainActivity.instance.finish();
                ProgramsActivity.this.finish();
                break;
        }
    }


}
