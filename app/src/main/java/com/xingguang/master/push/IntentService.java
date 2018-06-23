package com.xingguang.master.push;

import android.content.Context;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.igexin.sdk.message.FeedbackCmdMessage;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.igexin.sdk.message.SetTagCmdMessage;
import com.xingguang.master.R;
import com.xingguang.master.util.AppUtil;
import com.xingguang.master.util.LogUtils;
import com.xingguang.master.util.SharedPreferencesUtils;
import com.xingguang.master.util.SystemUtils;

public class IntentService extends GTIntentService {

    public static int notificationNum = 0;

    public IntentService() {

    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {

        String appId = msg.getAppid();
        String taskId = msg.getTaskId();
        String messageId = msg.getMessageId();
        byte[] payload = msg.getPayload();
        String pkg = msg.getPkgName();
        String cid = msg.getClientId();

        // 第三方回执调用接口，actionId范围为90000-90999，可根据业务场景执行
        boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskId, messageId, 90001);
        LogUtils.d(TAG, "call sendFeedbackMessage = " + (result ? "success" : "failed"));

        LogUtils.d(TAG, "onReceiveMessageData -> " + "appId = " + appId + "\ntaskId = " + taskId + "\nmessageId = " + messageId + "\npkg = " + pkg
                + "\ncid = " + cid);

        if (payload == null) {
            LogUtils.e(TAG, "receiver payload = null");
        } else {
            String data = new String(payload);
            LogUtils.d(TAG, "receiver payload = " + data);

            if (SharedPreferencesUtils.get(getApplicationContext(), SharedPreferencesUtils.MESSAGE_STATE, "0").equals("0")) {
                isRunningForeground(context, data);
            }

        }

        LogUtils.d(TAG, "----------------------------------------------------------------------------------------------");
    }

    @Override
    public void onReceiveClientId(Context context, String clientId) {
        LogUtils.e(TAG, "onReceiveClientId -> " + "clientId = " + clientId);
        SharedPreferencesUtils.put(context, SharedPreferencesUtils.CID, clientId);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        LogUtils.d(TAG, "onReceiveCommandResult -> " + cmdMessage);

        int action = cmdMessage.getAction();

        if (action == PushConsts.SET_TAG_RESULT) {
            setTagResult((SetTagCmdMessage) cmdMessage);
        } else if ((action == PushConsts.THIRDPART_FEEDBACK)) {
            feedbackResult((FeedbackCmdMessage) cmdMessage);
        }
    }

    private void setTagResult(SetTagCmdMessage setTagCmdMsg) {
        String sn = setTagCmdMsg.getSn();
        String code = setTagCmdMsg.getCode();

        String text = "设置标签失败, 未知异常";
        switch (Integer.valueOf(code)) {
            case PushConsts.SETTAG_SUCCESS:
                text = "设置标签成功";
                break;

            case PushConsts.SETTAG_ERROR_COUNT:
                text = "设置标签失败, tag数量过大, 最大不能超过200个";
                break;

            case PushConsts.SETTAG_ERROR_FREQUENCY:
                text = "设置标签失败, 频率过快, 两次间隔应大于1s且一天只能成功调用一次";
                break;

            case PushConsts.SETTAG_ERROR_REPEAT:
                text = "设置标签失败, 标签重复";
                break;

            case PushConsts.SETTAG_ERROR_UNBIND:
                text = "设置标签失败, 服务未初始化成功";
                break;

            case PushConsts.SETTAG_ERROR_EXCEPTION:
                text = "设置标签失败, 未知异常";
                break;

            case PushConsts.SETTAG_ERROR_NULL:
                text = "设置标签失败, tag 为空";
                break;

            case PushConsts.SETTAG_NOTONLINE:
                text = "还未登陆成功";
                break;

            case PushConsts.SETTAG_IN_BLACKLIST:
                text = "该应用已经在黑名单中,请联系售后支持!";
                break;

            case PushConsts.SETTAG_NUM_EXCEED:
                text = "已存 tag 超过限制";
                break;

            default:
                break;
        }

        LogUtils.d(TAG, "setTag result sn = " + sn + ", code = " + code + ", text = " + text);
    }

    private void feedbackResult(FeedbackCmdMessage feedbackCmdMsg) {
        String appId = feedbackCmdMsg.getAppid();
        String taskId = feedbackCmdMsg.getTaskId();
        String actionId = feedbackCmdMsg.getActionId();
        String result = feedbackCmdMsg.getResult();
        long timestamp = feedbackCmdMsg.getTimeStamp();
        String cid = feedbackCmdMsg.getClientId();

        LogUtils.d(TAG, "onReceiveCommandResult -> " + "appId = " + appId + "\ntaskId = " + taskId + "\nactionId = " + actionId + "\nresult = " + result
                + "\ncid = " + cid + "\ntimestamp = " + timestamp);
    }

    public static boolean isRunningForeground(Context context, String data) {

//        String[] resultInfo = data.split("\\&");
//        String status = resultInfo[1];
//        String text = resultInfo[0];

        notificationNum = notificationNum + 1;
        // app运行
        if (SystemUtils.isAppAlive(context, AppUtil.getPackageName(context))) {
            new ShowNotification().showIsAppLive(context, context.getResources().getString(com.xingguang.master.R.string.app_name),
                    notificationNum, data, "", "",
                    "", "");
            return false;

            // app 被kill
        } else {
            new ShowNotification().showIsNotApplive(context,context.getResources().getString(com.xingguang.master.R.string.app_name),
                    notificationNum, data, "", "",
                    "", "");
            return false;

        }
    }
}
