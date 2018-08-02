package com.xingguang.master.maincode.classifly.view.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.github.qing.itemdecoration.GridSpaceItemDecoration;
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
import com.xingguang.master.maincode.classifly.view.ClassifExamActivity;
import com.xingguang.master.maincode.classifly.view.adapter.LeftListAdapter;
import com.xingguang.master.maincode.classifly.view.adapter.RightAdapter;
import com.xingguang.master.maincode.home.model.BuMengBean;
import com.xingguang.master.maincode.home.view.fragment.BaodianItemFragment;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 创建日期：2018/5/25
 * 描述:分类
 * 作者:LiuYu
 */
public class ClassifFragment extends ToolBarFragment {

    @BindView(R.id.tab_class)
    TabLayout tab_layout;
    @BindView(R.id.mPager)
    ViewPager mPager;
    List<Fragment> mFragments;
    ItemFragment listFragment;
    String[] mTitles = new String[]{};
    private ArrayList<String> list = new ArrayList<>();
    private TextView textView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_classif;
    }

    @Override
    protected void initView() {
        setToolBarTitle("分类");
        loadgongzhong();
    }


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
//        list.add("考试机构");


        mFragments = new ArrayList<>();

        //list转换成数组
        mTitles = list.toArray(new String[list.size()]);

        for (int i = 0; i < mTitles.length; i++) {
            listFragment = new ItemFragment(i + 1,list.size());
            mFragments.add(listFragment);
        }
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getChildFragmentManager(), mFragments, mTitles);
        mPager.setAdapter(adapter);
        tab_layout.setupWithViewPager(mPager);

        for (int i = 0; i < list.size(); i++) {
            TabLayout.Tab tab = tab_layout.getTabAt(i);//获得每一个tab
            if (tab != null) {
                tab.setCustomView(R.layout.item_class_tab);//给每一个tab设置view
            }

            if (i == 0) {
                // 设置第一个tab的TextView是被选择的样式
                if (tab != null) {
                    if (tab.getCustomView() != null) {
                        tab.getCustomView().findViewById(R.id.tab_class_text).setSelected(true);//第一个tab被选中
                    }
                }
            }
            if (tab != null) {
                if (tab.getCustomView() != null) {
                    textView = tab.getCustomView().findViewById(R.id.tab_class_text);
                }
            }
            textView.setText(mTitles[i]);//设置tab上的文字
        }

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.tab_class_text).setSelected(true);
                    mPager.setCurrentItem(tab.getPosition());
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {
                    tab.getCustomView().findViewById(R.id.tab_class_text).setSelected(false);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void lazyLoad() {
    }


}
