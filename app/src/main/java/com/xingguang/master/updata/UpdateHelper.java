package com.xingguang.master.updata;

import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;
import com.igexin.download.DownloadService;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.xingguang.master.R;
import com.xingguang.master.http.DialogCallback;
import com.xingguang.master.http.HttpManager;
import com.xingguang.master.main.model.UpdateBean;
import com.xingguang.master.main.view.activity.MainActivity;
import com.xingguang.master.util.AppManager;
import com.xingguang.master.util.AppUtil;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * 创建者:Lund
 * 创建时间:2016/9/21
 * 备注:
 */

public class UpdateHelper {

    private Context mContext;
    private File apkFile;
    private String updateAddress;
    private String apkName = "dagongbaodian";
    private ServiceConnection conn;

    public interface UpdateCallBack {
        void hasNewVersion(boolean hasNew);

        void cancelUpdate();
    }

    private UpdateCallBack updateCallBack;

    public UpdateHelper(Context mContext, UpdateCallBack callBack) {
        this.mContext = mContext;
        this.updateCallBack = callBack;

    }


    private void showDialog(UpdateBean bean) {
        final Dialog dialog = new Dialog(mContext, R.style.CustomDialog);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_updata, null);

        TextView mTvCanel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView mTvUpdate = (TextView) view.findViewById(R.id.tv_update);
        TextView tv_info = (TextView) view.findViewById(R.id.tv_info);
        TextView tv_version = (TextView) view.findViewById(R.id.tv_version);

        tv_version.setText("版本:" + bean.getData().getVersionName());
        tv_info.setText(bean.getData().getContent().replace(",", "\n"));

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

    public void getNewVerison() {
        OkGo.<String>post(HttpManager.UPdata)
                .tag(this)
                .cacheKey("cachePostKey")
                .cacheMode(CacheMode.DEFAULT)
                .params("VersionName", AppUtil.getVersionName(MainActivity.instance))
                .execute(new DialogCallback<String>(MainActivity.instance) {
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        UpdateBean bean = gson.fromJson(response.body().toString(), UpdateBean.class);

                        String curAppVersion = AppUtil.getVersionName(mContext);
                        double curVersion = Double.parseDouble(curAppVersion);
                        double newVersion = Double.parseDouble("2.0");
                        updateAddress = bean.getData().getVersionUrl();
                        
                        Log.e("curVersion", "onSuccess: " + curVersion + ",," + newVersion);
                        //更新 ,弹窗
                        showDialog(bean);
                    }
                });
    }

    private void uploadFile() {


        final String url = updateAddress;
        if (conn == null)
            conn = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    com.xingguang.master.updata.DownloadService.DownloadBinder binder = (com.xingguang.master.updata.DownloadService.DownloadBinder) service;
                    com.xingguang.master.updata.DownloadService myService = binder.getService();
                    myService.downApk(url, new com.xingguang.master.updata.DownloadService.DownloadCallback() {
                        @Override
                        public void onPrepare() {

                        }

                        @Override
                        public void onProgress(int progress) {
//                            mTextView.setText(String.format(Locale.CHINESE,"%d%%", progress));
                        }

                        @Override
                        public void onComplete(File file) {
//                            view.showComplete(file);
                        }

                        @Override
                        public void onFail(String msg) {
//                            view.showFail(msg);
                        }
                    });
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {
                    //意味中断，较小发生，酌情处理
                }
            };
        Intent intent = new Intent(mContext,DownloadService.class);
        mContext.bindService(intent, conn, Service.BIND_AUTO_CREATE);
        
        
//        Request request = new Request.Builder().url(updateAddress).build();
//        new OkHttpClient().newCall(request).enqueue(new Callback() {
//
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("h_bl", "onFailure");
//            }
//
//            @SuppressWarnings("resource")
//            @Override
//            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                InputStream is = null;
//                byte[] buf = new byte[2048];
//                int len = 0;
//                FileOutputStream fos = null;
//                String SDPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//                try {
//                    is = response.body().byteStream();
//                    long total = response.body().contentLength();
//                    apkFile = new File(SDPath, apkName);
//                    if (apkFile.exists()) {
//                        apkFile.delete();
//                        apkFile.createNewFile();
//                    }
//                    fos = new FileOutputStream(apkFile);
//                    long sum = 0;
//                    while ((len = is.read(buf)) != -1) {
//                        fos.write(buf, 0, len);
//                        sum += len;
//                        int progress = (int) (sum * 1.0f / total * 100);
//                        Log.d("h_bl", "progress=" + progress);
//                        //                        mTvProgress.setText(progress+"%");
//                    }
//                    fos.flush();
//                    Log.d("h_bl", "文件下载成功");
//
//                    installApk();
//                } catch (Exception e) {
//                    Log.d("h_bl", "文件下载失败");
//                } finally {
//                    try {
//                        if (is != null)
//                            is.close();
//                    } catch (IOException e) {
//                    }
//                    try {
//                        if (fos != null)
//                            fos.close();
//                    } catch (IOException e) {
//                    }
//                }
//            }
//        });
    }

    private void installApk() {
        Intent intent = new Intent();
        //执行动作
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

//    public int getOldVersion() throws PackageManager.NameNotFoundException {
//        PackageManager packageManager = mContext.getPackageManager();
//        //getPackageName()是你当前类的包名，0代表是获取版本信息
//        PackageInfo packInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
//        oldApkName = packInfo.versionName;
//        return packInfo.versionCode;
//    }

    DialogInterface.OnKeyListener keylistener = new DialogInterface.OnKeyListener() {
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                AppManager.AppExit(AppManager.currentActivity());
                return true;
            } else {
                return false;
            }
        }
    };

}
