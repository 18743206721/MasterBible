package com.xingguang.master.maincode.home.view.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragmentAdapter;
import com.xingguang.master.base.FragAdapter;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.maincode.home.view.fragment.ExamBanFragment;
import com.xingguang.master.view.CustomViewPager;
import com.xingguang.master.view.NoScrollViewpager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * 创建日期：2018/5/26
 * 描述:答题界面
 * 作者:LiuYu
 */
public class DaTiActivity extends ToolBarActivity {

    ArrayList<Fragment> mFragments;
    @BindView(R.id.vp_exters)
    NoScrollViewpager vp_exters;
    private ExamBanFragment listFragment;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_da_ti;
    }

    @Override
    protected void initView() {
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setToolBarTitle("题库练习");

        initViewPage();
        vp_exters.setScanScroll(false);
    }


    private void initViewPage() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            listFragment = new ExamBanFragment(i,vp_exters);
            mFragments.add(listFragment);
        }
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), mFragments);
        vp_exters.setAdapter(adapter);
        vp_exters.setOffscreenPageLimit(0);
    }




}
