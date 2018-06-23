package com.xingguang.master.maincode.home.view.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragmentAdapter;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.view.MyTabLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建日期：2018/5/26
 * 描述:模拟考试里的分类   考试
 * 作者:LiuYu
 */
public class ExamChapterFragment extends ToolBarFragment {

    TabLayout tab_layoutexam;
    @BindView(R.id.mPager)
    ViewPager mPager;
    List<Fragment> mFragments;
    String[] mTitles = new String[]{};
    private ArrayList<String> list = new ArrayList<>();
    ExamChapterItemFragment listFragment;
    private TextView textView;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exam;
    }

    @Override
    protected void initView() {
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.instance.setBg(1);
                MainActivity.instance.setToNewsFragment();
            }
        });
        setToolBarTitle("分类");
        tab_layoutexam = getActivity().findViewById(R.id.tab_layoutexam);

        loadgongzhong();

    }

    @Override
    protected void lazyLoad() {}

    /**
     * 工种数据
     */
    private void loadgongzhong() {
        OkGo.<String>post(HttpManager.ExamRegistration)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("MethodCode", "list")
                .execute(new DialogCallback<String>(getActivity()) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        BuMengBean bean = gson.fromJson(response.body().toString(), BuMengBean.class);
                        list.clear();
                        if (bean.getData() != null) {
                            for (int i = 0; i < bean.getData().size(); i++) {
                                list.add(bean.getData().get(i).getName());
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
            listFragment = ExamChapterItemFragment.newInstance(i + 1);
            mFragments.add(listFragment);
        }
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragments, mTitles);
        mPager.setAdapter(adapter);
        tab_layoutexam.setupWithViewPager(mPager);

        for (int i = 0; i < list.size(); i++) {
            TabLayout.Tab tab = tab_layoutexam.getTabAt(i);//获得每一个tab
            if (tab != null) {
                tab.setCustomView(R.layout.item_tabtwo);//给每一个tab设置view
            }

            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                if (tab != null) {
                    if (tab.getCustomView() != null) {
                        tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);//第一个tab被选中
                    }
                }
            }
            if (tab != null) {
                if (tab.getCustomView() != null) {
                    textView = (TextView) tab.getCustomView().findViewById(R.id.tab_text);
                }
            }
            textView.setText(mTitles[i]);//设置tab上的文字
        }

        tab_layoutexam.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(true);
                mPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.tab_text).setSelected(false);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }





}
