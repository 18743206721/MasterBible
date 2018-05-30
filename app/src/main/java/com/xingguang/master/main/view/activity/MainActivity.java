package com.xingguang.master.main.view.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xingguang.master.R;
import com.xingguang.master.base.BaseActivity;
import com.xingguang.master.maincode.classifly.view.fragment.ClassifExamFragment;
import com.xingguang.master.maincode.classifly.view.fragment.ClassifFragment;
import com.xingguang.master.maincode.enter.view.fragment.EnterFragment;
import com.xingguang.master.maincode.home.model.MessageEvent;
import com.xingguang.master.maincode.home.view.fragment.BaodianFragment;
import com.xingguang.master.maincode.home.view.fragment.ExamChapterFragment;
import com.xingguang.master.maincode.home.view.fragment.HomeFragment;
import com.xingguang.master.maincode.home.view.fragment.OnlineFragment;
import com.xingguang.master.maincode.home.view.fragment.ProgramsFragment;
import com.xingguang.master.maincode.mine.view.fragment.MineFragment;
import com.xingguang.master.util.AppManager;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.functions.Action1;


/**
 * 创建日期：2018/5/25
 * 描述:主页
 * 作者:LiuYu
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.main_frame)
    FrameLayout mainFrame;
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

    //首页
    private HomeFragment homeFragment;
    //分类
    private ClassifFragment classifFragment;
    //报考
    private EnterFragment enterFragment;
    //我的
    private MineFragment myFragment;
    // 用来判断 两次返回键退出app
    private boolean isExit = false;

    private FragmentManager fm;

    public static MainActivity instance;

    BaodianFragment baodianFragment;  //考试宝典
    ExamChapterFragment examchapterFragment; //模拟考试
    ProgramsFragment programsFragment;//培训项目
    OnlineFragment onlineFragment;//在线留言
    ClassifExamFragment classifExamFragment; //考试宝典

    private int id = 0; //考试宝典页面用的id


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        id = getIntent().getIntExtra("id", 0);

        instance = this;
        fm = getSupportFragmentManager();
        setToNewsFragment();
        setThemeColor(tabOneImg, R.drawable.home_icon);
        tabOneTxt.setTextColor(getResources().getColor(R.color.text_color_red));

        if (id == 1) {
//            setBg(1);
//            setToNewsFragment();
        } else if (id == 2) {
            setBg(2);
            setToProjectFragment();
        } else if (id == 3) {
            setBg(3);
            setToActivityFragment();
        } else if (id == 4){
            setBg(4);
            setToInvestmentFragment();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.tab_one, R.id.tab_two, R.id.tab_three, R.id.tab_four})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.tab_one:// 首页
                setBg(1);
                setToNewsFragment();
                break;
            case R.id.tab_two:// 分类
                setBg(2);
                setToProjectFragment();
                break;
            case R.id.tab_three://报考
                setBg(3);
                setToActivityFragment();
                break;
            case R.id.tab_four:// 我的
                setBg(4);
                setToInvestmentFragment();
                break;
            default:
                break;
        }

    }


    public void setBg(int id) {
        switch (id) {
            case 1: // 首页
                setAllToGrey();
                setThemeColor(tabOneImg, R.drawable.home_icon);
                tabOneTxt.setTextColor(getResources().getColor(R.color.text_color_red));
                break;
            case 2: // 分类
                setAllToGrey();
                setThemeColor(tabTwoImg, R.drawable.classif_icon);
                tabTwoTxt.setTextColor(getResources().getColor(R.color.text_color_red));
                break;
            case 3: // 购物车
                setAllToGrey();
                setThemeColor(tabThreeImg, R.drawable.enter_icon);
                tabThreeTxt.setTextColor(getResources().getColor(R.color.text_color_red));
                break;
            case 4: // 我的
                setAllToGrey();
                setThemeColor(tabFourImg, R.drawable.mine_icon);
                tabFourTxt.setTextColor(getResources().getColor(R.color.text_color_red));
                break;
            default:
                break;
        }
    }

    private void setThemeColor(ImageView mImage, int icon) {
        //利用ContextCompat工具类获取drawable图片资源
        Drawable drawable = ContextCompat.getDrawable(this, icon);
        //简单的使用tint改变drawable颜色
        Drawable drawable1 = AppUtil.tintDrawable(drawable, ContextCompat.getColor(this, R.color.home_read));
        mImage.setImageDrawable(drawable1);
    }


    /**
     * 重置
     */
    private void setAllToGrey() {
        tabOneImg.setImageResource(R.drawable.home_icon);
        tabTwoImg.setImageResource(R.drawable.classif_icon);
        tabThreeImg.setImageResource(R.drawable.enter_icon);
        tabFourImg.setImageResource(R.drawable.mine_icon);

        tabOneTxt.setTextColor(getResources().getColor(R.color.textDarkGray));
        tabTwoTxt.setTextColor(getResources().getColor(R.color.textDarkGray));
        tabThreeTxt.setTextColor(getResources().getColor(R.color.textDarkGray));
        tabFourTxt.setTextColor(getResources().getColor(R.color.textDarkGray));

    }


    /**
     * 设置当前的Fragment 为考试宝典
     *
     * @param position
     */
    public void setOnClassifExamFragment(int position) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (classifExamFragment != null) {
            transaction.show(classifExamFragment);
        } else {
            classifExamFragment = new ClassifExamFragment();
            transaction.add(R.id.main_frame, classifExamFragment, "classifExamFragment");
        }
        transaction.commit();
    }


    /**
     * 设置当前的Fragment 为在线留言
     */
    public void setOnProgramsFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (programsFragment != null) {
            transaction.show(programsFragment);
        } else {
            programsFragment = new ProgramsFragment();
            transaction.add(R.id.main_frame, programsFragment, "programsFragment");
        }
        transaction.commit();
    }

    /**
     * 设置当前的Fragment 为在线留言
     */
    public void setOnLineFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (onlineFragment != null) {
            transaction.show(onlineFragment);
        } else {
            onlineFragment = new OnlineFragment();
            transaction.add(R.id.main_frame, onlineFragment, "onlineFragment");
        }
        transaction.commit();
    }

    /**
     * 设置当前的Fragment 为考试宝典
     */
    public void setToBaodianFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (baodianFragment != null) {
            transaction.show(baodianFragment);
        } else {
            baodianFragment = new BaodianFragment();
            transaction.add(R.id.main_frame, baodianFragment, "baodianFragment");
        }
        transaction.commit();
    }

    /**
     * 设置当前的Fragment 为模拟考试
     */
    public void setToExamChapterFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (examchapterFragment != null) {
            transaction.show(examchapterFragment);
        } else {
            examchapterFragment = new ExamChapterFragment();
            transaction.add(R.id.main_frame, examchapterFragment, "examchapterFragment");
        }
        transaction.commit();
    }

    /**
     * 设置当前的Fragment 为首页
     */
    public void setToNewsFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (homeFragment != null) {
            transaction.show(homeFragment);

        } else {
            homeFragment = new HomeFragment();
            transaction.add(R.id.main_frame, homeFragment, "homeFragment");
        }
        transaction.commit();
    }

    /**
     * 设置当前的Fragment 为分类
     */
    public void setToProjectFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (classifFragment != null) {
            transaction.show(classifFragment);
        } else {
            classifFragment = new ClassifFragment();
            transaction.add(R.id.main_frame, classifFragment, "classifFragment");
        }
        transaction.commit();
    }

    /**
     * 设置当前的Fragment 为购物车
     */

    public void setToActivityFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (enterFragment != null) {
            transaction.show(enterFragment);
        } else {
            enterFragment = new EnterFragment();
            transaction.add(R.id.main_frame, enterFragment, "enterFragment");
        }
        transaction.commit();
    }

    /**
     * 设置当前的Fragment 为我的
     */
    public void setToInvestmentFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (myFragment != null) {
            transaction.show(myFragment);
        } else {
            myFragment = new MineFragment();
            transaction.add(R.id.main_frame, myFragment, "mineFragment");
        }
        transaction.commit();
    }

    private void hideAll(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (baodianFragment != null) {
            transaction.hide(baodianFragment);
        }
        if (classifFragment != null) {
            transaction.hide(classifFragment);
        }
        if (enterFragment != null) {
            transaction.hide(enterFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
        if (examchapterFragment != null) {
            transaction.hide(examchapterFragment);
        }
        if (onlineFragment != null) {
            transaction.hide(onlineFragment);
        }
        if (programsFragment != null) {
            transaction.hide(programsFragment);
        }
        if (classifExamFragment != null) {
            transaction.hide(classifExamFragment);
        }

    }

    /**
     * 按俩次back键退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isExit) {
                isExit = true;
                ToastUtils.showToast(this, "再按一次退出程序");
                Observable.timer(2000, TimeUnit.MILLISECONDS)
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                isExit = false;
                            }
                        });

                return false;
            }
            AppManager.AppExit(this);
        }

        return super.onKeyDown(keyCode, event);
    }


}
