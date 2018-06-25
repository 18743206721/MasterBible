package com.xingguang.master.main.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.base.BaseActivity;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.main.model.TuisongBean;
import com.xingguang.master.main.model.UpdateBean;
import com.xingguang.master.maincode.classifly.view.fragment.ClassifExamFragment;
import com.xingguang.master.maincode.classifly.view.fragment.ClassifFragment;
import com.xingguang.master.maincode.enter.view.fragment.EnterFragment;
import com.xingguang.master.maincode.home.view.fragment.BaodianFragment;
import com.xingguang.master.maincode.home.view.fragment.ExamChapterFragment;
import com.xingguang.master.maincode.home.view.fragment.HomeFragment;
import com.xingguang.master.maincode.home.view.fragment.OneFragment;
import com.xingguang.master.maincode.home.view.fragment.OnlineFragment;
import com.xingguang.master.maincode.home.view.fragment.ThreeFragment;
import com.xingguang.master.maincode.home.view.fragment.TwoFragment;
import com.xingguang.master.maincode.mine.view.fragment.MineFragment;
import com.xingguang.master.push.IntentService;
import com.xingguang.master.updata.UpdateHelper;
import com.xingguang.master.util.AppManager;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.ToastUtils;

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
    OnlineFragment onlineFragment;//在线留言
    ClassifExamFragment classifExamFragment; //考试宝典
    OneFragment oneFragment; //更多页面的招工信息
    TwoFragment twoFragment; // 资讯更多
    ThreeFragment threeFragment; //焊工更多
    private int id = 0; //考试宝典页面用的id

    private final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1003;

    public UpdateHelper helper = new UpdateHelper(MainActivity.this, new UpdateHelper.UpdateCallBack() {
        @Override
        public void hasNewVersion(boolean hasNew) {
            if (!hasNew) {

            }
        }

        @Override
        public void cancelUpdate() {

        }
    });

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

        //推送
        PushManager.getInstance().initialize(this.getApplicationContext(), com.xingguang.master.push.PushService.class);
        PushManager.getInstance().registerPushIntentService(this.getApplicationContext(), IntentService.class);

        Log.e("sdfasdf", "initView: " + (String) SharedPreferencesUtils.get(MainActivity.this,
                SharedPreferencesUtils.CID, ""));


        if (id == 1) {
//            setBg(1);
//            setToNewsFragment();
        } else if (id == 2) {
            setBg(2);
            setToProjectFragment();
        } else if (id == 3) {
            setBg(3);
            setToActivityFragment();
        } else if (id == 4) {
            setBg(4);
            setToInvestmentFragment();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
//        checkAppVersion();
    }

    //推送
    String tuisong;

    private void load() {

        if (((String) SharedPreferencesUtils.get(MainActivity.this,
                SharedPreferencesUtils.CID, "")).equals("")) {
            tuisong = "";
        } else {
            tuisong = (String) SharedPreferencesUtils.get(MainActivity.this,
                    SharedPreferencesUtils.CID, "");

            OkGo.<String>post(HttpManager.PushMessageInterface)
                    .tag(this)
                    .cacheKey("cachePostKey")
                    .cacheMode(CacheMode.DEFAULT)
                    .params("ClientID", (String) SharedPreferencesUtils.get(MainActivity.this,
                            SharedPreferencesUtils.CID, ""))
                    .execute(new DialogCallback<String>(this) {
                        @Override
                        public void onSuccess(Response<String> response) {
                            Gson gson = new Gson();
                            TuisongBean bean = gson.fromJson(response.body().toString(), TuisongBean.class);
                        }
                    });


        }

    }

    /**
     * 自动检测app的版本
     * 直接检测是否有新的版本，然后进行更新
     */
    private void checkAppVersion() {

        if (Build.VERSION.SDK_INT >= 21) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            } else {
                helper.getNewVerison();
            }
        } else {
            helper.getNewVerison();
        }

    }

    /**
     * 更新弹窗
     *
     * @param bean
     */
    private void showUpdataDialog(UpdateBean bean) {
        final Dialog dialog = new Dialog(this, R.style.CustomDialog);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_updata, null);

        TextView mTvCanel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView mTvUpdate = (TextView) view.findViewById(R.id.tv_update);
        TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
        TextView tv_version = (TextView) view.findViewById(R.id.tv_version);

        tv_version.setText("版本:" + bean.getVersionName());
        tv_info.setText(bean.getContent().replace(",", "\n"));
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        dialog.setCanceledOnTouchOutside(false);

        mTvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

        mTvCanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }


    private void uploadFile() {

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
                if (AppUtil.isExamined(MainActivity.this)) {
                    setBg(3);
                    setToActivityFragment();
                }
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
            case 3: // 报考
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
     * 设置当前的Fragment 为招工更多
     */
    public void setOnOneFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (oneFragment != null) {
            transaction.show(oneFragment);
        } else {
            oneFragment = new OneFragment();
            transaction.add(R.id.main_frame, oneFragment, "oneFragment");
        }
        transaction.commit();
    }

    /**
     * 设置当前的Fragment 为资讯更多
     */
    public void setOnTwoFragment() {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.addToBackStack(null);
        hideAll(transaction);
        if (twoFragment != null) {
            transaction.show(twoFragment);
        } else {
            twoFragment = new TwoFragment();
            transaction.add(R.id.main_frame, twoFragment, "twoFragment");
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
     * 设置当前的Fragment 为报考
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
        if (classifExamFragment != null) {
            transaction.hide(classifExamFragment);
        }
        if (oneFragment != null) {
            transaction.hide(oneFragment);
        }
        if (twoFragment != null) {
            transaction.hide(twoFragment);
        }
        if (threeFragment != null) {
            transaction.hide(threeFragment);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    helper.getNewVerison();

                } else {
                    // Permission Denied
                    ToastUtils.showToast(MainActivity.this, "请到设置中开放对本引用的权限");
                }
            } else {
//                MobclickAgent.reportError(MainActivity.this, "grantResults.length:<0");
            }

        }

    }




}
