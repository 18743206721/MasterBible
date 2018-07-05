package com.xingguang.master.maincode.home.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ikidou.fragmentBackHandler.BackHandlerHelper;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseActivity;
import com.xingguang.master.base.FragAdapter;
import com.xingguang.master.maincode.home.view.fragment.ExamBanFragment;
import com.xingguang.master.util.AppManager;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.CountDownTimerUtil;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.NoScrollViewpager;
import com.xingguang.master.view.TimerTextView;

import java.io.DataInput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;
import rx.Observable;
import rx.functions.Action1;

/**
 * 创建日期：2018/5/26
 * 描述:答题界面
 * 作者:LiuYu
 */
public class DaTiActivity extends BaseActivity implements CountDownTimerUtil.CountDownTimerListener {

    public static DaTiActivity instance;
    ArrayList<Fragment> mFragments;
    @BindView(R.id.vp_exters)
    NoScrollViewpager vp_exters;
    @BindView(R.id.ivback)
    ImageView ivback;
    @BindView(R.id.csv2)
    TimerTextView tvTitle;
    @BindView(R.id.tvti2)
    TextView tvti2;

    private ExamBanFragment listFragment;
    private String count = "";
    String exam = ""; //上个界面传来的，判断是练习1还是考试2
    private CountDownTimerUtil util;
    String date = "";
    String exampaperID;
    private long timea;
    private String kaoshifenshu; //考试分数
    private int currentcount; //当前答题的数量
    private int yesjilu = 0;
    private int nojilu = 0;
    private int biaoshi;
    //声明内部定义的回调接口
   CallBackListener callBackListener;
    private int ivdianji = 0;
    private int kaoshi = 0;//考试提交的标示 0练习，1考试

    public void setCallBackListener(CallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_da_ti;
    }

    @Override
    protected void initView() {
        instance = this;
        exam = getIntent().getStringExtra("exam");
        count = getIntent().getStringExtra("count");
        exampaperID = getIntent().getStringExtra("exampaperID");
        currentcount = getIntent().getIntExtra("currentcount", 0);
        yesjilu = getIntent().getIntExtra("yesjilu",0);
        nojilu = getIntent().getIntExtra("nojilu",0);
        biaoshi=getIntent().getIntExtra("biaoshi",0);
        kaoshi = getIntent().getIntExtra("kaoshi",0);


        if (exam.equals("2")) {
            tvti2.setVisibility(View.GONE);
            kaoshifenshu = getIntent().getStringExtra("kaoshifenshu");
            timea = Long.parseLong(getIntent().getStringExtra("kaoshiTime")) * 60000;
            //设置时间
            tvTitle.setTimes(timea);
            /**
             * 开始倒计时
             */
            if (!tvTitle.isRun()) {
                tvTitle.start();
            }
        } else {
            tvTitle.setVisibility(View.GONE);
            tvti2.setVisibility(View.VISIBLE);
            tvti2.setText("题库练习");

        }
        initViewPage();
    }

    private void initViewPage() {
        mFragments = new ArrayList<>();
        if (count.equals("")) {
            ToastUtils.showToast(DaTiActivity.this, "暂无题目!");
        } else {
            for (int i = 0; i < Integer.parseInt(count); i++) {
                listFragment = new ExamBanFragment(i + 1, count, exam, vp_exters, exampaperID, kaoshifenshu,
                        tvTitle, currentcount,yesjilu,nojilu,biaoshi);
                mFragments.add(listFragment);
            }

            FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), mFragments);
            vp_exters.setAdapter(adapter);
            vp_exters.setOffscreenPageLimit(0);
            vp_exters.setScanScroll(false); //设置Viewpager禁止滑动
                    if (kaoshi == 0) { //练习
                        if (!AppUtil.getCount(DaTiActivity.this).equals("")) {
                            if (ivdianji == 0) {
                                vp_exters.setCurrentItem(Integer.parseInt(AppUtil.getCount(DaTiActivity.this)) - 1);
                            } else {
                                vp_exters.setCurrentItem(Integer.parseInt(AppUtil.getCount(DaTiActivity.this)) - 2);
                            }

                        }
                    }else{ //1是考试

                        vp_exters.setCurrentItem(1);
                    }



        }
    }


    @Override
    public void countDownTimerListener(String time) {
        date = time;
    }

    @Override
    public void countDownTimerFinish() {
    }


    @OnClick(R.id.ivback)
    public void onViewClicked() {
            ivdianji = 1;
        if (callBackListener != null) {
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
                    callBackListener.cancleSelect(ivback);
//                }
//            });
        }

//        if (listFragment != null) {
//            if (listFragment instanceof ExamBanFragment) {
//                listFragment.cancleSelect();
//                //保存当前答对的题，和打错的题，和当前的页数；
//            }
//        }
    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
//            if (listFragment instanceof ExamBanFragment) {
//                listFragment.cancleSelect();
//                //保存当前答对的题，和打错的题，和当前的页数；
////                ToastUtils.showToast(this, "已为您保存当前的答题数!");
////                finish();
//                return true;
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }


    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }

    public  interface CallBackListener{
       void cancleSelect(ImageView ivback);
    }


}
