package com.xingguang.master.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.xingguang.master.R;
import java.io.File;

/**
 * 版本更新
 * Created by Administrator on 2018/5/3.
 */

public class UpdateVersionService extends Service {

    private String appName;
    private String url;
    String target;
    //通知栏
    private Notification notification = null;
    private NotificationManager notificationManager = null;
    // 通知栏跳转Intent
    private Intent updateIntent = null;
    private PendingIntent pendingIntent = null;
    Notification.Builder builder1;

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @SuppressLint("NewApi")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        appName = intent.getStringExtra("appName");
        url = intent.getStringExtra("url");
        //创建下载APK的路径
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            target = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crave.apk";
        }
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder1 = new Notification.Builder(this);
        builder1.setSmallIcon(R.mipmap.icon_logo); //设置图标
        builder1.setTicker("CraveHome开始下载");
        builder1.setContentTitle("正在下载更新" + 0 + "%");
        builder1.setContentTitle(appName); //设置标题
//        builder1.setContentText("正在下载:"); //消息内容
        builder1.setWhen(System.currentTimeMillis()); //发送时间
        //      builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
        builder1.setAutoCancel(true);//打开程序后图标消失
        //      // 设置下载过程中，点击通知栏，回到主界面
        //      updateIntent = new Intent(this, MainActivity.class);
        //      pendingIntent =PendingIntent.getActivity(this, 0, updateIntent, 0);
        //      builder1.setContentIntent(pendingIntent);

        notification = builder1.build();
        // 通过通知管理器发送通知֪
        notificationManager.notify(124, notification);
        if (url != null) {
            downLoad(url);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void downLoad(String url) {
        OkGo.<File>post(url).execute(new FileCallback("") {
            @Override
            public void onSuccess(Response<File> response) {

            }

            @Override
            public void onStart(Request<File, ? extends Request> request) {
                super.onStart(request);
            }

            @Override
            public void onError(Response<File> response) {
                super.onError(response);
            }
        });
//        HttpUtils utils = new HttpUtils();
//        utils.download(url, target, new RequestCallBack<File>() {
//            @Override
//            public void onStart() {
//                System.out.println("CraveHome开始下载");
//                super.onStart();
//            }
//
//            @SuppressLint("NewApi")
//            @Override
//            public void onLoading(long total, long current, boolean isUploading) {
//                System.out.println("正在下载:" + current * 100 / total);
//                builder1.setContentText("正在下载:" + current * 100 / total + "/100"); //消息内容
//                if (current == total) {
//                    builder1.setContentText("下载完成");
//                    builder1.setDefaults(Notification.DEFAULT_ALL); //设置默认的提示音，振动方式，灯光
//                }
//                notification = builder1.build();
//                notificationManager.notify(124, notification); //通过通知管理器发送通知
//                super.onLoading(total, current, isUploading);
//            }
//
//            @Override
//            public void onSuccess(ResponseInfo<File> arg0) {
//                System.out.println("arg0:" + arg0.result);
//                Intent intent = new Intent(Intent.ACTION_VIEW);
//                intent.addCategory(Intent.CATEGORY_DEFAULT);
//                intent.setDataAndType(Uri.fromFile(arg0.result), "application/vnd.android.package-archive");
//
//                //在BroadcastReceiver和Service中startActivity要加上此设置
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//                // 结束service
//                stopSelf();
//            }
//
//            @Override
//            public void onFailure(HttpException arg0, String arg1) {
//                System.out.println(arg1);
//                // 结束service
//                stopSelf();
//            }
//        });
    }

    @Override
    public void onDestroy() {
        System.out.println("serviceDestroy");
        super.onDestroy();
    }
}