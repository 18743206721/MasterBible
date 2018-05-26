package com.xingguang.master.util;

import android.content.Context;
import android.os.CountDownTimer;

/**
 * 创建日期：2018/5/26
 * 描述:倒计时工具
 * 作者:LiuYu
 */
public class CountDownTimerUtil {

    private MyCountDownTimer mc;
    private CountDownTimerListener listener;
    private Context context;

    public CountDownTimerUtil(Context context, CountDownTimerListener listener) {
        this.listener = listener;
        this.context = context;
        mc = new MyCountDownTimer(2500, 1000);
    }

    public void restart() {
        mc.start();
    }


    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p/>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p/>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            listener.countDownTimerListener("重新获取");
            listener.countDownTimerFinish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (context != null) {
                listener.countDownTimerListener("倒计时(" + millisUntilFinished / 1000 + ")");
            }
        }
    }

    public interface CountDownTimerListener {
        public void countDownTimerListener(String time);

        public void countDownTimerFinish();
    }

}
