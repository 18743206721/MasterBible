package com.xingguang.master.maincode.mine.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.maincode.mine.view.activity.MineBaoKaoActivity;
import com.xingguang.master.maincode.mine.view.activity.MinePersonActivity;
import com.xingguang.master.maincode.mine.view.activity.SettingActivity;
import com.xingguang.master.util.RoundImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 创建日期：2018/5/25
 * 描述:
 * 作者:LiuYu
 */
public class MineFragment extends ToolBarFragment {
    @BindView(R.id.my_user_img)
    RoundImageView myUserImg;
    @BindView(R.id.my_user_name)
    TextView myUserName;
    @BindView(R.id.ll_baokao1)
    LinearLayout llBaokao1;
    @BindView(R.id.ll_peixun)
    LinearLayout llPeixun;
    @BindView(R.id.ll_person)
    LinearLayout llPerson;
    @BindView(R.id.ll_setting)
    LinearLayout llSetting;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView() {
        setToolBarTitle("我的");
    }

    @Override
    protected void lazyLoad() {

    }

    @OnClick({R.id.my_user_img, R.id.ll_baokao1, R.id.ll_peixun, R.id.ll_person, R.id.ll_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_user_img://修改头像
                startActivity(new Intent(getActivity(),MinePersonActivity.class));
                break;
            case R.id.ll_baokao1://报考记录
                startActivity(new Intent(getActivity(),MineBaoKaoActivity.class)
                        .putExtra("type",1));
                break;
            case R.id.ll_peixun://培训记录
                startActivity(new Intent(getActivity(),MineBaoKaoActivity.class)
                        .putExtra("type",2));
                break;
            case R.id.ll_person://修改个人信息
                startActivity(new Intent(getActivity(),MinePersonActivity.class));
                break;
            case R.id.ll_setting://设置
                startActivity(new Intent(getActivity(),SettingActivity.class));
                break;
        }
    }


}
