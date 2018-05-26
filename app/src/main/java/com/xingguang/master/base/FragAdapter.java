package com.xingguang.master.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建日期：2018/5/26
 * 描述:
 * 作者:LiuYu
 */
public class FragAdapter  extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    /**
     * 构造函数中就把 管理Fragment的List集合传入进来
     * 在这里必须实现只重写getItem(int)和getCount()
     * @param fragmentManager
     * @param fragmentList
     */
    public FragAdapter(FragmentManager fragmentManager, ArrayList<Fragment> fragmentList) {
        super(fragmentManager);
        this.fragmentList = fragmentList;
    }
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
