package com.xingguang.master.app;

import android.content.Context;

import com.lzy.okgo.OkGo;
import com.xingguang.master.base.BaseApplication;


/**
 * Application 基类
 *
 * @author BiHaidong
 * @date 2017-4-25
 */

public class MyApplication extends BaseApplication {

    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.getInstance().init(this);

//        Config.DEBUG = true;
//        UMShareAPI.get(this);
//        PlatformConfig.setWeixin("wx84ca153aa536ac9b", "c33a2d97edd2c5bba9ff0d86b2f0fad5");
//        PlatformConfig.setQQZone("1106595089", "KEYpUll27j3djgCR4jY");
//        PlatformConfig.setSinaWeibo("2163751088", "f4ac147246f089774295ab3a5480a765", "http://sns.whalecloud.com");
//        SMSSDK.initSDK(this, "ee879aa47de7", "29bda79d5acb507531eba66ffe8d9abf");
    }

    /**
     * 获取context
     * @return
     */
    public static Context getInstance() {
        if (mInstance == null) {
            mInstance = new MyApplication();
        }
        return mInstance;
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

}
