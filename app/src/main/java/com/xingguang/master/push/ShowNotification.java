/**
 *
 */
package com.xingguang.master.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.xingguang.master.R;
import com.xingguang.master.main.view.activity.MainActivity;


public class ShowNotification {

    private Notification notification;
    private NotificationManager nManager;

    public void showIsAppLive(Context context, String title, int id,
                              String text, String showType, String arg1, String arg2, String arg3) {
        nManager = (NotificationManager) context
                .getSystemService(context.NOTIFICATION_SERVICE);

        Intent mainIntent = new Intent(context, MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // 创建一个PendingIntent，和Intent类似，不同的是由于不是马上调用，需要在下拉状态条出发的activity，所以采用的是PendingIntent,即点击Notification跳转启动到哪个Activity
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id,
                mainIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.icon_logo)
                .setTicker(text)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setNumber(1).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。

        //添加声音提示
        notification.defaults = Notification.DEFAULT_SOUND;
        // audioStreamType的值必须AudioManager中的值，代表着响铃的模式
        notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;

        notification.defaults = Notification.DEFAULT_VIBRATE;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        long[] vibrate = {0, 100, 200, 300};
        notification.vibrate = vibrate;
        nManager.notify(id, notification);
    }

    public void showIsNotApplive(Context context, String title, int id,
                                 String text, String showType, String arg1, String arg2, String arg3) {

        Bitmap bit = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon_logo);

        nManager = (NotificationManager) context
                .getSystemService(context.NOTIFICATION_SERVICE);

        Intent launchIntent = context.getPackageManager()
                .getLaunchIntentForPackage("com.xingguang.master");
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        // 创建一个PendingIntent，和Intent类似，不同的是由于不是马上调用，需要在下拉状态条出发的activity，所以采用的是PendingIntent,即点击Notification跳转启动到哪个Activity
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id,
                launchIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification = new Notification.Builder(context)
                .setSmallIcon(R.mipmap.icon_logo)
                .setTicker(text)
                .setLargeIcon(bit)
                .setContentTitle(title)
                .setContentText(text)
                .setContentIntent(pendingIntent)
                .setNumber(1).build(); // 需要注意build()是在API
        // level16及之后增加的，API11可以使用getNotificatin()来替代
        notification.flags |= Notification.FLAG_AUTO_CANCEL; // FLAG_AUTO_CANCEL表明当通知被用户点击时，通知将被清除。

        //添加声音提示
        notification.defaults = Notification.DEFAULT_SOUND;
        // audioStreamType的值必须AudioManager中的值，代表着响铃的模式
        notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;

        notification.defaults = Notification.DEFAULT_VIBRATE;
        notification.flags = Notification.FLAG_AUTO_CANCEL;
        long[] vibrate = {0, 100, 200, 300};
        notification.vibrate = vibrate;

        nManager.notify(id, notification);
    }
}
