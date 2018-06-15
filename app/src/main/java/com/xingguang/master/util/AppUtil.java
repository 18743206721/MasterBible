package com.xingguang.master.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.xingguang.master.R;
import com.xingguang.master.login.view.LoginActivity;
import com.xingguang.master.maincode.home.model.JsonBean;
import com.xingguang.master.view.BasicDialog;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;


/**
 * 方法工具类
 *
 * @author BiHaidong
 * @date 2017-4-25
 */

public class AppUtil {

    public static AppUtil instance;

    public static AppUtil getInstance(){
        if (null == instance){
            instance = new AppUtil();
        }
        return instance;
    }

    /**
     * 手机号码 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
     * 联通：130,131,132,152,155,156,185,186 电信：133,1349,153,180,189
     */
    private static final String mobile = "^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}|17[0-9]{9}$";
    /**
     * 10 * 中国移动：China Mobile 11 *
     * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188 12
     */
    private static final String CM = "^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}|17[0-9]{9}$";
    /**
     * 15 * 中国联通：China Unicom 16 * 130,131,132,152,155,156,185,186 17
     */
    private static final String CU = "^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}|17[0-9]{9}$";
    /**
     * 20 * 中国电信：China Telecom 21 * 133,1349,153,180,189 22
     */
    private static final String CT = "^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}|17[0-9]{9}$";

    /**
     * 电话号校验
     *
     * @param phone
     * @return true if the phone is right.
     */
    public static Boolean validatePhone(String phone) {

        List<String> listNumList = new ArrayList<>();
        listNumList.add(mobile);
        listNumList.add(CM);
        listNumList.add(CU);
        listNumList.add(CT);
        Boolean isNum = false;
        for (int i = 0, j = listNumList.size(); i < j; i++) {
            if (phone.matches(listNumList.get(i))) {
                isNum = true;
                break;
            }
        }

        return isNum;
    }

    private static long lastClickTime;

    /**
     * 防止快速点击
     *
     * @return boolean if true FastDoubleClick
     */
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 防止快速点击
     *
     * @param num 间隔时间（单位 毫秒）
     * @return boolean if true FastDoubleClick
     */
    public static boolean isFastDoubleClick(int num) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < num) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    /**
     * 不带参数的跳转
     *
     * @param fromContext
     * @param cls  泛型
     *
     */
    public void intent(Context fromContext, Class<?> cls) {
        Intent intent = new Intent(fromContext, cls);
        fromContext.startActivity(intent);
    }

    /**
     * 保存在手机里面的文件名 用户信息
     */
    public static final String FILE_NAME = "user_data";

    /**
     * 针对复杂类型存储<对象> 保存用户信息
     *
     * @param context
     * @param object
     */
    public static void saveUserInfo(Context context, Object object) {

        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,
                MODE_PRIVATE);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {

            out = new ObjectOutputStream(baos);
            out.writeObject(object);
            String objectVal = new String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT));
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("user", objectVal);
            editor.commit();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 针对复杂类型存储<对象> 清除用户信息
     *
     * @param context
     */
    public static void clearUserInfo(Context context) {

        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

//    /**
//     * 获得用户id
//     *
//     * @param context
//     */
//    public static String getUserId(Context context) {
//        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.USERID, "");
//    }
//
//
//    /**
//     * 获得用户头像
//     *
//     * @param context
//     */
//    public static String getUserImage(Context context) {
//        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.USERIMAGE, "");
//    }
//
//    /**
//     * 获得用户昵称
//     *
//     * @param context
//     */
//    public static String getUserName(Context context) {
//        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.USERNAME, "");
//    }
//    /**
//     * 获得用户个人简介
//     *
//     */
//    public static String getUserAutograph(Context context) {
//        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.AUTOGRAPH, "");
//    }
//    /**
//     * 获得用户公司
//     *
//     */
//    public static String getUserCompany(Context context) {
//        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.COMPANY, "");
//    }
//    /**
//     * 获得用户园区
//     *
//     */
//    public static String getUserGion(Context context) {
//        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.GION, "");
//    }
//
//    /**
//     * 获得默认地址Id
//     *
//     * @param context
//     */
//    public static String getAddressId(Context context) {
//        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.ADDRESSID, "");
//    }
//    /**
//     * 获得默认地址用户
//     *
//     * @param context
//     */
//    public static String getAddressUser(Context context) {
//        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.ADDRESSUSER, "");
//    }
//    /**
//     * 获得默认地址
//     *
//     * @param context
//     */
//    public static String getAddressLoc(Context context) {
//        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.ADDRESSLOC, "");
//    }
//
//    /**
//     * 获得用户手机号
//     *
//     * @param context
//     */
//    public static String getphone(Context context) {
//        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.PHONE, "");
//    }
//
//    /**
//     * 获得邀请码
//     *
//     * @param context
//     */
//    public static String getcode(Context context) {
//        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.CODE, "");
//    }

//    /**
//     * 获得用户id
//     *
//     * @param context
//     */
//    public static String getUserId(Context context) {
//
//        UserBean userBean = getUserInfo(context);
//        if (userBean != null)
//            return userBean.getUserId();
//        return null;
//    }

//    /**
//     * 针对复杂类型存储<对象> 取得用户信息
//     *
//     * @param context
//     */
//    public static UserBean getUserInfo(Context context) {
//
//        SharedPreferences preferences = context.getSharedPreferences(FILE_NAME,
//                MODE_PRIVATE);
//
//        if (preferences.contains("user")) {
//            String objectVal = preferences.getString("user", null);
//            byte[] buffer = android.util.Base64.decode(objectVal, android.util.Base64.DEFAULT);
//            ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
//            ObjectInputStream ois = null;
//            try {
//                ois = new ObjectInputStream(bais);
//                UserBean t = (UserBean) ois.readObject();
//                return t;
//            } catch (StreamCorruptedException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    if (bais != null) {
//                        bais.close();
//                    }
//                    if (ois != null) {
//                        ois.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return null;
//    }

//    public static void dynamicSetTabLayoutMode(TabLayout tabLayout) {
//        int tabWidth = calculateTabWidth(tabLayout);
//        int screenWidth = getScreenWith();
//
//        if (tabWidth <= screenWidth) {
//            tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        } else {
//            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
//        }
//    }

    private static int calculateTabWidth(TabLayout tabLayout) {
        int tabWidth = 0;
        for (int i = 0; i < tabLayout.getChildCount(); i++) {
            final View view = tabLayout.getChildAt(i);
            view.measure(0, 0); // 通知父view测量，以便于能够保证获取到宽高
            tabWidth += view.getMeasuredWidth();
        }
        return tabWidth;
    }

//    public static int getScreenWith() {
//        return app.getResources().getDisplayMetrics().widthPixels;
//    }

    /**
     * from yyyy-MM-dd HH:mm:ss to MM-dd HH:mm
     */
    public static String formatDate(String before) {
        String after;
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                    .parse(before);
            after = new SimpleDateFormat("MM-dd HH:mm", Locale.getDefault()).format(date);
        } catch (ParseException e) {
            return before;
        }
        return after;
    }

    /**
     * 以宽为基准按比例设置view的高
     *
     * @param picWidth  宽高传入比例即可
     * @param picHeight 宽高传入比例即可
     * @describe 宽为屏幕宽度，按照传入的款高比例设置view的高度
     */
    public static void setViewPara(Context context, View view, int picWidth, int picHeight) {
        ViewGroup.LayoutParams para = view.getLayoutParams();

        Double bili = Double.valueOf(picHeight) / Double.valueOf(picWidth);

        para.height = ((Double) ((((Activity) context).getWindowManager()
                .getDefaultDisplay().getWidth()) * bili))
                .intValue();

        view.setLayoutParams(para);
    }

    /**
     * 以屏幕宽为基准按比例设置view的宽高
     *
     * @param picWidth  view的宽所占屏幕宽度的几分之一
     * @param picHeight view的高所占屏幕宽度的几分之一
     * @param picDif    屏幕宽度的差值
     * @describe 以屏幕的宽为基准，根据传入的参数，设置view的宽高
     */
    public static void setViewParaScale(Context context, View view, int picWidth, int picHeight, int picDif) {
        ViewGroup.LayoutParams para = view.getLayoutParams();

        para.width = (((Activity) context).getWindowManager().getDefaultDisplay().getWidth() - dip2px(context, picDif)) / picWidth;
        para.height = (((Activity) context).getWindowManager().getDefaultDisplay().getWidth() - dip2px(context, picDif)) / picHeight;

        view.setLayoutParams(para);
    }

    /**
     * 以屏幕宽为基准按比例设置view的宽高
     *
     * @param picWidth  view的宽所占屏幕宽度的几分之一
     * @param picHeight view的高所占屏幕宽度的几分之一
     * @describe 以屏幕的宽为基准，根据传入的参数，设置view的宽高
     */
    public static void setViewParaScale(Context context, View view, int picWidth, int picHeight) {
        ViewGroup.LayoutParams para = view.getLayoutParams();

        para.width = (((Activity) context).getWindowManager().getDefaultDisplay().getWidth()) / picWidth;
        para.height = (((Activity) context).getWindowManager().getDefaultDisplay().getWidth()) / picHeight;

        view.setLayoutParams(para);
    }

    private static View viewPara;
    private static int viewParaWidth;

    /**
     * 以宽为基准按比例设置view的高
     *
     * @param picWidth  宽高传入比例即可
     * @param picHeight 宽高传入比例即可
     * @describe 宽为屏幕宽度，按照传入的款高比例设置view的高度
     */
    public static void setViewParaObserver(Context context, View view, final int picWidth, final int picHeight) {
        viewPara = view;
        final ViewGroup.LayoutParams para = view.getLayoutParams();

        // 增加整体布局监听
        ViewTreeObserver vto = view.getViewTreeObserver();

        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // TODO Auto-generated method stub
                viewPara.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
                viewParaWidth = viewPara.getMeasuredWidth();

                Double bili = Double.valueOf(picHeight) / Double.valueOf(picWidth);

                para.height = (int) (viewParaWidth * bili);
                viewPara.setLayoutParams(para);
            }
        });

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 是否登录
     *
     * @param context
     */
//    public static boolean isExamine(Context context) {
//        String userId = getUserId(context);
//        Boolean isExamine = false;
//        if (TextUtils.isEmpty(userId)) {
//            isExamine = false;
//        } else {
//            isExamine = true;
//        }
//        return isExamine;
//    }

    /**
     * 是否登录 如果没登录弹出登录Dialog
     *
     * @param context
     */
//    public static boolean isExamined(final Context context) {
//        if (isExamine(context)) {
//            return true;
//        } else {
//            BasicDialog.Builder builder = new BasicDialog.Builder(context);
//            builder.setMessage("您还没有登录哦!~")
//                    .setPositiveButton("登录", new AlertDialog.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent intent = new Intent(context, LoginActivity.class);
//                            intent.putExtra("startFlag", "1");
//                            context.startActivity(intent);
//                            dialog.dismiss();
//                        }
//
//                    })
//                    .setNegativeButton("忽略", new AlertDialog.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//
//            builder.create().show();
//            return false;
//        }
//    }

    /**
     * 设置部分文字颜色
     */
    public static void setTextViewColor(TextView view, String string, int color) {

        SpannableStringBuilder builder = new SpannableStringBuilder(
                view.getText());

        // ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        String str = view.getText().toString();
        int fStart = str.indexOf(string);
        int fEnd = fStart + string.length();
        ForegroundColorSpan redSpan = new ForegroundColorSpan(color);
        builder.setSpan(redSpan, fStart, fEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        view.setText(builder);
    }

    /**
     * get App versionCode
     *
     * @param context
     */
    public static String getVersionCode(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionCode = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode + "";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * get App versionName
     *
     * @param context
     */
    public static String getVersionName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo;
        String versionName = "";
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * get App packageName
     *
     * @param context
     */
    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return 进程名
     */
    public static final String getProcessName(Context context) {
        String processName = null;

        // ActivityManager
        ActivityManager am = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));

        while (true) {
            for (ActivityManager.RunningAppProcessInfo info : am.getRunningAppProcesses()) {
                if (info.pid == android.os.Process.myPid()) {
                    processName = info.processName;
                    break;
                }
            }

            if (!TextUtils.isEmpty(processName)) {
                return processName;
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 设置EditText的光标位置在最后(需要在赋值之后调用)
     *
     * @param view
     */
    public static final void setSelection(EditText view) {

        if (view != null) {
            if (!TextUtils.isEmpty(view.getText())) {
                view.setSelection(view.getText().toString().length());
            }
        }
    }

    /**
     * 设置EditText的最大输入长度
     *
     * @param view
     */
    public static final void setLengthFilter(EditText view, int length) {

        if (view != null) {
            view.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)}); //最大输入长度
        }
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context
     * @param packageName ：应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 判断微信是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        return isAvilible(context, "com.tencent.mm");
    }

    /**
     * 判断qq是否可用
     *
     * @param context
     * @return
     */
    public static boolean isQQClientAvailable(Context context) {
        return isAvilible(context, "com.tencent.mobileqq");
    }

    /**
     * 判断微博是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWeiBoClientAvailable(Context context) {
        return isAvilible(context, "com.sina.weibo");
    }

    public static String TodayNoTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        String d = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1)
                + "-" + c.get(Calendar.DAY_OF_MONTH);
        Date date = null;
        try {
            date = sdf.parse(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdf.format(date);
    }


    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    public static boolean compareDate(String DATE1, String DATE2)  {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return false;
            } else if (dt1.getTime() <= dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return true;
    }

    //获取颜色
    public static int getColor(Context context,int colorId){
        return ContextCompat.getColor(context,colorId);
    }


    /**
     * 隐藏软键盘
     *
     * @param activity 当前Activity
     */
    public static void hideSoftInput(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getWindow().getDecorView().getWindowToken(), 0);
    }


    /**
     * 显示软键盘，Dialog使用
     *
     * @param activity 当前Activity
     */
    public static void showSoftInput(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 给图标着色
     *
     * */
    public static Drawable tintDrawable(Drawable drawable, int colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable).mutate();
        DrawableCompat.setTint(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /** 根据百分比改变颜色透明度 */
    public static int changeAlpha(int color, float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }

    /**
     * 设置系统状态栏，沉浸式效果
     * */
    public static void translucentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = activity.getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    /**
     * 文字颜色
     * */
    public static void addForeColorSpan(TextView textView,String text) {
        SpannableString spanString = new SpannableString(text);
        ForegroundColorSpan span = new ForegroundColorSpan(Color.RED);
        spanString.setSpan(span, text.length()-1, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(spanString);
    }

    /**
     * 文字大小
     * */
    public static void addForeSizeSpan(TextView textView,String text,Context mcontext) {
        SpannableString spanString = new SpannableString(text);
        spanString.setSpan(new AbsoluteSizeSpan(20,true), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置粗体
        spanString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.append(spanString);
    }

    /**
     * 解析实体类数据
     * */
    public static ArrayList<JsonBean> parseData(String jsonData) { //gson解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonData);
            Gson gson = new Gson();
            for (int i = 0; i < array.length(); i++) {
                JsonBean entity = gson.fromJson(array.getJSONObject(i).toString(), JsonBean.class); //转换成实体类
                detail.add(entity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return detail;
    }

    public static void setThemeColor(ImageView mImage, Context context,int icon) {
        //利用ContextCompat工具类获取drawable图片资源
        Drawable drawable = ContextCompat.getDrawable(context, icon);
        //简单的使用tint改变drawable颜色
        Drawable drawable1 = AppUtil.tintDrawable(drawable, ContextCompat.getColor(context, R.color.home_read));
        mImage.setImageDrawable(drawable1);
    }

    /**
     * 获得用户id
     *
     * @param context
     */
    public static String getUserId(Context context) {
        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.USERID, "");
    }

    /**
     * 获得头像
     *
     * @param context
     */
    public static String getUserImage(Context context) {
        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.USERIMAGE, "");
    }

    /**
     * 获得昵称
     *
     * @param context
     */
    public static String getUserNickname(Context context) {
        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.USERNAME, "");
    }
    /**
     * 获得性别
     *
     * @param context
     */
    public static String getUsersex(Context context) {
        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.USERSEX, "");
    }
    /**
     * 获得地区
     *
     * @param context
     */
    public static String getUserads(Context context) {
        return (String) SharedPreferencesUtils.get(context, SharedPreferencesUtils.USERADS, "");
    }



    /**
     * 是否登录
     *
     * @param context
     */
    public static boolean isExamine(Context context) {
        String userId = getUserId(context);
        Boolean isExamine = false;
        if (TextUtils.isEmpty(userId)) {
            isExamine = false;
        } else {
            isExamine = true;
        }
        return isExamine;
    }

    /**
     * 是否登录 如果没登录弹出登录Dialog
     *
     * @param context
     */
    public static boolean isExamined(final Context context) {
        if (isExamine(context)) {
            return true;
        } else {
            BasicDialog.Builder builder = new BasicDialog.Builder(context);
            builder.setMessage("您还没有登录哦!~")
                    .setPositiveButton("登录", new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(context, LoginActivity.class);
                            intent.putExtra("startFlag", "1");
                            context.startActivity(intent);
                            dialog.dismiss();
                        }

                    })
                    .setNegativeButton("忽略", new AlertDialog.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

            builder.create().show();
            return false;
        }
    }


}
