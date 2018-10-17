package com.cxp.mybaselibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 本地数据缓存工具类
 */
public class SPUtils {


    private static final String KEY_SP_USER = "key_user";

    private static synchronized SharedPreferences getPreference(Context appContext, String name) {
        SharedPreferences preference = appContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preference;
    }

    public static void clear(Context context) {
        SharedPreferences.Editor edit = getPreference(context, KEY_SP_USER).edit();
        edit.clear();
        edit.commit();
    }

    public static void setUid(Context context, String uid) {
        getPreference(context, KEY_SP_USER).edit().putString(Constant.UID, uid).commit();
    }

    public static String getUid(Context context) {
        return getPreference(context, KEY_SP_USER).getString(Constant.UID, "");
    }

    public static void setPlateNumber(Context context, String plateNumber) {
        getPreference(context, KEY_SP_USER).edit().putString(Constant.PLATE_NUMBER, plateNumber).commit();
    }

    public static String getPlateNumber(Context context) {
        return getPreference(context, KEY_SP_USER).getString(Constant.PLATE_NUMBER, "");
    }
}