package com.xingguang.master.base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    public static Context app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = getApplicationContext();

    }
}