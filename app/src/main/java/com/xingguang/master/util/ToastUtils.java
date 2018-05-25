package com.xingguang.master.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 工具类
 *
 * @author BiHaidong
 * @date 2017-5-15
 */
public class ToastUtils {

    public static Toast mToast;

    /**
     * Toast 封装（防止多次点击）
     */
    public static void showToast(Context context, String text) {

        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.show();
    }

    /**
     * Toast 封装（防止多次点击）
     */
    public static void showLongToast(Context context, String text) {

        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_LONG);
        } else {
            //如果当前Toast没有消失， 直接显示内容，不需要重新设置
            mToast.setText(text);
        }
        mToast.show();
    }
}
