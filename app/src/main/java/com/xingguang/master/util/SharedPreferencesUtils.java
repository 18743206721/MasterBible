package com.xingguang.master.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * SharedPreferences 工具类
 *
 * @author liuyu
 * @Description: SharedPreferences工具类
 * @ClassName: SharedPreferencesUtils
 * @Date: 2015-4-1 上午9:44:30
 */
public class SharedPreferencesUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    public static final String FIRST_TIME = "first_time";

    // 消息通知 0:允许 1：不允许
    public static final String MESSAGE_STATE = "message_state";

    //USERID
    public static final String USERID = "userId";
    //头像
    public static final String USERIMAGE = "userimage";
    //昵称
    public static final String USERNAME = "username";
    //性别
    public static final String USERSEX = "usersex";
    //地区
    public static final String USERADS = "userads";

    //一起玩联盟id
    public static final String ALLIANCEID = "allianceid";

    //预约看房搜索历史
    public static final String SUBSCRIBEHOUSE = "subscribehouse";

    //新鲜事搜索历史
    public static final String NOVELTYHISTORY = "noveltyhistory";

    //易企购搜索历史
    public static final String PURCHASEONEHISTORY = "purchaseOneHistory";

    //易企购搜索历史
    public static final String PURCHASETWOHISTORY = "purchaseTwoHistory";

    //一起玩搜索历史
    public static final String TOGETHERPLAY = "togetherplay";
    //二手置换搜索历史
    //一起玩搜索历史
    public static final String TWOHAND = "twohand";
    //默认地址用户
    public static final String ADDRESSUSER = "addressuser";
    //默认地址ID
    public static final String ADDRESSID = "addressid";
    //默认地址地址
    public static final String ADDRESSLOC = "addressloc";
    //个人简介
    public static final String AUTOGRAPH = "autograph";
    //个人公司
    public static final String COMPANY = "company";
    //个人园区
    public static final String GION = "gion";

    //cid
    public static final String CID = "cid";

    /**
     * @param context
     * @param key
     * @param object
     * @Description: 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @Title: put
     * @ReturnType: void
     * @Date: 2015-4-1 上午9:38:45
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * @param context
     * @param key
     * @param defaultObject
     * @return
     * @Description: 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @Title: get
     * @ReturnType: Object
     * @Date: 2015-4-1 上午9:39:06
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * @param context
     * @param key
     * @Description: 移除某个key值已经对应的值
     * @Title: remove
     * @ReturnType: void
     * @Date: 2015-4-1 上午9:39:14
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * @param context
     * @Description: 清除所有数据
     * @Title: clear
     * @ReturnType: void
     * @Date: 2015-4-1 上午9:39:26
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * @param context
     * @param key
     * @return
     * @Description: 查询某个key是否已经存在
     * @Title: contains
     * @ReturnType: boolean
     * @Date: 2015-4-1 上午9:39:34
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * @param context
     * @return
     * @Description: 返回所有的键值对
     * @Title: getAll
     * @ReturnType: Map<String,?>
     * @Date: 2015-4-1 上午9:39:42
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * @Description: 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     * @ClassName: SharedPreferencesCompat
     * @Date: 2015-4-1 上午9:39:50
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * @return
         * @Description: 反射查找apply的方法
         * @Title: findApplyMethod
         * @ReturnType: Method
         * @Date: 2015-4-1 上午9:39:57
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }
            return null;
        }

        /**
         * @param editor
         * @Description: 如果找到则使用apply执行，否则使用commit
         * @Title: apply
         * @ReturnType: void
         * @Date: 2015-4-1 上午9:40:08
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
