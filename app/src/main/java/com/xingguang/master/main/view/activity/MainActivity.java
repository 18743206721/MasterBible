package com.xingguang.master.main.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.igexin.sdk.PushManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.BuildConfig;
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
import com.xingguang.master.maincode.mine.view.activity.SettingActivity;
import com.xingguang.master.maincode.mine.view.fragment.MineFragment;
import com.xingguang.master.push.IntentService;
import com.xingguang.master.updata.UpdateHelper;
import com.xingguang.master.util.AppManager;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.LogUtils;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.ToastUtils;

import java.io.File;
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
    private PackageInfo packageInfo;
    private DownloadManager downloadManager;
    private long mTaskId;

//    public UpdateHelper helper = new UpdateHelper(MainActivity.this, new UpdateHelper.UpdateCallBack() {
//        @Override
//        public void hasNewVersion(boolean hasNew) {
//            if (!hasNew) {
//
//            }
//        }
//
//        @Override
//        public void cancelUpdate() {
//
//        }
//    });

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

        checkAppVersion();

    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
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
                loadcheck();
            }
        } else {
            loadcheck();
        }

    }

    /**
     * 检查更新版本
     */
    private void loadcheck() {
        OkGo.<String>post(HttpManager.UPdata)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("VersionName", AppUtil.getVersionCode(MainActivity.this))
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        UpdateBean bean = gson.fromJson(response.body().toString(), UpdateBean.class);
                        try {
                            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            if (compareVersion(AppUtil.getVersionCode(MainActivity.this), bean.getVersionName()) != 1) {
                                showDialog(bean);
                            } else {
                                ToastUtils.showToast(MainActivity.this, "当前已是最新版本");
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 版本号比较
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        if (version1.equals(version2)) {
            return 0;
        }
        String[] version1Array = version1.split("\\.");
        String[] version2Array = version2.split("\\.");
        int index = 0;
        // 获取最小长度值
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;
        // 循环判断每位的大小
        while (index < minLen
                && (diff = Integer.parseInt(version1Array[index])
                - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }
        if (diff == 0) {
            // 如果位数不一致，比较多余位数
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return 1;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return -1;
                }
            }
            return 0;
        } else {
            return diff > 0 ? 1 : -1;
        }
    }

    private void showDialog(final UpdateBean bean) {
        final Dialog dialog = new Dialog(this, R.style.update_dialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_updata, null);

        dialog.setContentView(view);
        dialog.setCancelable(true);//设置点击屏幕消失
        Window window = dialog.getWindow();

//        dialog.getWindow().setWindowAnimations(R.style.AnimBottom);
        //最重要的一句话，一定要加上！要不然怎么设置都不行！
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        //设置dialog能适配各个手机屏幕
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        //获取屏幕宽
        wlp.width = (int) (d.getWidth());
        //宽度按屏幕大小的百分比设置，这里我设置的是全屏显示
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT; // 高度自适应
        window.setAttributes(wlp);


        TextView mTvCanel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView mTvUpdate = (TextView) view.findViewById(R.id.tv_update);
        TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
        TextView tv_version = (TextView) view.findViewById(R.id.tv_version);

        tv_version.setText("版本:" + bean.getVersionName());
        tv_info.setText(bean.getContent().replace(",", "\n"));


        mTvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast(MainActivity.this, "正在下载中,请稍后...");
                dialog.dismiss();
                downloadAPK(bean.getVersionUrl(), packageInfo.versionName);
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


    /**
     * 使用系统下载器下载
     */
    public void downloadAPK(String versionUrl, String versionName) {
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(versionUrl));
        //漫游网络是否可以下载
        request.setAllowedOverRoaming(false);

        //设置文件类型，可以在下载结束后自动打开该文件
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(versionUrl));
        request.setMimeType(mimeString);

        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);

        //sdcard的目录下的download文件夹，必须设置
        request.setDestinationInExternalPublicDir("/download/", versionName);
//        request.setDestinationInExternalPublicDir(getExternalCacheDir().getAbsolutePath(), "download"); 解析包错问题（未下载）
//        request.setDestinationInExternalFilesDir(),也可以自己制定下载路径

        //将下载请求加入下载队列
        downloadManager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
        //加入下载队列后会给该任务返回一个long型的id，
        //通过该id可以取消任务，重启任务等等，看上面源码中框起来的方法
        mTaskId = downloadManager.enqueue(request);
        //注册广播接收者，监听下载状态
        MainActivity.this.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }


    /**
     * 广播接受者，接收下载状态
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            checkDownloadStatus();//检查下载状态
        }
    };

    /**
     * 检查下载状态
     */
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(mTaskId);
        //筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    LogUtils.i(">>>下载暂停");
                    break;
                case DownloadManager.STATUS_PENDING:
                    LogUtils.i(">>>下载延迟");
                    break;
                case DownloadManager.STATUS_RUNNING:
                    LogUtils.i(">>>正在下载");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    LogUtils.i(">>>下载完成");
                    //下载完成安装APK
                    String downloadPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
                            + File.separator + AppUtil.getVersionName(MainActivity.this);
                    installAPK(new File(downloadPath));
                    break;
                case DownloadManager.STATUS_FAILED:
                    LogUtils.i(">>>下载失败");
                    break;
                default:
                    break;
            }
        }
    }


    protected void installAPK(File file) {
        Log.i("大小", "installAPK: " + file.length());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (file != null) {
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider", file);
                intent.setDataAndType(contentUri, "applicationnd.android.package-archive");
                this.startActivity(intent);
            }
        } else {

            Uri downloadFileUri;
//            File file = this.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/update.apk");
            if (file != null) {
                String path = file.getAbsolutePath();
                downloadFileUri = Uri.parse("file://" + path);
                intent.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
            }

//            intent.setDataAndType(Uri.fromFile(file), "applicationnd.android.package-archive");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                    loadcheck();

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
