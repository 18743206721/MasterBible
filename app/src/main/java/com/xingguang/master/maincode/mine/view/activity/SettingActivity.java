package com.xingguang.master.maincode.mine.view.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.BuildConfig;
import com.xingguang.master.R;
import com.xingguang.master.base.ToolBarActivity;
import com.xingguang.master.http.CommonBean;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.login.model.LoginBean;
import com.xingguang.master.login.view.LoginActivity;
import com.xingguang.master.main.model.UpdateBean;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.LogUtils;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.ToastUtils;
import com.xingguang.master.view.ImageLoader;

import java.io.File;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 创建日期：2018/5/31
 * 描述:设置
 * 作者:LiuYu
 */
public class SettingActivity extends ToolBarActivity {

    @BindView(R.id.ll_update)
    LinearLayout llUpdate;
    @BindView(R.id.ll_about)
    LinearLayout llAbout;
    @BindView(R.id.ll_backlogin)
    LinearLayout ll_backlogin;
    private DownloadManager downloadManager;
    private long mTaskId;
    private PackageInfo packageInfo;
    private final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1003;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        setToolBarTitle("设置");
        getToolbarBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @OnClick({R.id.ll_update, R.id.ll_about, R.id.ll_backlogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_update:
                if (Build.VERSION.SDK_INT >= 21) {
                    if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //申请WRITE_EXTERNAL_STORAGE权限
                        ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                    } else {
                        loadcheck();
                    }
                } else {
                    loadcheck();
                }

                break;
            case R.id.ll_about:
                startActivity(new Intent(SettingActivity.this, WebViewActivity.class)
                        .putExtra("id", 0)
                        .putExtra("title",0)//title 1是全国考试机构，0是关于我们
                );
                break;
            case R.id.ll_backlogin: //退出登录
                loadback();
                break;
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
                .params("VersionName", AppUtil.getVersionName(SettingActivity.this))
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        UpdateBean bean = gson.fromJson(response.body().toString(), UpdateBean.class);
                        try {
                            packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            if (bean.getData().getVersionName()!=null) {
                                if (compareVersion(AppUtil.getVersionName(SettingActivity.this), bean.getData().getVersionName()) != 1) {
                                    showDialog(bean);
                                }
                            }else {
                                ToastUtils.showToast(SettingActivity.this, "当前已是最新版本");
                            }
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
                    ToastUtils.showToast(SettingActivity.this, "请到设置中开放对本引用的权限");
                }
            } else {
//                MobclickAgent.reportError(MainActivity.this, "grantResults.length:<0");
            }

        }

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


    @Override
    protected void onResume() {
        super.onResume();
        if (AppUtil.getUserId(SettingActivity.this).equals("")) {
            ll_backlogin.setVisibility(View.GONE);
        } else {
            ll_backlogin.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 退出界面
     */
    private void loadback() {
        OkGo.<String>get(HttpManager.NonLogin)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("UserName", AppUtil.getUserId(SettingActivity.this))
                .execute(new DialogCallback<String>(this) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        CommonBean loginBean = gson.fromJson(response.body().toString(), CommonBean.class);
                        SharedPreferencesUtils.clear(SettingActivity.this);
                        ToastUtils.showToast(SettingActivity.this, loginBean.getResult());
                        finish();
                    }
                });
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

        tv_version.setText("版本:" + bean.getData().getVersionName());
        tv_info.setText(bean.getData().getContent().replace(",", "\n"));


        mTvUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ToastUtils.showToast(SettingActivity.this, "正在下载中,请稍后...");
                downloadAPK(bean.getData().getVersionUrl(), packageInfo.versionName);
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
        SettingActivity.this.registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
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
                            + File.separator + AppUtil.getVersionName(SettingActivity.this);
//                    SPUtils.getInstance().put(Constant.IS_DOWNLOAD_APK, false);
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
//        unregisterReceiver(receiver);
    }


}
