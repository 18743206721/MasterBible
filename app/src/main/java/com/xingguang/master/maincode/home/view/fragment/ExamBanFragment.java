package com.xingguang.master.maincode.home.view.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.BaseFragment;
import com.xingguang.master.maincode.home.view.activity.FiBaodianActivity;
import com.xingguang.master.util.CountDownTimerUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 创建日期：2018/5/26
 * 描述:题库练习
 * 作者:LiuYu
 */
@SuppressLint("ValidFragment")
public class ExamBanFragment extends BaseFragment implements CountDownTimerUtil.CountDownTimerListener {

    ViewPager viewPager;
    @BindView(R.id.tv_yes_count)
    TextView tvYesCount;
    @BindView(R.id.tv_no_count)
    TextView tvNoCount;
    @BindView(R.id.all_count)
    TextView allCount;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.iv_a)
    ImageView ivA;
    @BindView(R.id.tv_a)
    TextView tvA;
    @BindView(R.id.ll_a)
    LinearLayout llA;
    @BindView(R.id.iv_b)
    ImageView ivB;
    @BindView(R.id.tv_b)
    TextView tvB;
    @BindView(R.id.ll_b)
    LinearLayout llB;
    @BindView(R.id.iv_c)
    ImageView ivC;
    @BindView(R.id.tv_c)
    TextView tvC;
    @BindView(R.id.ll_c)
    LinearLayout llC;
    @BindView(R.id.iv_d)
    ImageView ivD;
    @BindView(R.id.tv_d)
    TextView tvD;
    @BindView(R.id.ll_d)
    LinearLayout llD;
    @BindView(R.id.ll_lianxi)
    LinearLayout llLianxi;
    @BindView(R.id.ll_jiaojuan)
    LinearLayout llJiaojuan;
    @BindView(R.id.tv2_yes_count)
    TextView tv2YesCount;
    @BindView(R.id.tv2_no_count)
    TextView tv2NoCount;
    @BindView(R.id.all2_count)
    TextView all2Count;
    @BindView(R.id.ll_exam)
    LinearLayout llExam;
    Unbinder unbinder;
    private CountDownTimerUtil util;
    int type; //上个界面传过来的页面数值

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

    private int yesallno = 0; //对错标示 1对，2错
    private int sum;
    private String exam;

    public ExamBanFragment(int type, int sum, String exam, ViewPager vp_exters) {
        this.type = type;
        this.sum = sum;
        this.exam = exam;
        viewPager = vp_exters;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_examban;
    }

    int a;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initView() {
        if ("1".equals(exam)) { //练习
            llLianxi.setVisibility(View.VISIBLE);
            llExam.setVisibility(View.GONE);
            allCount.setText(type + "/" + sum);
        } else { //考试
            llLianxi.setVisibility(View.GONE);
            llExam.setVisibility(View.VISIBLE);
            all2Count.setText(type + "/" + sum);
        }


        util = new CountDownTimerUtil(getActivity(), this);

        comment();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void comment() {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append("单选题" + " 在选择漏电保护装置中，要避免由于正常____引起的不必要的动作而影响正常供电打开九分裤。");
        //获取图片
        Drawable drawable = getResources().getDrawable(R.mipmap.danxuan);
        //设置图片的间距
        drawable.setBounds(0, -20, drawable.getIntrinsicWidth() + 20, drawable.getIntrinsicHeight());
        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvContent.setText(spannableString);
        tvContent.setLetterSpacing(0.3f);//设置TextView的字间距，在低版本的手机上无效果
    }

    @Override
    protected void lazyLoad() {

    }


    @Override
    public void countDownTimerListener(String time) {

    }

    @Override
    public void countDownTimerFinish() {
        viewPager.setCurrentItem(a + 1);
        if (type+1 == sum-1){
           startActivity(new Intent(getActivity(),FiBaodianActivity.class));
           getActivity().finish();
        }

    }

    @OnClick({R.id.ll_a, R.id.ll_b, R.id.ll_c, R.id.ll_d,R.id.ll_jiaojuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_a:
                setbg(1);
                break;
            case R.id.ll_b:
                setbg(2);
                break;
            case R.id.ll_c:
                setbg(3);
                break;
            case R.id.ll_d:
                setbg(4);
                break;
            case R.id.ll_jiaojuan://交卷

                break;
        }
    }


    public void setbg(int id) {
        switch (id) {
            case 1: // a
                setlected(1);
                break;
            case 2: // b
                setlected(2);
                break;
            case 3: // c
                setlected(3);
                break;
            case 4: // d
                setlected(4);
                break;
            default:
                break;
        }
    }


    private void setlected(int i) {

        if (i == 1) {
            if (yesallno == 1) {
                ivA.setImageResource(R.mipmap.yes);
            } else {
                ivA.setImageResource(R.mipmap.no);
            }

            llB.setClickable(false);
            llC.setClickable(false);
            llD.setClickable(false);
        } else if (i == 2) {
            if (yesallno == 1) {
                ivB.setImageResource(R.mipmap.yes);
            } else {
                ivB.setImageResource(R.mipmap.no);
            }

            llA.setClickable(false);
            llC.setClickable(false);
            llD.setClickable(false);

        } else if (i == 3) {
            if (yesallno == 1) {
                ivC.setImageResource(R.mipmap.yes);
            } else {
                ivC.setImageResource(R.mipmap.no);
            }

            llA.setClickable(false);
            llB.setClickable(false);
            llD.setClickable(false);

        } else {
            if (yesallno == 1) {
                ivD.setImageResource(R.mipmap.yes);
            } else {
                ivD.setImageResource(R.mipmap.no);
            }

            llA.setClickable(false);
            llB.setClickable(false);
            llC.setClickable(false);

        }

        a = viewPager.getCurrentItem();
        Message msgs = mHandler.obtainMessage();
        msgs.what = 1;
        msgs.sendToTarget();


    }



}
