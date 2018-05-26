package com.xingguang.master.maincode.home.view.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.base.ToolBarFragment;
import com.xingguang.master.util.CountDownTimerUtil;

import butterknife.BindView;

/**
 * 创建日期：2018/5/26
 * 描述:
 * 作者:LiuYu
 */
@SuppressLint("ValidFragment")
public class ExamBanFragment extends BaseFragment implements CountDownTimerUtil.CountDownTimerListener{

    @BindView(R.id.textview1)
    TextView textview1;

    int type;
    ViewPager viewPager;
    private CountDownTimerUtil util;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    util.restart();
                    break;
            }
        }
    };


    public ExamBanFragment(int i, ViewPager vp_exters) {
        type = i;
        viewPager = vp_exters;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_examban;
    }

    int a;
    @Override
    protected void initView() {
        util = new CountDownTimerUtil(getActivity(), this);
        textview1.setText(type+"");

        textview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a  =viewPager.getCurrentItem();

                Message msgs = mHandler.obtainMessage();
                msgs.what = 1;
                msgs.sendToTarget();

            }
        });


    }

    @Override
    protected void lazyLoad() {

    }


    @Override
    public void countDownTimerListener(String time) {

    }

    @Override
    public void countDownTimerFinish() {
        viewPager.setCurrentItem(a+1);
        textview1.setText(type+1+"");
    }


}
