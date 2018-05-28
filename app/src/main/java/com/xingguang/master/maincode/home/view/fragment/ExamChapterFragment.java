package com.xingguang.master.maincode.home.view.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragmentAdapter;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.view.MyTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建日期：2018/5/26
 * 描述:模拟考试里的分类
 * 作者:LiuYu
 */
public class ExamChapterFragment extends ToolBarFragment {


    @BindView(R.id.tab_layout)
    MyTabLayout tab_layout;
    @BindView(R.id.mPager)
    ViewPager mPager;
    List<Fragment> mFragments;
    String[] mTitles = new String[]{"药具", "市局", "公安局", "质监局", "质监局", "质监局", "质监局"};
    ExamChapterItemFragment listFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_baodian;
    }

    @Override
    protected void initView() {
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().onBackPressed();//销毁自己
                MainActivity.instance.setBg(1);
                MainActivity.instance.setToNewsFragment();
            }
        });
        setToolBarTitle("分类");

        initViewPage();

    }

    @Override
    protected void lazyLoad() {

    }

    private void initViewPage() {
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            listFragment = ExamChapterItemFragment.newInstance(i + 1);
            mFragments.add(listFragment);
        }
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragments, mTitles);
        mPager.setAdapter(adapter);
        tab_layout.setupWithViewPager(mPager);
        mPager.setOffscreenPageLimit(0);
    }




}
